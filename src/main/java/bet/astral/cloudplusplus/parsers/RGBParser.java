package bet.astral.cloudplusplus.parsers;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionProvider;

import java.awt.Color;
import java.util.concurrent.CompletableFuture;

public class RGBParser<C> implements ArgumentParser<C, Color>, SuggestionProvider<C> {
	@Override
	public @NonNull ArgumentParseResult<@NonNull Color> parse(@NonNull CommandContext<@NonNull C> commandContext, @NonNull CommandInput commandInput) {
		return null;
	}

	@Override
	public @NonNull CompletableFuture<@NonNull Iterable<@NonNull Suggestion>> suggestionsFuture(@NonNull CommandContext<C> context, @NonNull CommandInput input) {
		return null;
	}
}
