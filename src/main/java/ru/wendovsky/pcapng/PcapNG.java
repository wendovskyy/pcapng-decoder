package ru.wendovsky.pcapng;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.block.*;
import ru.wendovsky.pcapng.block.lookup.Lookup;
import ru.wendovsky.pcapng.block.lookup.PrimaryLookup;
import ru.wendovsky.pcapng.context.Context;
import ru.wendovsky.pcapng.exception.PcapNGFileFormatException;
import ru.wendovsky.pcapng.reader.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class PcapNG implements Lookup {
    // Block type, length, length
    private static final int META_BYTES_PER_BLOCK_COUNT = 12;
    public static final int ALIGNMENT = 4;
    private static final Map<Integer, Function<Context, Block>> BLOCK_TYPE_TO_BLOCK_FACTORY = Map.of(
            0x0A0D0D0A, SectionHeaderBlock::new,
            0x00000001, InterfaceDescriptionBlock::new,
            0x00000006, EnhancedPacketBlock::new,
            0x00000005, InterfaceStatisticsBlock::new
    );
    @Getter
    final List<Block> blocks;
    @Delegate
    final Lookup lookup;

    public PcapNG(Reader reader) {
        blocks = parseBlocks(reader);
        lookup = new PrimaryLookup(blocks);
    }

    private List<Block> parseBlocks(Reader reader) {
        List<Block> blocks = new ArrayList<>();
        Lookup lookup = new PrimaryLookup(blocks);
        while (!reader.endOfStream()) {
            int blockType = reader.readInt();
            int blockLength = lengthOfBlock(reader) - META_BYTES_PER_BLOCK_COUNT;
            // Mark end of block
            reader.mark(blockLength + reader.position());
            blocks.add(blockByBlockType(new Context(reader, lookup), blockType));
            skipLengthOfBlock(reader);
        }
        return blocks;
    }


    private void skipLengthOfBlock(Reader reader) {
        lengthOfBlock(reader);
    }

    private int lengthOfBlock(Reader reader) {
        return reader.readInt();
    }

    private Block blockByBlockType(Context context, int blockType) {
        if (!BLOCK_TYPE_TO_BLOCK_FACTORY.containsKey(blockType)) {
            throw new PcapNGFileFormatException("Unknown block type: " + blockType);
        }
        return BLOCK_TYPE_TO_BLOCK_FACTORY.get(blockType).apply(context);
    }
}
