package ru.wendovsky.pcapng.reader;

import java.nio.ByteOrder;

public interface Reader {
    int readUnsignedByte();

    int readUnsignedShort();

    int readInt();

    long readLong();

    String readUtf8(int length);

    byte[] readBytes(int length);

    void skip(int bytes);

    void order(ByteOrder order);

    void align(int alignment);

    int position();

    boolean endOfStream();

    void mark(int index);

    boolean markAchieved();

    ByteOrder order();
}
