package ru.wendovsky.pcapng.packet.ethernet;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.reader.Reader;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
@Getter
public final class UDP implements Protocol {
    final int sourcePort;
    final int destinationPort;

    public UDP(Reader reader) {
        sourcePort = reader.readUnsignedShort();
        destinationPort = reader.readUnsignedShort();
        // Length
        reader.skip(2);
        // Checksum
        reader.skip(2);
    }
}
