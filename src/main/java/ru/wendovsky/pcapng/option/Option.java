package ru.wendovsky.pcapng.option;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Option {
    final List<Node> nodes = new ArrayList<>();

    public void put(byte[] bytes) {
        nodes.add(new Node(bytes));
    }

    public String asSingleStringOrNull() {
        return asSingleNode()
                .map(Node::utf8)
                .orElse(null);
    }

    public Integer asSingleUnsignedByteOrNull() {
        return asSingleNode()
                .map(Node::unsignedInt)
                .orElse(null);
    }

    private Optional<Node> asSingleNode() {
        guaranteeSingleNodeOrNull();
        if (nodes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(nodes.getFirst());
    }

    private void guaranteeSingleNodeOrNull() {
        if (nodes.size() > 1) {
            throw unresolvedNode();
        }
    }

    private IllegalStateException unresolvedNode() {
        return new IllegalStateException("Cannot resolve the node");
    }

    private record Node(byte[] data) {
        public String utf8() {
            return new String(data, StandardCharsets.UTF_8);
        }

        public int unsignedInt() {
            return data[0] & 0xFF;
        }
    }
}
