package ru.wendovsky.pcapng.packet.ethernet;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.reader.Reader;

@Accessors(fluent = true)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class TCP implements Protocol {
    final int sourcePort;
    final int destinationPort;

    public TCP(Reader reader) {
        sourcePort = reader.readUnsignedShort();
        destinationPort = reader.readUnsignedShort();
        // Sequence number
        reader.skip(4);
        // Acknowledgement Number
        reader.skip(4);
        int dataOffset = reader.readUnsignedByte() >>> 4;
        // Flags
        reader.skip(1);
        // Window
        reader.skip(2);
        // Checksum
        reader.skip(2);
        // Urgent Pointer
        reader.skip(2);
        int optionsLength = Math.max((dataOffset - 5) * 32, 0) / 8;
        reader.skip(optionsLength);
    }
}
