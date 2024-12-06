package ru.wendovsky.pcapng.block;

import org.junit.jupiter.api.*;
import ru.wendovsky.pcapng.context.Context;
import ru.wendovsky.pcapng.environment.Environment;
import ru.wendovsky.pcapng.exception.PcapNGFileFormatException;
import ru.wendovsky.pcapng.reader.ByteBufferReader;
import ru.wendovsky.pcapng.reader.Reader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.jupiter.api.Assertions.*;

class SectionHeaderBlockTest {
    static final String STANDALONE = "STANDALONE";
    static SectionHeaderBlock block;

    @BeforeAll
    static void setUp(TestInfo testInfo) {
        if (standalone(testInfo)) {
            return;
        }
        Reader reader = Environment.bunnyReader();
        // type, length
        reader.skip(8);
        block = new SectionHeaderBlock(new Context(reader));
    }

    @Test
    @Tag(STANDALONE)
    void init_ShouldThrowException_WhenInvalidByteOrder() {
        ByteBuffer buffer = Environment.bunnyBuffer();
        buffer.putInt(8, 0);
        Reader reader = new ByteBufferReader(buffer);
        reader.skip(8);
        assertThrows(PcapNGFileFormatException.class, () -> new SectionHeaderBlock(new Context(reader)));
    }

    @Test
    void major() {
        assertEquals(1, block.major());
    }

    @Test
    void minor() {
        assertEquals(0, block.minor());
    }

    @Test
    void shbHardware() {
        assertEquals("Intel(R) Core(TM) i5-8250U CPU @ 1.60GHz (with SSE4.2)", block.shbHardware());
    }

    @Test
    void shbOs() {
        assertEquals("64-bit Windows 10 (1809), build 17763", block.shbOs());
    }

    @Test
    void shbUserApplication() {
        assertEquals("Dumpcap (Wireshark) 3.0.1 (v3.0.1-0-gea351cd8)", block.shbUserApplication());
    }

    static boolean standalone(TestInfo context) {
        return context.getTags().contains(STANDALONE);
    }
}