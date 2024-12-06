package ru.wendovsky.pcapng.context;

import ru.wendovsky.pcapng.block.lookup.Lookup;
import ru.wendovsky.pcapng.reader.Reader;

public record Context(Reader reader, Lookup lookup) {
}
