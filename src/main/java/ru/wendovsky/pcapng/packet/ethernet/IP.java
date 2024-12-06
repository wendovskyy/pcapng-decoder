package ru.wendovsky.pcapng.packet.ethernet;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IP {
    final int address;

    @Override
    public String toString() {
        return buildAddress();
    }

    private String buildAddress() {
        int p0 = address >>> 24;
        int p1 = (address >> 16) & 0xFF;
        int p2 = (address >> 8) & 0xFF;
        int p3 = address & 0xFF;
        return "%s.%s.%s.%s".formatted(p0, p1, p2, p3);
    }
}
