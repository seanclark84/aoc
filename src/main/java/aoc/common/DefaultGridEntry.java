package aoc.common;

import lombok.NonNull;

public class DefaultGridEntry extends GridEntry<DefaultGridEntry> {

    public DefaultGridEntry(@NonNull String data, @NonNull Grid<DefaultGridEntry> grid, Coordinate coordinate) {
        super(data, grid, coordinate);
    }
}
