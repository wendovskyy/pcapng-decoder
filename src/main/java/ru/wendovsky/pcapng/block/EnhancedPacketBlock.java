package ru.wendovsky.pcapng.block;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.option.Options;
import ru.wendovsky.pcapng.reader.Reader;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
public final class EnhancedPacketBlock implements Block {
    final int interfaceId;
    final int timestampHigh;
    final int timestampLow;
    final int capturedLength;
    final int packetLength;
    final byte[] packetData;

    public EnhancedPacketBlock(Reader reader) {
        interfaceId = reader.readInt();
        timestampHigh = reader.readInt();
        timestampLow = reader.readInt();
        capturedLength = reader.readInt();
        packetLength = reader.readInt();
        packetData = reader.readBytes(capturedLength);
        reader.align(4);
        if (reader.markAchieved()) {
            return;
        }
        new Options(reader);
    }
}
