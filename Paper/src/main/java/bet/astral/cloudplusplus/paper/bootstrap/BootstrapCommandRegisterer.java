package bet.astral.cloudplusplus.paper.bootstrap;

import bet.astral.cloudplusplus.CommandRegisterer;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jetbrains.annotations.NotNull;

public interface BootstrapCommandRegisterer<C> extends CommandRegisterer<C> {
	@NotNull
	PaperCommandManager.Bootstrapped<C> getCommandManager();
	@NotNull
	BootstrapHandler getHandler();
}
