package bet.astral.cloudplusplus.minecraft.paper.bootstrap.commands;

import bet.astral.cloudplusplus.CommandRegisterer;
import bet.astral.cloudplusplus.command.CPPConfirmableCommand;
import bet.astral.cloudplusplus.minecraft.paper.bootstrap.BootstrapCommandRegisterer;
import bet.astral.cloudplusplus.minecraft.paper.bootstrap.InitAfterBootstrap;
import org.incendo.cloud.CommandManager;

public class CPPBootstrapConfirmableCommand<C> extends CPPConfirmableCommand<C> {
	public CPPBootstrapConfirmableCommand(CommandRegisterer<C> registerer, CommandManager<C> commandManager) {
		super(registerer, commandManager);
		if (this instanceof InitAfterBootstrap initAfterBootstrap && registerer instanceof BootstrapCommandRegisterer<C> bootstrapCommandRegisterer){
			bootstrapCommandRegisterer.getHandler().add(initAfterBootstrap);
		}
	}
}
