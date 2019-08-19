package vault.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Util {
	public static String removeTrailingSlash(String string) {
		if (string != null && string.endsWith("/")) {
			return string.substring(0, string.length()-1);
		}

		return string;
	}

	public static <T> T checkAllowedValue(String fieldName, T object, List<T> allowedValues) {
		if (allowedValues == null || allowedValues.isEmpty()) {
			throw new NullPointerException("Allowed Values list must not be null or empty.");
		}

		if (object != null && allowedValues.contains(object)) {
			return object;
		}

		throw new IllegalArgumentException("The only allowed values for " + fieldName + " are: " + allowedValues.stream().map(Object::toString).collect(Collectors.joining(",")));
	}

	@SafeVarargs
	public static <T> T checkAllowedValue(String fieldName, T object, T... allowedValues) {
		if (allowedValues == null) {
			throw new NullPointerException("Allowed Values list must not be null.");
		}

		return checkAllowedValue(fieldName, object, Arrays.asList(allowedValues));
	}

	public static <T> List<T> copyList(List<T> list) {
		if (list != null) {
			return new ArrayList<>(list);
		}

		return Collections.emptyList();
	}

	public static <K, V> Map<K, V> copyMap(Map<K, V> map) {
		if (map != null) {
			return new HashMap<>(map);
		}

		return Collections.emptyMap();
	}

	public static void checkArgumentCondition(boolean condition, String message) {
		if (!condition) {
			throw new IllegalArgumentException(message);
		}
	}

	private Util() {
		throw new UnsupportedOperationException("Cannot instantiate the Utility Class " + Util.class + ".");
	}
}