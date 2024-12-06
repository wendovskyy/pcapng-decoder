package ru.wendovsky.pcapng.block;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.context.Context;
import ru.wendovsky.pcapng.exception.PcapNGFileFormatException;
import ru.wendovsky.pcapng.option.Options;
import ru.wendovsky.pcapng.reader.Reader;

import java.nio.ByteOrder;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class SectionHeaderBlock implements Block {
    private static final ExceptionFactory EXCEPTION_FACTORY = new ExceptionFactory("SectionHeaderBlock");
    private static final int SHB_HARDWARE = 2;
    private static final int SHB_OS = 3;
    private static final int SHB_USER_APPLICATION = 4;
    private static final int BYTE_ORDER_MAGIC_LITTLE_ENDIAN = 0x4D3C2B1A;
    private static final int BYTE_ORDER_MAGIC_BIG_ENDIAN = 0x1A2B3C4D;
    final int major;
    final int minor;
    final String shbHardware;
    final String shbOs;
    final String shbUserApplication;

    public SectionHeaderBlock(Context context) {
        Reader reader = context.reader();
        reader.order(byteOrder(reader.readInt()));
        major = reader.readUnsignedShort();
        minor = reader.readUnsignedShort();
        parseLengthOfSection(reader);
        Options options = new Options(reader);
        shbHardware = options.stringByCodeOrNull(SHB_HARDWARE);
        shbOs = options.stringByCodeOrNull(SHB_OS);
        shbUserApplication = options.stringByCodeOrNull(SHB_USER_APPLICATION);
    }

    private ByteOrder byteOrder(int byteOrderMagic) {
        return switch (byteOrderMagic) {
            case BYTE_ORDER_MAGIC_LITTLE_ENDIAN -> ByteOrder.LITTLE_ENDIAN;
            case BYTE_ORDER_MAGIC_BIG_ENDIAN -> ByteOrder.BIG_ENDIAN;
            default ->
                    throw EXCEPTION_FACTORY.exception(PcapNGFileFormatException::new, "Unknown byte order magic " + byteOrderMagic);
        };
    }

    private void parseLengthOfSection(Reader reader) {
        reader.skip(8);
    }
}
