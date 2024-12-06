package ru.wendovsky.pcapng.block;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.exception.PcapNGFileFormatException;
import ru.wendovsky.pcapng.option.Options;
import ru.wendovsky.pcapng.reader.Reader;

import java.nio.ByteOrder;
import java.util.Map;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class SectionHeaderBlock implements Block {
    private static final String SHB_HARDWARE = "shb_hardware";
    private static final String SHB_OS = "shb_os";
    private static final String SHB_USER_APPLICATION = "shb_userappl";
    private static final Map<Integer, String> OPTION_CODE_TO_OPTION_NAME =
            Map.of(
                    2, SHB_HARDWARE,
                    3, SHB_OS,
                    4, SHB_USER_APPLICATION
            );
    private static final int BYTE_ORDER_MAGIC_LITTLE_ENDIAN = 0x4D3C2B1A;
    private static final int BYTE_ORDER_MAGIC_BIG_ENDIAN = 0x1A2B3C4D;
    final int major;
    final int minor;
    final String shbHardware;
    final String shbOs;
    final String shbUserApplication;

    public SectionHeaderBlock(Reader reader) {
        reader.order(byteOrder(reader.readInt()));
        major = reader.readUnsignedShort();
        minor = reader.readUnsignedShort();
        // Skip the length of section
        reader.skip(8);
        Options options = new Options(reader, OPTION_CODE_TO_OPTION_NAME);
        shbHardware = options.stringByNameOrNull(SHB_HARDWARE);
        shbOs = options.stringByNameOrNull(SHB_OS);
        shbUserApplication = options.stringByNameOrNull(SHB_USER_APPLICATION);
    }

    private ByteOrder byteOrder(int byteOrderMagic) {
        return switch (byteOrderMagic) {
            case BYTE_ORDER_MAGIC_LITTLE_ENDIAN -> ByteOrder.LITTLE_ENDIAN;
            case BYTE_ORDER_MAGIC_BIG_ENDIAN -> ByteOrder.BIG_ENDIAN;
            default -> throw exception("Unknown byte order magic " + byteOrderMagic);
        };
    }

    private PcapNGFileFormatException exception(String message) {
        return new PcapNGFileFormatException("SectionHeaderBlock / " + message);
    }
}
