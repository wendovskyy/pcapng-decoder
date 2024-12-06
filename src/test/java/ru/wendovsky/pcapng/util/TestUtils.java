package ru.wendovsky.pcapng.util;

import org.junit.jupiter.api.TestInfo;

public class TestUtils {
    public static final String STANDALONE = "STANDALONE";

    public static boolean standalone(TestInfo context) {
        return context.getTags().contains(STANDALONE);
    }
}
