package bet.astral.cloudplusplus.minecraft.paper.bootstrap;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;

public class BootstrapHandler implements Iterable<InitAfterBootstrap>{
	private final HashSet<InitAfterBootstrap> initAfterBootstraps = new HashSet<>();

	public void add(@NotNull InitAfterBootstrap bootstrap){
		this.initAfterBootstraps.add(bootstrap);
	}

	public void remove(@NotNull InitAfterBootstrap bootstrap){
		this.initAfterBootstraps.remove(bootstrap);
	}

	public void clear() {
		this.initAfterBootstraps.clear();
	}

	@Override
	public @NotNull Iterator<InitAfterBootstrap> iterator() {
		return initAfterBootstraps.iterator();
	}

	public void init(){
		for (InitAfterBootstrap initAfterBootstrap : this){
			initAfterBootstrap.init();
		}
	}
}
