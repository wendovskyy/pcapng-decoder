package ru.wendovsky.pcapng.block;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionFactoryTest {

    @Test
    void throwException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ExceptionFactory("123").throwException(IllegalArgumentException::new, "321");
        });
    }
}