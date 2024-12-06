package ru.wendovsky.pcapng.block;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PrimaryLookupTest {
    @Mock
    InterfaceDescriptionBlock firstBlock;
    @Mock
    InterfaceDescriptionBlock secondBlock;

    @Test
    void findInterfaceDescriptionBlockById() {
        List<InterfaceDescriptionBlock> blocks = List.of(firstBlock, secondBlock);
        Lookup lookup = new PrimaryLookup(blocks);
        assertEquals(secondBlock, lookup.findInterfaceDescriptionBlockById(1));
    }
}