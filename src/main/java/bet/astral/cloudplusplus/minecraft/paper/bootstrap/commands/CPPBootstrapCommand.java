package bet.astral.cloudplusplus.minecraft.paper.bootstrap.commands;

import bet.astral.cloudplusplus.CommandRegisterer;
import bet.astral.cloudplusplus.command.CPPCommand;
import bet.astral.cloudplusplus.minecraft.paper.bootstrap.BootstrapCommandRegisterer;
import bet.astral.cloudplusplus.minecraft.paper.bootstrap.InitAfterBootstrap;
import org.incendo.cloud.CommandManager;

public class CPPBootstrapCommand<C> extends CPPCommand<C> {
	public CPPBootstrapCommand(CommandRegisterer<C> registerer, CommandManager<C> commandManager) {
		super(registerer, commandManager);
		if (this instanceof InitAfterBootstrap initAfterBootstrap && registerer instanceof BootstrapCommandRegisterer<C> bootstrapCommandRegisterer){
			bootstrapCommandRegisterer.getHandler().add(initAfterBootstrap);
		}
	}
}
