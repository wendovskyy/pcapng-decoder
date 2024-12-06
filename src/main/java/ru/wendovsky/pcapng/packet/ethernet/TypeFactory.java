package ru.wendovsky.pcapng.packet.ethernet;

import ru.wendovsky.pcapng.reader.Reader;

import java.util.Map;
import java.util.function.Function;

public final class TypeFactory {
    private static final Map<Integer, Function<Reader, Type>> TYPE_INDEX_TO_TYPE =
            Map.of(
                    0x0800, IPv4::new
            );

    public Type instantiate(Reader reader) {
        int type = reader.readUnsignedShort();
        if (!TYPE_INDEX_TO_TYPE.containsKey(type)) {
            throw new UnsupportedOperationException("Type " + type + " not supported");
        }
        return TYPE_INDEX_TO_TYPE.get(type).apply(reader);
    }
}
