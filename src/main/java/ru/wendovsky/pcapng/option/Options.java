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
    final Map<String, Option> optionNameToOption;

    public Options(Reader reader, Map<Integer, String> optionCodeToOptionName) {
        optionNameToOption = parseOptions(reader, optionCodeToOptionName);
    }

    public Optional<Option> byName(String name) {
        if (!optionNameToOption.containsKey(name)) {
            return Optional.empty();
        }
        return Optional.of(optionNameToOption.get(name));
    }

    public String stringByNameOrNull(String name) {
        return byName(name)
                .map(Option::asSingleStringOrNull)
                .orElse(null);
    }

    private Map<String, Option> parseOptions(Reader reader, Map<Integer, String> optionCodeToOptionName) {
        // LinkedHashMap saves the order
        Map<String, Option> map = new LinkedHashMap<>();
        RawOption rawOption;
        while ((rawOption = parseRawOptionOrNull(reader, optionCodeToOptionName)) != null) {
            String name = rawOption.name();
            byte[] bytes = rawOption.bytes();
            Option option = map.computeIfAbsent(name, ignored -> new Option());
            option.put(bytes);
        }
        return map;
    }

    private RawOption parseRawOptionOrNull(Reader reader, Map<Integer, String> optionCodeToOptionName) {
        int optionCode = reader.readUnsignedShort();
        int optionLength = reader.readUnsignedShort();
        if (optionCode == OPT_END_OF_OPT) {
            return null;
        }
        if (!optionCodeToOptionName.containsKey(optionCode)) {
            throw new IllegalStateException("Unknown option code " + optionCode);
        }
        String optionName = optionCodeToOptionName.get(optionCode);
        try {
            return new RawOption(optionName, reader.readBytes(optionLength));
        } finally {
            reader.align(PcapNG.ALIGNMENT);
        }
    }

    private record RawOption(String name, byte[] bytes) {
    }
}
