package ru.wendovsky.pcapng.block;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.option.Options;
import ru.wendovsky.pcapng.reader.Reader;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
public final class InterfaceDescriptionBlock implements Block {
    private static final Map<Integer, LinkType> LINK_TYPE_MAP = new HashMap<>();
    final LinkType linkType;
    final int snapLength;

    public InterfaceDescriptionBlock(Reader reader) {
        linkType = linkTypeById(reader.readUnsignedShort());
        // Reserved
        reader.skip(2);
        snapLength = reader.readInt();
        // TODO parse options
        new Options(reader);
    }

    private LinkType linkTypeById(int id) {
        if (!LINK_TYPE_MAP.containsKey(id)) {
            return null;
        }
        return LINK_TYPE_MAP.get(id);
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

    @RequiredArgsConstructor
    @Accessors(fluent = true)
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    public enum LinkType {
        NULL("No link layer information. A packet saved with this link layer contains a raw L3 packet preceded by a 32-bit host-byte-order AF_ value indicating the specific L3 type."),
        ETHERNET("D/I/X and 802.3 Ethernet"),
        EXP_ETHERNET("Experimental Ethernet (3Mb)"),
        AX25("Amateur Radio AX.25"),
        PRONET("Proteon ProNET Token Ring"),
        CHAOS("Chaos"),
        TOKEN_RING("IEEE 802 Networks"),
        ARCNET("ARCNET, with BSD-style header"),
        SLIP("Serial Line IP"),
        PPP("Point-to-point Protocol"),
        FDDI("FDDI"),
        PPP_HDLC("PPP in HDLC-like framing"),
        PPP_ETHER("NetBSD PPP-over-Ethernet"),
        SYMANTEC_FIREWALL("Symantec Enterprise Firewall"),
        ATM_RFC1483("LLC/SNAP-encapsulated ATM"),
        RAW("Raw IP"),
        SLIP_BSDOS("BSD/OS SLIP BPF header"),
        PPP_BSDOS("BSD/OS PPP BPF header"),
        C_HDLC("Cisco HDLC"),
        IEEE802_11("IEEE 802.11 (wireless)"),
        ATM_CLIP("Linux Classical IP over ATM"),
        FRELAY("Frame Relay"),
        LOOP("OpenBSD loopback"),
        ENC("OpenBSD IPSEC enc"),
        LANE8023("ATM LANE + 802.3 (Reserved for future use)"),
        HIPPI("NetBSD HIPPI (Reserved for future use)"),
        HDLC("NetBSD HDLC framing (Reserved for future use)"),
        LINUX_SLL("Linux cooked socket capture"),
        LTALK("Apple LocalTalk hardware"),
        ECONET("Acorn Econet"),
        IPFILTER("Reserved for use with OpenBSD ipfilter"),
        PFLOG("OpenBSD DLT_PFLOG"),
        CISCO_IOS("For Cisco-internal use"),
        PRISM_HEADER("802.11+Prism II monitor mode"),
        AIRONET_HEADER("FreeBSD Aironet driver stuff"),
        HHDLC("Reserved for Siemens HiPath HDLC"),
        IP_OVER_FC("RFC 2625 IP-over-Fibre Channel"),
        SUNATM("Solaris+SunATM"),
        RIO("RapidIO - Reserved as per request from Kent Dahlgren &lt;kent@praesum.com&gt; for private use."),
        PCI_EXP("PCI Express - Reserved as per request from Kent Dahlgren &lt;kent@praesum.com&gt; for private use."),
        AURORA("Xilinx Aurora link layer - Reserved as per request from Kent Dahlgren &lt;kent@praesum.com&gt; for private use."),
        IEEE802_11_RADIO("802.11 plus BSD radio header"),
        TZSP("Tazmen Sniffer Protocol - Reserved for the TZSP encapsulation, as per request from Chris Waters &lt;chris.waters@networkchemistry.com&gt; TZSP is a generic encapsulation for any other link type, which includes a means to include meta-information with the packet, e.g. signal strength and channel for 802.11 packets."),
        ARCNET_LINUX("Linux-style headers"),
        JUNIPER_MLPPP("Juniper-private data link type, as per request from Hannes Gredler &lt;hannes@juniper.net&gt;.  The corresponding DLT_s are used for passing on chassis-internal metainformation such as QOS profiles, etc.."),
        JUNIPER_MLFR("Juniper-private data link type, as per request from Hannes Gredler &lt;hannes@juniper.net&gt;.  The corresponding DLT_s are used for passing on chassis-internal metainformation such as QOS profiles, etc.."),
        JUNIPER_ES("Juniper-private data link type, as per request from Hannes Gredler &lt;hannes@juniper.net&gt;.  The corresponding DLT_s are used for passing on chassis-internal metainformation such as QOS profiles, etc.."),
        JUNIPER_GGSN("Juniper-private data link type, as per request from Hannes Gredler &lt;hannes@juniper.net&gt;.  The corresponding DLT_s are used for passing on chassis-internal metainformation such as QOS profiles, etc.."),
        JUNIPER_MFR("Juniper-private data link type, as per request from Hannes Gredler &lt;hannes@juniper.net&gt;.  The corresponding DLT_s are used for passing on chassis-internal metainformation such as QOS profiles, etc.."),
        JUNIPER_ATM2("Juniper-private data link type, as per request from Hannes Gredler &lt;hannes@juniper.net&gt;.  The corresponding DLT_s are used for passing on chassis-internal metainformation such as QOS profiles, etc.."),
        JUNIPER_SERVICES("Juniper-private data link type, as per request from Hannes Gredler &lt;hannes@juniper.net&gt;.  The corresponding DLT_s are used for passing on chassis-internal metainformation such as QOS profiles, etc.."),
        JUNIPER_ATM1("Juniper-private data link type, as per request from Hannes Gredler &lt;hannes@juniper.net&gt;.  The corresponding DLT_s are used for passing on chassis-internal metainformation such as QOS profiles, etc.."),
        APPLE_IP_OVER_IEEE1394("Apple IP-over-IEEE 1394 cooked header"),
        MTP2_WITH_PHDR("???"),
        MTP2("???"),
        MTP3("???"),
        SCCP("???"),
        DOCSIS("DOCSIS MAC frames"),
        LINUX_IRDA("Linux-IrDA"),
        IBM_SP("Reserved for IBM SP switch and IBM Next Federation switch."),
        IBM_SN("Reserved for IBM SP switch and IBM Next Federation switch.");
        final String details;
    }
}
