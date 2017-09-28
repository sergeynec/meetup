package ru.necs.common.test.assertj;

public class Assertions {

    private Assertions() {
    }

    public static void failIfSuccess() {
        org.assertj.core.api.Assertions.fail("Unexpected success!!!");
    }
}
