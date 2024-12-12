package aoc.common;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Data
public class Grid<G extends GridEntry> {
    private final G[][] grid;

    public Grid(String[][] input, Class<G> clazz) {
        int[] args = new int[2];
        args[0] = input.length;
        args[1] = input[0].length;
        grid = (G[][]) Array.newInstance(clazz, args);

        try {
            for (int i = 0; i < input.length; i++) {
                String[] line = input[i];
                for (int j = 0; j < line.length; j++) {
                    String data = input[i][j];
                    G entry = clazz.getDeclaredConstructor(data.getClass(), Coordinate.class).newInstance(data, new Coordinate(j, i));
                    grid[i][j] = entry;
                }
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

        for (int i = 0; i < grid.length; i++) {
            G[] rowEntry = grid[i];
            for (int j = 0; j < rowEntry.length; j++) {
                G entry = grid[i][j];
                for (Direction dir: Direction.values()) {
                    G neighbour;
                    try {
                        neighbour = grid[i + dir.getY()][j + dir.getX()];
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        neighbour = null;
                    }
                    entry.getEntryDirection().put(dir, neighbour);
                }
            }
        }

    }

    public Optional<G> findFirst(Predicate<G> test) {
        return Arrays.stream(grid).flatMap(Arrays::stream).filter(test).findFirst();
    }

    public List<Coordinate> getAllCoordinates() {
        return Arrays.stream(grid)
                .flatMap(Arrays::stream)
                .map(G::getCoordinate)
                .toList();
    }

    public List<G> getAllGridEntries() {
        return Arrays.stream(grid)
                .flatMap(Arrays::stream)
                .toList();
    }
}
