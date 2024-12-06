package ru.wendovsky.pcapng.reader;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.exception.ReaderEndOfStreamException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class ByteBufferReader implements Reader {
    @NonNull
    final ByteBuffer buffer;

    @Override
    public int readUnsignedByte() throws ReaderEndOfStreamException {
        guaranteeMoreFreeBytes(1);
        return buffer.get() & 0xFF;
    }

    @Override
    public int readUnsignedShort() throws ReaderEndOfStreamException {
        guaranteeMoreFreeBytes(2);
        return buffer.getShort() & 0xFFFF;
    }

    @Override
    public int readInt() throws ReaderEndOfStreamException {
        guaranteeMoreFreeBytes(4);
        return buffer.getInt();
    }

    @Override
    public long readLong() throws ReaderEndOfStreamException {
        guaranteeMoreFreeBytes(8);
        return buffer.getLong();
    }

    @Override
    public byte[] readBytes(int length) {
        guaranteeMoreFreeBytes(length);
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return bytes;
    }

    @Override
    public String readUtf8(int length) {
        return new String(readBytes(length), StandardCharsets.UTF_8);
    }

    @Override
    public void skip(int bytes) {
        offset(bytes);
    }

    @Override
    public void order(ByteOrder order) throws NullPointerException {
        Objects.requireNonNull(order, "Order must not be null");
        buffer.order(order);
    }

    @Override
    public void align(int alignment) {
        int bytesToAlignment = buffer.position() % alignment;
        offset((alignment - bytesToAlignment) % alignment);
    }

    @Override
    public int position() {
        return buffer.position();
    }

    @Override
    public boolean endOfStream() {
        return !buffer.hasRemaining();
    }

    private void guaranteeMoreFreeBytes(int length) throws ReaderEndOfStreamException {
        if (buffer.position() + length > buffer.limit()) {
            throw new ReaderEndOfStreamException("End of stream");
        }
    }

    private void offset(int offset) {
        buffer.position(buffer.position() + offset);
    }
}
