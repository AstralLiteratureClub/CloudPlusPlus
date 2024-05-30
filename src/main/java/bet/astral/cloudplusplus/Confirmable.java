package bet.astral.cloudplusplus;

import bet.astral.messenger.v2.delay.Delay;

import java.util.function.Consumer;

public interface Confirmable<C> {
	boolean hasRequestBending(C sender);
	boolean tryConfirm(C sender);
	void requestConfirm(C sender, Delay delay, Consumer<C> acceptedConsumer, Consumer<C> deniedConsumer, Consumer<C> timeRanOutConsumer);
}
