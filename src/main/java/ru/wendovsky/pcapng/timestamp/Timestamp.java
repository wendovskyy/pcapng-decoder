package ru.wendovsky.pcapng.timestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Timestamp {
    private static final int LOG_10_SECOND_TO_NANOS = 9;
    private static final int POWER_OF_2_MASK = 0b1000_0000;
    final long nanos;

    public Timestamp(int high, int low, int resolution) {
        long timestamp = calculateTimestamp(high, low);
        if ((resolution & POWER_OF_2_MASK) > 0) {
            throw new UnsupportedOperationException("Resolution that negative power of 2 unsupported now");
        }
        BigDecimal nanos = new BigDecimal(timestamp).movePointRight(LOG_10_SECOND_TO_NANOS - resolution);
        this.nanos = nanos.longValue();
    }

    private long calculateTimestamp(int high, int low) {
        return (((long) high) << 32) | (((long) low) & 0xFFFFFFFFL);
    }
}
