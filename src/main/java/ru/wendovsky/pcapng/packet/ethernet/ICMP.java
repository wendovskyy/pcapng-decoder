package ru.wendovsky.pcapng.packet.ethernet;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.reader.Reader;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
@Getter
public class ICMP implements Protocol {
    public ICMP(Reader reader) {
        // Type
        reader.skip(1);
        // Code
        reader.skip(1);
        // Checksum
        reader.skip(2);
        // Rest of header
        reader.skip(4);
    }
}
