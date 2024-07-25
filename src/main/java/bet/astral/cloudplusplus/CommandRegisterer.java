package bet.astral.cloudplusplus;


import bet.astral.cloudplusplus.annotations.Cloud;
import bet.astral.cloudplusplus.command.CPPCommand;
import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.receiver.Receiver;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.incendo.cloud.CommandManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public interface CommandRegisterer<C> {
	ArrayList<CPPCommand<?>> commands = new ArrayList<>();
	CommandManager<C> getCommandManager();
	Logger getSlf4jLogger();
	Messenger getMessenger();
	Receiver convertToReceiver(@NotNull C c);

	boolean isDebug();

	default void registerCommands(List<String> packages){
		for (String subPackage : packages){
			registerCommands(subPackage);
		}
	}

	default void registerCommands(String... pkg){
		for (String subPackage : pkg) {
			try (ScanResult scanResult = new ClassGraph()
					.enableAllInfo().acceptPackages(subPackage).scan()) {
				ClassInfoList classInfo = scanResult.getClassesWithAnnotation(Cloud.class);
				List<String> classes = classInfo.getNames();
				for (String clazzName : classes) {
					if (isDebug()) {
						getSlf4jLogger().info("Registering command: " + clazzName);
					}
					Class<?> clazz = Class.forName(clazzName);
					registerCommand(clazz);
					if (isDebug()) {
						getSlf4jLogger().info("Registered command: " + clazzName);
					}
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	default void registerCommand(Class<?> clazz) {
		/*
		 * This is the cloud command framework
		 */
		Constructor<?> constructor = null;
		try {
			constructor = getConstructor(clazz, this.getClass(), getCommandManager().getClass());
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		try {
			constructor.setAccessible(true);
			CPPCommand<?> reload = (CPPCommand<?>) constructor.newInstance(this, getCommandManager());
			commands.add(reload);
			if (isDebug()) {
				getSlf4jLogger().info("Loaded cloud command: " + clazz.getName());
			}
		} catch (InvocationTargetException | InstantiationException |
		         IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}


	default Constructor<?> getConstructor(Class<?> clazz, Class<?>... params) throws NoSuchMethodException {
		try {
			return clazz.getConstructor(params);
		} catch (NoSuchMethodException ignore) {
			return clazz.getDeclaredConstructor(params);
		}
	}
}
