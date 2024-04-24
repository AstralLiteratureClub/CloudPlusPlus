# CloudPlusPlus (CPP)
CloudPlusPlus is designed to be able to reflect CLOUD commands,
so a developer does not need to initialize each class themselves.

## How to use it?

Creating the main class
```java
package bet.astral.example;

import bet.astral.cloudplusplus.CommandRegisterer;
import bet.astral.messenger.Messenger;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

import java.util.List;

public class ExamplePlugin extends JavaPlugin implements CommandRegisterer<ExamplePlugin> {
	private Messenger<ExamplePlugin> messenger;
	private PaperCommandManager<CommandSender> commandManager;

	@Override
	public void onEnable() {
		messenger = new Messenger<>(...);

		commandManager = new PaperCommandManager<>(
				this,
				ExecutionCoordinator.asyncCoordinator(),
				SenderMapper.identity());
		commandManager.registerBrigadier();
		commandManager.registerAsynchronousCompletions();

		registerCommands(
				List.of("bet.astral.example.commands"),
				commandManager);
	}

	@Override
	public ExamplePlugin plugin() {
		return this;
	}

	@Override
	public Messenger<ExamplePlugin> commandMessenger() {
		return messenger;
	}

	@Override
	public Messenger<ExamplePlugin> debugMessenger() {
		return messenger;
	}
}
```

Creating an example command which sends "Hey!" to the player.
```java
package bet.astral.example.commands;

import bet.astral.cloudplusplus.annotations.Cloud;
import bet.astral.cloudplusplus.command.CloudPPCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.paper.PaperCommandManager;

// Add @Cloud annotation so the reflection methods find this class.
@Cloud
public class ExampleCommand extends CloudPPCommand<JavaPlugin, CommandSender> {
	public ExampleCommand(JavaPlugin plugin, PaperCommandManager<CommandSender> commandManager) {
		super(plugin, commandManager);
		command(
				commandBuilder("hello")
						.handler(context->{
							context.sender().sendMessage("Hey!");
						})
		);
	}
}
```

Example command extending CloudPPCommand to have easier commands
(as not needing to extend CloudPPCommand<Main, CommandSender>)
```java
package bet.astral.example.commands;

import bet.astral.cloudplusplus.command.CloudPPCommand;
import bet.astral.example.ExamplePlugin;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.paper.PaperCommandManager;

public class ExampleExtendedCommand extends CloudPPCommand<ExamplePlugin, CommandSender> {
	public ExampleExtendedCommand(ExamplePlugin plugin, PaperCommandManager commandManager) {
		super(plugin, commandManager);
	}
}
```

Using the extended command
```java
package bet.astral.example.commands;

import bet.astral.example.ExamplePlugin;
import org.incendo.cloud.paper.PaperCommandManager;

public class ExampleExtendedCommandUsageCommand extends ExampleExtendedCommand{
	public ExampleExtendedCommandUsageCommand(ExamplePlugin plugin, PaperCommandManager commandManager) {
		super(plugin, commandManager);
		command(
				commandBuilder("exampled!")
						.handler(context->context.sender().sendMessage("Super example!"))
		);
	}
}
```

## Requirements
 - [Paper](https://github.com/PaperMC/Paper)
 - [Cloud-Core](https://github.com/Incendo/cloud) [(Docs)](https://cloud.incendo.org/core/)
 - [Cloud-Paper](https://github.com/Incendo/cloud-minecraft) [(Docs)](https://cloud.incendo.org/minecraft/bukkit/)
 - [Messenger](https://github.com/AstralLiteratureClub/MessageManager/)
