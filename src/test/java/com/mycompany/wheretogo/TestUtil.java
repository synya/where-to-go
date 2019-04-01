package com.mycompany.wheretogo;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtil {
    public static <T> void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static <T> void assertMatch(T actual, T expected, String... ignoreProperties) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, ignoreProperties);
    }

    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected, String... ignoreProperties) {
        assertThat(actual).usingElementComparatorIgnoringFields(ignoreProperties).isEqualTo(expected);
    }
}
