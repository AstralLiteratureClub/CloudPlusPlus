package bet.astral.cloudplusplus.command;


import bet.astral.cloudplusplus.CommandRegisterer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.paper.PaperCommandManager;

@SuppressWarnings("removal")
public class CloudPPCommand<P extends JavaPlugin> {
	protected final PaperCommandManager<CommandSender> commandManager;
	protected final P plugin;
	protected final CommandRegisterer<P> registerer;


	public CloudPPCommand(P plugin, CommandRegisterer<P> registerer, PaperCommandManager<CommandSender> commandManager) {
		this.plugin = plugin;
		this.commandManager = commandManager;
		this.registerer = registerer;
	}
	public CloudPPCommand(P plugin, PaperCommandManager<CommandSender> commandManager){
		this.plugin = plugin;
		//noinspection PatternVariableHidesField
		if (!(plugin instanceof CommandRegisterer<?> registerer)){
			throw new RuntimeException("You cannot use JavaPlugin.class, PaperCommandManager.class constructor if you're not extending CommandRegisterer.class in "+plugin.getClass().getName());
		}
		//noinspection unchecked
		this.registerer = (CommandRegisterer<P>) registerer;
		this.commandManager = commandManager;
	}

}
