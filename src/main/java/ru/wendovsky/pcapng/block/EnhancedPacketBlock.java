package ru.wendovsky.pcapng.block;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.PcapNG;
import ru.wendovsky.pcapng.context.Context;
import ru.wendovsky.pcapng.option.Options;
import ru.wendovsky.pcapng.reader.Reader;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
public final class EnhancedPacketBlock implements Block {
    final InterfaceDescriptionBlock interfaceDescriptionBlock;
    final int timestampHigh;
    final int timestampLow;
    final int capturedLength;
    final int packetLength;
    final byte[] packetData;

    public EnhancedPacketBlock(Context context) {
        Reader reader = context.reader();
        Lookup lookup = context.lookup();
        int interfaceId = reader.readInt();
        interfaceDescriptionBlock = lookup.findInterfaceDescriptionBlockById(interfaceId);
        timestampHigh = reader.readInt();
        timestampLow = reader.readInt();
        capturedLength = reader.readInt();
        packetLength = reader.readInt();
        packetData = reader.readBytes(capturedLength);
        reader.align(PcapNG.ALIGNMENT);
        Options.createOptionIfMarkNotAchievedOrNull(reader);
    }
}
