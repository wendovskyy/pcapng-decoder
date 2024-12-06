package ru.wendovsky.pcapng.block;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.PcapNG;
import ru.wendovsky.pcapng.context.Context;
import ru.wendovsky.pcapng.option.Options;
import ru.wendovsky.pcapng.reader.Reader;
import ru.wendovsky.pcapng.timestamp.Timestamp;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
public final class EnhancedPacketBlock implements Block {
    final InterfaceDescriptionBlock interfaceDescriptionBlock;
    final Timestamp timestamp;
    final int capturedLength;
    final int packetLength;
    final byte[] packetData;

    public EnhancedPacketBlock(Context context) {
        Reader reader = context.reader();
        Lookup lookup = context.lookup();
        int interfaceId = reader.readInt();
        interfaceDescriptionBlock = lookup.findInterfaceDescriptionBlockById(interfaceId);
        timestamp = new Timestamp(reader.readInt(), reader.readInt(), interfaceDescriptionBlock.timeResolution());
        capturedLength = reader.readInt();
        packetLength = reader.readInt();
        packetData = reader.readBytes(capturedLength);
        reader.align(PcapNG.ALIGNMENT);
        Options.createOptionIfMarkNotAchievedOrNull(reader);
    }
}
