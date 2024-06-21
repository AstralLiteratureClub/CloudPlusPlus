package bet.astral.cloudplusplus.parsers;

import io.leangen.geantyref.TypeToken;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.component.CommandComponent;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.parser.aggregate.AggregateParser;
import org.incendo.cloud.parser.aggregate.AggregateParserTripletBuilder;
import org.incendo.cloud.parser.standard.FloatParser;

import java.awt.Color;
public class ColorParser<C> {
	public static <C> ParserDescriptor<C, Color> rgbParser(String name){
		//noinspection unchecked
		return (AggregateParser<C, Color>) AggregateParser.tripletBuilder(name+"-red", FloatParser.floatParser(0, 255), name+"-green", FloatParser.floatParser(0, 255), name+"-blue", FloatParser.floatParser(0, 255))
				.withDirectMapper(TypeToken.get(Color.class), new AggregateParserTripletBuilder.Mapper.DirectSuccessMapper<Object, Float, Float, Float, Color>() {
					@Override
					public @NonNull Color mapSuccess(@NonNull CommandContext<Object> commandContext, @NonNull Float firstResult, @NonNull Float secondResult, @NonNull Float thirdResult) {
						return new Color(firstResult, secondResult, thirdResult);
					}
				}).build();
	}
	public static <C> CommandComponent<C> rgbComponent(String name){
		return CommandComponent.<C, Color>builder().parser(rgbParser(name)).name(name).build();
	}
}