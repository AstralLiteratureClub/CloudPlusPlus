package bet.astral.cloudplusplus.command;

import bet.astral.cloudplusplus.CommandRegisterer;
import bet.astral.messenger.v2.Messenger;
import org.incendo.cloud.Command;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.description.Description;

public class CPPCommand<C> {
	protected final CommandManager<C> commandManager;
	protected final CommandRegisterer<C> registerer;
	protected Messenger messenger;

	public CPPCommand(CommandRegisterer<C> registerer, CommandManager<C> commandManager){
		this.registerer = registerer;
		this.commandManager = commandManager;
	}

	public void command(Command.Builder<C> command){
		commandManager.command(command);
	}

	public Command.Builder<C> commandBuilder(String name,
	                                                     Description description,
	                                                     String... aliases
	                                                     ){
		return commandManager.commandBuilder(name, description, aliases);
	}
	public Command.Builder<C> commandBuilder(String name,
	                                                     String... aliases){
		return commandManager.commandBuilder(name, aliases);
	}
}
