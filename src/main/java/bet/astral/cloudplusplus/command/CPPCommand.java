package bet.astral.cloudplusplus.command;

import bet.astral.cloudplusplus.CommandRegisterer;
import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.translation.TranslationKey;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.Command;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.minecraft.extras.RichDescription;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Function;

public class CPPCommand<C> {
	protected final CommandManager<C> commandManager;
	protected final CommandRegisterer<C> registerer;
	protected Locale locale = Locale.US;
	protected Messenger messenger;

	public CPPCommand(CommandRegisterer<C> registerer, CommandManager<C> commandManager){
		this.registerer = registerer;
		this.commandManager = commandManager;
		this.messenger = registerer.getMessenger();
	}

	public void command(Command.Builder<? extends C> command){
		commandManager.command(command);
	}

	public RegistrableCommand<? extends C> command(@NotNull String name,
	                                    @NotNull TranslationKey descriptionKey,
	                                    @NotNull Function<Command.Builder<C>, Command.Builder<? extends C>> builder,
	                                    @NotNull String... aliases){
		return command(name,
				aliases,
				descriptionKey,
				builder);
	}
	public RegistrableCommand<? extends C> command(@NotNull String name,
	                                    @NotNull String[] args,
	                                    @NotNull TranslationKey descriptionKey,
	                                    @NotNull Function<Command.Builder<C>, Command.Builder<? extends C>> builder){
		return command(name, args, loadDescription(descriptionKey), builder);
	}

	public RegistrableCommand<? extends C> command(@NotNull String name,
	                                     @NotNull Description description,
	                                     @NotNull Function<Command.Builder<C>, Command.Builder<? extends C>> builder,
	                                     @NotNull String... aliases){
		return command(name,
				aliases,
				description,
				builder);
	}
	public RegistrableCommand<? extends C> command(@NotNull String name,
	                                     @NotNull String[] args,
	                                     @NotNull Description description,
	                                     @NotNull Function<Command.Builder<C>, Command.Builder<? extends C>> builder){
		Command.Builder<C> bldr = commandManager.commandBuilder(name, description, args).commandDescription(description);
		return new RegistrableCommand<>(commandManager, builder.apply(bldr));
	}


	public RichDescription loadDescription(@NotNull TranslationKey translationKey){
		Component component = messenger.parseComponent(translationKey, locale, ComponentType.CHAT);
		if (component == null){
			return RichDescription.translatable(translationKey.getKey());
		}
		return RichDescription.of(component);
	}

	public static class RegistrableCommand<C> {
		final CommandManager<C> commandManager;
		final Command.Builder<? extends C> builder;

		public RegistrableCommand(CommandManager<C> commandManager, Command.Builder<? extends C> builder) {
			this.commandManager = commandManager;
			this.builder = builder;
		}

		public void register(){
			commandManager.command(builder);
		}
	}
}
