package bet.astral.cloudplusplus.command;

import bet.astral.cloudplusplus.CommandRegisterer;
import bet.astral.cloudplusplus.Confirmable;
import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.task.ITask;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.type.tuple.Triplet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class CPPConfirmableCommand<C> extends CPPCommand<C> implements Confirmable<C> {
	private final Map<C, Triplet<AtomicReference<ITask>, Consumer<C>, Consumer<C>>> confirmable = new HashMap<>();

	public CPPConfirmableCommand(CommandRegisterer<C> registerer, CommandManager<C> commandManager) {
		super(registerer, commandManager);
	}

	@Override
	public boolean hasRequestBending(C sender) {
		return confirmable.get(sender) != null && !confirmable.get(sender).first().get().isCanceled();
	}

	@Override
	public boolean tryConfirm(C sender) {
		if (hasRequestBending(sender)) {
			Triplet<AtomicReference<ITask>, Consumer<C>, Consumer<C>> triple = confirmable.get(sender);
			triple.first().get().cancel();
			triple.second().accept(sender);
			confirmable.remove(sender);
			return true;
		}
		return false;
	}

	@Override
	public void requestConfirm(C sender, Delay delay, Consumer<C> acceptedConsumer, Consumer<C> deniedConsumer, Consumer<C> timeRanOutConsumer) {
		confirmable.put(sender, Triplet.of(new AtomicReference<>(), acceptedConsumer, deniedConsumer));
		messenger.convertReceiver(sender).getScheduler().runLater((task) -> {
			confirmable.get(sender).first().set(task);
			timeRanOutConsumer.accept(sender);
			confirmable.remove(sender);
		}, delay);
	}
}