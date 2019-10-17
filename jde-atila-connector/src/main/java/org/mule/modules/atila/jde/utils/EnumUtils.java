package org.mule.modules.atila.jde.utils;

public class EnumUtils {

    private EnumUtils() {
    }

    public static <T extends Enum<T>> T getEnumFromString(final Class<T> enumType, final String string) {
        if (enumType != null && string != null) {
            for (T v : enumType.getEnumConstants()) {
                if (v.toString()
                        .equalsIgnoreCase(string)) {
                    return v;
                }
            }
        }
        return null;
    }
}
