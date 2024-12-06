package ru.wendovsky.pcapng.packet.ethernet;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.packet.PacketData;
import ru.wendovsky.pcapng.reader.Reader;

import java.nio.ByteOrder;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class EthernetPacketData implements PacketData {
    private static final TypeFactory TYPE_FACTORY = new TypeFactory();
    final Mac source;
    final Mac destination;
    final Type type;
    final byte[] data;

    public EthernetPacketData(Reader reader, int capturedLength) {
        int initialPosition = reader.position();
        ByteOrder previousOrder = reader.order();
        reader.order(ByteOrder.BIG_ENDIAN);
        destination = new Mac(reader.readBytes(6));
        source = new Mac(reader.readBytes(6));
        type = TYPE_FACTORY.instantiate(reader);
        int headerSize = reader.position() - initialPosition;
        data = reader.readBytes(capturedLength - headerSize);
        reader.order(previousOrder);
    }
}
