package ru.wendovsky.pcapng.timestamp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimestampTest {
    @Test
    void init() {
        Timestamp timestamp = new Timestamp(0x628A4, 0x0BC32188, 6);
        assertEquals(1733534897349000000L, timestamp.nanos());
    }
}