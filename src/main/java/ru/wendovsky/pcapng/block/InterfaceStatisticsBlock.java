package ru.wendovsky.pcapng.block;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.context.Context;
import ru.wendovsky.pcapng.option.Options;
import ru.wendovsky.pcapng.reader.Reader;
import ru.wendovsky.pcapng.timestamp.Timestamp;

@Accessors(fluent = true)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InterfaceStatisticsBlock implements Block {
    final InterfaceDescriptionBlock interfaceDescriptionBlock;
    final Timestamp timestamp;

    public InterfaceStatisticsBlock(Context context) {
        Reader reader = context.reader();
        Lookup lookup = context.lookup();
        int interfaceId = reader.readInt();
        interfaceDescriptionBlock = lookup.findInterfaceDescriptionBlockById(interfaceId);
        timestamp = new Timestamp(reader.readInt(), reader.readInt(), interfaceDescriptionBlock.timeResolution());
        Options.createOptionIfMarkNotAchievedOrNull(reader);
    }
}
