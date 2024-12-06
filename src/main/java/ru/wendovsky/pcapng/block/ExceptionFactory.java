package ru.wendovsky.pcapng.block;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.function.Function;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExceptionFactory {
    final String name;

    public void throwException(Function<String, RuntimeException> exceptionFactory, String message) {
        throw exception(exceptionFactory, message);
    }

    public RuntimeException exception(Function<String, RuntimeException> exceptionFactory, String message) {
        return exceptionFactory.apply(name + " | " + message);
    }
}
