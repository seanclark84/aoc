package aoc.common;

import aoc.year2024.Day4;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@RequiredArgsConstructor
public abstract class GridEntry<T extends GridEntry<T>> {
    @NonNull
    private String data;
    private final Coordinate coordinate;
    @EqualsAndHashCode.Exclude
    private Map<Direction, T> entryDirection = new HashMap<>();

    public Optional<T> getInDirection(Direction direction) {
        return Optional.ofNullable(entryDirection.get(direction));
    }

    /**
     * nothing by default
     */
    public void postInitialisation() {
    }
}
