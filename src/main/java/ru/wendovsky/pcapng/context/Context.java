package ru.wendovsky.pcapng.context;

import ru.wendovsky.pcapng.block.Lookup;
import ru.wendovsky.pcapng.reader.Reader;

public record Context(Reader reader, Lookup lookup) {
}
