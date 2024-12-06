package ru.wendovsky.pcapng;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.block.InterfaceDescriptionBlock;
import ru.wendovsky.pcapng.block.SectionHeaderBlock;
import ru.wendovsky.pcapng.exception.PcapNGFileFormatException;
import ru.wendovsky.pcapng.reader.Reader;
import ru.wendovsky.pcapng.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class PcapNG {
    public static final int ALIGNMENT = 4;
    private static final Map<Integer, Function<Reader, Block>> BLOCK_TYPE_TO_BLOCK_FACTORY = Map.of(
            0x0A0D0D0A, SectionHeaderBlock::new,
            0x00000001, InterfaceDescriptionBlock::new
    );
    @Getter
    final List<Block> blocks;

    public PcapNG(Reader reader) {
        blocks = parseBlocks(reader);
    }

    private List<Block> parseBlocks(Reader reader) {
        List<Block> blocks = new ArrayList<>();
        while (!reader.endOfStream()) {
            int blockType = reader.readInt();
            parseLengthOfBlock(reader);
            blocks.add(blockByBlockType(reader, blockType));
            parseLengthOfBlock(reader);
        }
        return blocks;
    }

    private void parseLengthOfBlock(Reader reader) {
        // Skip the length of block
        reader.skip(4);
    }

    private Block blockByBlockType(Reader reader, int blockType) {
        if (!BLOCK_TYPE_TO_BLOCK_FACTORY.containsKey(blockType)) {
            throw new PcapNGFileFormatException("Unknown block type: " + blockType);
        }
        return BLOCK_TYPE_TO_BLOCK_FACTORY.get(blockType).apply(reader);
    }
}
