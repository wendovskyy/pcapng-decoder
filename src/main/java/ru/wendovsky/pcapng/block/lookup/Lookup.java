package ru.wendovsky.pcapng.block.lookup;

import ru.wendovsky.pcapng.block.InterfaceDescriptionBlock;

public interface Lookup {
    InterfaceDescriptionBlock findInterfaceDescriptionBlockById(int id);
}
