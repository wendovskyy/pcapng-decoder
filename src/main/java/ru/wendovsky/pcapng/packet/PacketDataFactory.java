package ru.wendovsky.pcapng.packet;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.block.LinkType;
import ru.wendovsky.pcapng.packet.ethernet.EthernetPacketData;
import ru.wendovsky.pcapng.reader.Reader;

import java.util.Map;
import java.util.function.BiFunction;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public final class PacketDataFactory {
    private static final Map<LinkType, BiFunction<Reader, Integer, PacketData>> LINK_TYPE_TO_PACKET_DATA_FACTORY =
            Map.of(LinkType.ETHERNET, EthernetPacketData::new);

    public PacketData instantiate(LinkType linkType, Reader reader, int capturedLength) {
        if (!LINK_TYPE_TO_PACKET_DATA_FACTORY.containsKey(linkType)) {
            throw new UnsupportedOperationException("Unsupported link type: " + linkType);
        }
        return LINK_TYPE_TO_PACKET_DATA_FACTORY.get(linkType).apply(reader, capturedLength);
    }
}
