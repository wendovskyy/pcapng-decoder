package ru.wendovsky.pcapng.block;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.PcapNG;
import ru.wendovsky.pcapng.context.Context;
import ru.wendovsky.pcapng.option.Options;
import ru.wendovsky.pcapng.packet.PacketData;
import ru.wendovsky.pcapng.packet.PacketDataFactory;
import ru.wendovsky.pcapng.reader.Reader;
import ru.wendovsky.pcapng.timestamp.Timestamp;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
public final class EnhancedPacketBlock implements Block {
    private static final PacketDataFactory PACKET_DATA_FACTORY = new PacketDataFactory();
    final InterfaceDescriptionBlock interfaceDescriptionBlock;
    final Timestamp timestamp;
    final PacketData packetData;

    public EnhancedPacketBlock(Context context) {
        Reader reader = context.reader();
        Lookup lookup = context.lookup();
        int interfaceId = reader.readInt();
        interfaceDescriptionBlock = lookup.findInterfaceDescriptionBlockById(interfaceId);
        timestamp = new Timestamp(reader.readInt(), reader.readInt(), interfaceDescriptionBlock.timeResolution());
        int capturedLength = reader.readInt();
        // Packet data length
        reader.skip(4);
        packetData = PACKET_DATA_FACTORY.instantiate(interfaceDescriptionBlock.linkType(), reader, capturedLength);
        reader.align(PcapNG.ALIGNMENT);
        Options.createOptionIfMarkNotAchievedOrNull(reader);
    }
}
