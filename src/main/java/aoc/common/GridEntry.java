package aoc.common;

import aoc.year2024.Day4;
import aoc.year2024.Day8;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@RequiredArgsConstructor
public abstract class GridEntry<T extends GridEntry<T>> {
    @NonNull
    private String data;
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Grid<T> grid;
    private final Coordinate coordinate;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Map<Direction, T> entryDirection = new HashMap<>();

    public Optional<T> getInDirection(Direction direction) {
        return Optional.ofNullable(entryDirection.get(direction));
    }

    /**
     * nothing by default
     */
    public void postInitialisation() {
    }

    public T translate(int xdiff, int ydiff) {
        return grid.getByCoords(getCoordinate().getFirst() + xdiff, getCoordinate().getSecond() + ydiff);
    }
}
