package bet.astral.cloudplusplus.minecraft.paper;

import bet.astral.cloudplusplus.CommandRegisterer;
import org.incendo.cloud.paper.PaperCommandManager;

public interface PaperCommandRegisterer<C> extends CommandRegisterer<C>{
	@Override
	PaperCommandManager<C> getCommandManager();
}
