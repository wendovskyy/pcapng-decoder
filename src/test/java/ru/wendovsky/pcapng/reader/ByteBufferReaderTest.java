package ru.wendovsky.pcapng.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import ru.wendovsky.pcapng.exception.ReaderEndOfStreamException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ByteBufferReaderTest {
    static final String STANDALONE = "STANDALONE";
    static final String IGNORE_ORDER = "IGNORE_ORDER";
    static final String BE = "BE";
    static final String LE = "LE";
    ByteBufferReader reader;

    @BeforeEach
    void setUp(TestInfo context) {
        if (standalone(context)) {
            return;
        }
        byte[] data = {
                b(0xCA), b(0xFE), b(0xBA), b(0xBE),
                b(0xDE), b(0xAD), b(0xBE), b(0xEF)
        };
        reader = new ByteBufferReader(ByteBuffer.wrap(data));
        if (hasOrderInContext(context)) {
            reader.order(orderFromContext(context));
        }
    }

    @Test
    @Tag(STANDALONE)
    void readUTF8() {
        reader = new ByteBufferReader(ByteBuffer.wrap("ABCDEFж".getBytes(StandardCharsets.UTF_8)));
        assertEquals("ABCDEFж", reader.readUtf8(8));
    }

    @Test
    @Tag(LE)
    void order() {
        assertEquals(ByteOrder.LITTLE_ENDIAN, reader.order());
    }

    @Test
    @Tag(IGNORE_ORDER)
    void readUnsignedByte() {
        assertEquals(0xCA, reader.readUnsignedByte());
    }

    @Test
    @Tag(BE)
    void BE_readUnsignedByte() {
        assertEquals(0xCAFE, reader.readUnsignedShort());
    }

    @Test
    @Tag(LE)
    void LE_readUnsignedByte() {
        assertEquals(0xFECA, reader.readUnsignedShort());
    }

    @Test
    @Tag(BE)
    void BE_readInt() {
        assertEquals(0xCAFEBABE, reader.readInt());
    }

    @Test
    @Tag(LE)
    void LE_readInt() {
        assertEquals(0xBEBAFECA, reader.readInt());
    }

    @Test
    @Tag(BE)
    void BE_readLong() {
        assertEquals(0xCAFEBABEDEADBEEFL, reader.readLong());
    }

    @Test
    @Tag(LE)
    void LE_readLong() {
        assertEquals(0xEFBEADDEBEBAFECAL, reader.readLong());
    }

    @Test
    @Tag(IGNORE_ORDER)
    void readLong_ShouldThrowReaderEndOfStreamException_WhenEndOfStream() {
        reader.readLong();
        assertThrows(ReaderEndOfStreamException.class, () -> reader.readUnsignedByte());
    }

    @Test
    @Tag(IGNORE_ORDER)
    void align() {
        reader.align(4);
        assertEquals(0, reader.position());
        reader.skip(1);
        reader.align(4);
        assertEquals(4, reader.position());
    }

    @Test
    @Tag(IGNORE_ORDER)
    void endOfStream() {
        reader.skip(8);
        assertTrue(reader.endOfStream());
    }

    @Test
    @Tag(IGNORE_ORDER)
    void mark() {
        reader.mark(8);
        for (int i = 0; i < 7; i++) {
            reader.readUnsignedByte();
        }
        assertFalse(reader.markAchieved());
        reader.readUnsignedByte();
        assertTrue(reader.markAchieved());
    }

    boolean hasOrderInContext(TestInfo context) {
        return !context.getTags().contains("IGNORE_ORDER");
    }

    boolean standalone(TestInfo context) {
        return context.getTags().contains(STANDALONE);
    }

    ByteOrder orderFromContext(TestInfo testInfo) {
        if (testInfo.getTags().contains("LE")) {
            return ByteOrder.LITTLE_ENDIAN;
        } else if (testInfo.getTags().contains("BE")) {
            return ByteOrder.BIG_ENDIAN;
        }
        throw new IllegalArgumentException("Specify endian");
    }

    byte b(int value) {
        return (byte) value;
    }
}