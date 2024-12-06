package ru.wendovsky.pcapng.block;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.context.Context;
import ru.wendovsky.pcapng.exception.PcapNGFileFormatException;
import ru.wendovsky.pcapng.option.Options;
import ru.wendovsky.pcapng.reader.Reader;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
public final class InterfaceDescriptionBlock implements Block {
    private static final int IF_TIMESTAMP_RESOLUTION = 9;
    private static final ExceptionFactory EXCEPTION_FACTORY = new ExceptionFactory("InterfaceDescriptionBlock");
    private static final Map<Integer, LinkType> LINK_TYPE_MAP = new HashMap<>();
    final LinkType linkType;
    final int snapLength;
    final int timeResolution;

    public InterfaceDescriptionBlock(Context context) {
        Reader reader = context.reader();
        linkType = linkTypeById(reader.readUnsignedShort());
        parseReserved(reader);
        snapLength = reader.readInt();
        Options options = Options.createOptionIfMarkNotAchievedOrNull(reader);
        int timeResolution = 6;
        if (options != null) {
            timeResolution = options.unsignedIntByCodeOrNull(IF_TIMESTAMP_RESOLUTION);
        }
        this.timeResolution = timeResolution;
    }

    private LinkType linkTypeById(int id) {
        if (!LINK_TYPE_MAP.containsKey(id)) {
            throw EXCEPTION_FACTORY.exception(PcapNGFileFormatException::new, "Unknown link type: " + id);
        }
        return LINK_TYPE_MAP.get(id);
    }

    private void parseReserved(Reader reader) {
        reader.skip(2);
    }

    static {
        LINK_TYPE_MAP.put(0, LinkType.NULL);
        LINK_TYPE_MAP.put(1, LinkType.ETHERNET);
        LINK_TYPE_MAP.put(2, LinkType.EXP_ETHERNET);
        LINK_TYPE_MAP.put(3, LinkType.AX25);
        LINK_TYPE_MAP.put(4, LinkType.PRONET);
        LINK_TYPE_MAP.put(5, LinkType.CHAOS);
        LINK_TYPE_MAP.put(6, LinkType.TOKEN_RING);
        LINK_TYPE_MAP.put(7, LinkType.ARCNET);
        LINK_TYPE_MAP.put(8, LinkType.SLIP);
        LINK_TYPE_MAP.put(9, LinkType.PPP);
        LINK_TYPE_MAP.put(10, LinkType.FDDI);
        LINK_TYPE_MAP.put(50, LinkType.PPP_HDLC);
        LINK_TYPE_MAP.put(51, LinkType.PPP_ETHER);
        LINK_TYPE_MAP.put(99, LinkType.SYMANTEC_FIREWALL);
        LINK_TYPE_MAP.put(100, LinkType.ATM_RFC1483);
        LINK_TYPE_MAP.put(101, LinkType.RAW);
        LINK_TYPE_MAP.put(102, LinkType.SLIP_BSDOS);
        LINK_TYPE_MAP.put(103, LinkType.PPP_BSDOS);
        LINK_TYPE_MAP.put(104, LinkType.C_HDLC);
        LINK_TYPE_MAP.put(105, LinkType.IEEE802_11);
        LINK_TYPE_MAP.put(106, LinkType.ATM_CLIP);
        LINK_TYPE_MAP.put(107, LinkType.FRELAY);
        LINK_TYPE_MAP.put(108, LinkType.LOOP);
        LINK_TYPE_MAP.put(109, LinkType.ENC);
        LINK_TYPE_MAP.put(110, LinkType.LANE8023);
        LINK_TYPE_MAP.put(111, LinkType.HIPPI);
        LINK_TYPE_MAP.put(112, LinkType.HDLC);
        LINK_TYPE_MAP.put(113, LinkType.LINUX_SLL);
        LINK_TYPE_MAP.put(114, LinkType.LTALK);
        LINK_TYPE_MAP.put(115, LinkType.ECONET);
        LINK_TYPE_MAP.put(116, LinkType.IPFILTER);
        LINK_TYPE_MAP.put(117, LinkType.PFLOG);
        LINK_TYPE_MAP.put(118, LinkType.CISCO_IOS);
        LINK_TYPE_MAP.put(119, LinkType.PRISM_HEADER);
        LINK_TYPE_MAP.put(120, LinkType.AIRONET_HEADER);
        LINK_TYPE_MAP.put(121, LinkType.HHDLC);
        LINK_TYPE_MAP.put(122, LinkType.IP_OVER_FC);
        LINK_TYPE_MAP.put(123, LinkType.SUNATM);
        LINK_TYPE_MAP.put(124, LinkType.RIO);
        LINK_TYPE_MAP.put(125, LinkType.PCI_EXP);
        LINK_TYPE_MAP.put(126, LinkType.AURORA);
        LINK_TYPE_MAP.put(127, LinkType.IEEE802_11_RADIO);
        LINK_TYPE_MAP.put(128, LinkType.TZSP);
        LINK_TYPE_MAP.put(129, LinkType.ARCNET_LINUX);
        LINK_TYPE_MAP.put(130, LinkType.JUNIPER_MLPPP);
        LINK_TYPE_MAP.put(131, LinkType.JUNIPER_MLFR);
        LINK_TYPE_MAP.put(132, LinkType.JUNIPER_ES);
        LINK_TYPE_MAP.put(133, LinkType.JUNIPER_GGSN);
        LINK_TYPE_MAP.put(134, LinkType.JUNIPER_MFR);
        LINK_TYPE_MAP.put(135, LinkType.JUNIPER_ATM2);
        LINK_TYPE_MAP.put(136, LinkType.JUNIPER_SERVICES);
        LINK_TYPE_MAP.put(137, LinkType.JUNIPER_ATM1);
        LINK_TYPE_MAP.put(138, LinkType.APPLE_IP_OVER_IEEE1394);
        LINK_TYPE_MAP.put(139, LinkType.MTP2_WITH_PHDR);
        LINK_TYPE_MAP.put(140, LinkType.MTP2);
        LINK_TYPE_MAP.put(141, LinkType.MTP3);
        LINK_TYPE_MAP.put(142, LinkType.SCCP);
        LINK_TYPE_MAP.put(143, LinkType.DOCSIS);
        LINK_TYPE_MAP.put(144, LinkType.LINUX_IRDA);
        LINK_TYPE_MAP.put(145, LinkType.IBM_SP);
        LINK_TYPE_MAP.put(146, LinkType.IBM_SN);
    }
}
