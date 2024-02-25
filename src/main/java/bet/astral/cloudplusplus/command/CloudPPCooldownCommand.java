package bet.astral.cloudplusplus.command;

import bet.astral.cloudplusplus.Cooldown;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.paper.PaperCommandManager;

import java.util.HashMap;
import java.util.Map;

public class CloudPPCooldownCommand<P extends JavaPlugin> extends CloudPPCommand<P> implements Cooldown, Listener {
	private final Map<CommandSender, Long> cooldowns = new HashMap<>();
	private final long cooldown;

	public CloudPPCooldownCommand(P plugin, PaperCommandManager<CommandSender> commandManager, long cooldown) {
		super(plugin, commandManager);
		this.cooldown = cooldown;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@Override
	public void setCooldown(CommandSender sender) {
		cooldowns.put(sender, System.currentTimeMillis()+cooldown);
	}

	@Override
	public void resetCooldown(CommandSender sender) {
		cooldowns.remove(sender);
	}

	@Override
	public long getCooldownLeft(CommandSender sender) {
		long left = (cooldowns.get(sender) != null ? cooldowns.get(sender) : 0) - System.currentTimeMillis();
		if (left<0){
			left = 0;
		}
		if (left==0){
			resetCooldown(sender);
		}
		return left;
	}

	@Override
	public boolean hasCooldown(CommandSender sender) {
		return getCooldownLeft(sender)>0;
	}

	@EventHandler
	private void onPlayerLeave(PlayerQuitEvent event){
		cooldowns.remove(event.getPlayer());
	}
}
