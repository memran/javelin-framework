package app.views;

import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.extension.Filter;
import io.pebbletemplates.pebble.extension.Function;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class JavelinViewExtension extends AbstractExtension {
    @Override
    public Map<String, Filter> getFilters() {
        return Map.of("headline", new HeadlineFilter());
    }

    @Override
    public Map<String, Function> getFunctions() {
        return Map.of("javelin_version", new JavelinVersionFunction());
    }

    private static final class HeadlineFilter implements Filter {
        @Override
        public Object apply(Object input, Map<String, Object> args, io.pebbletemplates.pebble.template.PebbleTemplate self,
                            io.pebbletemplates.pebble.template.EvaluationContext context, int lineNumber) {
            if (input == null) {
                return "";
            }
            String value = input.toString().replace('-', ' ').replace('_', ' ');
            String[] words = value.split("\\s+");
            StringBuilder headline = new StringBuilder();
            for (String word : words) {
                if (word.isBlank()) {
                    continue;
                }
                if (!headline.isEmpty()) {
                    headline.append(' ');
                }
                headline.append(word.substring(0, 1).toUpperCase(Locale.ROOT));
                if (word.length() > 1) {
                    headline.append(word.substring(1).toLowerCase(Locale.ROOT));
                }
            }
            return headline.toString();
        }

        @Override
        public List<String> getArgumentNames() {
            return List.of();
        }
    }

    private static final class JavelinVersionFunction implements Function {
        @Override
        public Object execute(Map<String, Object> args, io.pebbletemplates.pebble.template.PebbleTemplate self,
                              io.pebbletemplates.pebble.template.EvaluationContext context, int lineNumber) {
            return "0.1.0-SNAPSHOT";
        }

        @Override
        public List<String> getArgumentNames() {
            return List.of();
        }
    }
}
