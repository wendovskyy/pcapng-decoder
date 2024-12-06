package ru.wendovsky.pcapng.environment;

import lombok.Cleanup;
import lombok.SneakyThrows;
import ru.wendovsky.pcapng.reader.ByteBufferReader;
import ru.wendovsky.pcapng.reader.Reader;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Objects;

public class Environment {
    @SneakyThrows
    public static ByteBuffer bunnyBuffer() {
        @Cleanup
        InputStream stream = Environment.class.getResourceAsStream("/bunny.pcapng");
        Objects.requireNonNull(stream);
        return ByteBuffer.wrap(stream.readAllBytes());
    }

    public static Reader bunnyReader() {
        return new ByteBufferReader(bunnyBuffer());
    }
}
