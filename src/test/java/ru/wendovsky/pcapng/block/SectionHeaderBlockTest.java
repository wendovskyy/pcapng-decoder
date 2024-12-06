package ru.wendovsky.pcapng.block;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import ru.wendovsky.pcapng.context.Context;
import ru.wendovsky.pcapng.environment.Environment;
import ru.wendovsky.pcapng.exception.PcapNGFileFormatException;
import ru.wendovsky.pcapng.reader.ByteBufferReader;
import ru.wendovsky.pcapng.reader.Reader;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.wendovsky.pcapng.util.TestUtils.STANDALONE;
import static ru.wendovsky.pcapng.util.TestUtils.standalone;

class SectionHeaderBlockTest {
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
}