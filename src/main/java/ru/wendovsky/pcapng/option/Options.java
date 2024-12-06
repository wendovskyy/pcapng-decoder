package ru.wendovsky.pcapng.option;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.wendovsky.pcapng.PcapNG;
import ru.wendovsky.pcapng.reader.Reader;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Options {
    private static final int OPT_END_OF_OPT = 0;
    final Map<Integer, Option> optionCodeToOption;

    public Options(Reader reader) {
        optionCodeToOption = parseOptions(reader);
    }

    public static Options createOptionIfMarkNotAchievedOrNull(Reader reader) {
        if (reader.markAchieved()) {
            return null;
        }
        return new Options(reader);
    }

    public Optional<Option> byCode(int code) {
        if (!optionCodeToOption.containsKey(code)) {
            return Optional.empty();
        }
        return Optional.of(optionCodeToOption.get(code));
    }

    public String stringByCodeOrNull(int code) {
        return byCode(code)
                .map(Option::asSingleStringOrNull)
                .orElse(null);
    }

    public Integer unsignedIntByCodeOrNull(int code) {
        return byCode(code)
                .map(Option::asSingleUnsignedByteOrNull)
                .orElse(null);
    }

    private Map<Integer, Option> parseOptions(Reader reader) {
        // LinkedHashMap saves the order
        Map<Integer, Option> map = new LinkedHashMap<>();
        RawOption rawOption;
        while ((rawOption = parseRawOptionOrNull(reader)) != null) {
            int code = rawOption.code();
            byte[] bytes = rawOption.bytes();
            Option option = map.computeIfAbsent(code, ignored -> new Option());
            option.put(bytes);
        }
        return map;
    }

    private RawOption parseRawOptionOrNull(Reader reader) {
        int optionCode = reader.readUnsignedShort();
        int optionLength = reader.readUnsignedShort();
        if (optionCode == OPT_END_OF_OPT) {
            return null;
        }
        try {
            return new RawOption(optionCode, reader.readBytes(optionLength));
        } finally {
            reader.align(PcapNG.ALIGNMENT);
        }
    }

    private record RawOption(int code, byte[] bytes) {
    }
}
