package ru.wendovsky.pcapng.block;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

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