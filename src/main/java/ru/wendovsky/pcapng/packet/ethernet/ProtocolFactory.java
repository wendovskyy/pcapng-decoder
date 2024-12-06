package ru.wendovsky.pcapng.packet.ethernet;

import ru.wendovsky.pcapng.reader.Reader;

import java.util.Map;
import java.util.function.Function;

public final class ProtocolFactory {
    private static final Map<Integer, Function<Reader, Protocol>> PROTOCOL_ID_TO_PROTOCOL_FACTORY =
            Map.of(
                    6, TCP::new,
                    17, UDP::new,
                    1, ICMP::new
            );

    public Protocol instantiate(int protocol, Reader reader) {
        if (!PROTOCOL_ID_TO_PROTOCOL_FACTORY.containsKey(protocol)) {
            throw new UnsupportedOperationException("Unknown protocol: " + protocol);
        }
        return PROTOCOL_ID_TO_PROTOCOL_FACTORY.get(protocol).apply(reader);
    }
}
