package ru.wendovsky.pcapng.exception;

public final class ReaderEndOfStreamException extends RuntimeException {
    public ReaderEndOfStreamException(String message) {
        super(message);
    }
}
