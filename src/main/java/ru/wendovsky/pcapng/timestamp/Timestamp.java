package ru.wendovsky.pcapng.timestamp;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Timestamp {
    final long timestamp;
    final int resolution;

    public Timestamp(int high, int low, int resolution) {
        timestamp = calculateTimestamp(high, low);
        this.resolution = resolution;
    }

    private long calculateTimestamp(int high, int low) {
        return (((long) high) << 32) | (((long) low) & 0xFFFFFFFFL);
    }
}
