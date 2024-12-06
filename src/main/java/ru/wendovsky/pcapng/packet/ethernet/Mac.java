package ru.wendovsky.pcapng.packet.ethernet;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode
public final class Mac {
    final String mac;

    public Mac(byte[] bytes) {
        if (bytes.length != 6) {
            throw new IllegalArgumentException("MAC length must be 6 bytes");
        }
        this.mac = macFromBytes(bytes);
    }

    private String macFromBytes(byte[] bytes) {
        StringBuilder macBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            macBuilder.append(Integer.toHexString(aByte & 0xFF)).append(":");
        }
        String mac = macBuilder.toString();
        return mac.substring(0, mac.length() - 1).toUpperCase();
    }
}
