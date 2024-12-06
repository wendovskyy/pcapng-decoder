package ru.wendovsky.pcapng.packet.ethernet;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.reader.Reader;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
@Getter
public class IPv4 implements Type {
    private static final ProtocolFactory PROTOCOL_FACTORY = new ProtocolFactory();
    final IP source;
    final IP destination;
    final Protocol protocol;

    public IPv4(Reader reader) {
        int versionAndIHL = reader.readUnsignedByte();
        int IHL = versionAndIHL & 0b1111;
        // DSCP & ECN
        reader.skip(1);
        // Total length
        reader.skip(2);
        // Identification
        reader.skip(2);
        // Flags & Fragment Offset
        reader.skip(2);
        // Time to live
        reader.skip(1);
        int protocol = reader.readUnsignedByte();
        // Header checksum
        reader.skip(2);
        source = new IP(reader.readInt());
        destination = new IP(reader.readInt());
        int optionsLength = Math.max((IHL - 5) * 32, 0) / 8;
        reader.skip(optionsLength);
        this.protocol = PROTOCOL_FACTORY.instantiate(protocol, reader);
    }
}
