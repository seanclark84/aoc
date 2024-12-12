package aoc.common;

import lombok.NonNull;

public class DefaultGridEntry extends GridEntry<DefaultGridEntry> {
    public DefaultGridEntry(@NonNull String data, Coordinate coordinate) {
        super(data, coordinate);
    }
}
