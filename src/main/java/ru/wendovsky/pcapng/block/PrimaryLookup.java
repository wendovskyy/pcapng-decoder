package ru.wendovsky.pcapng.block;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.exception.PcapNGFileFormatException;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class PrimaryLookup implements Lookup {
    @NonNull
    final List<? extends Block> blocks;

    @Override
    public InterfaceDescriptionBlock findInterfaceDescriptionBlockById(int id) {
        try {
            return findBlocksByClass(InterfaceDescriptionBlock.class).get(id);
        } catch (IndexOutOfBoundsException exception) {
            throw new PcapNGFileFormatException("Invalid IDB index %s".formatted(id));
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Block> List<? extends T> findBlocksByClass(Class<T> clazz) {
        return (List<? extends T>) blocks
                .stream()
                .filter(block -> block.getClass() == clazz)
                .toList();
    }
}
