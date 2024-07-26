package bet.astral.cloudplusplus.paper.mapper;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class NullSourceStack implements CommandSourceStack {
	public static final NullSourceStack INSTANCE = new NullSourceStack();
	private final CommandSender sender;
	{
		Class<?> clazz = null;
		try {
			clazz = Class.forName("io.papermc.paper.brigadier.NullCommandSender");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		try {
			Field field = clazz.getField("INSTANCE");
			sender = (CommandSender) field.get(null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	private final Location location = new Location(null, 0, 0, 0);
	@Override
	public @NotNull Location getLocation() {
		return location;
	}

	@Override
	public @NotNull CommandSender getSender() {
		return sender;
	}

	@Override
	public @Nullable Entity getExecutor() {
		return null;
	}
}
