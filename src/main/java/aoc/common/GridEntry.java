package aoc.common;

import aoc.year2024.Day4;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@RequiredArgsConstructor
public class GridEntry {
    @NonNull
    private String data;
    private final Coordinate coordinate;
    private Map<Direction, GridEntry> entryDirection = new HashMap<>();

    public Optional<GridEntry> getInDirection(Direction direction) {
        return Optional.ofNullable(entryDirection.get(direction));
    }
}
