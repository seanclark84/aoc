package aoc.year2024;

import aoc.common.Coordinate;
import aoc.common.Direction;
import aoc.common.Grid;
import aoc.common.GridEntry;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Day12 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        // Parse all the data into a grid, GridEntry objects will be linked together with their neighbours
        Grid<FieldGridEntry> grid = Utils.parseToGrid("/aoc.year2024/day12.txt", FieldGridEntry.class);
        long total = 0;
        while (true) {
            FieldGridEntry entry = grid.findFirst(e -> !e.counted).orElse(null);

            // quick exit, they are all counted
            if ( entry == null ) break;

            Set<FieldGridEntry> countedEntries = new HashSet<>();
            Queue<FieldGridEntry> queue = new ArrayDeque<>();
            int fences = 0;
            queue.add(entry);
            while(queue.peek() != null) {
                FieldGridEntry fge = queue.poll();
                if ( !countedEntries.contains(fge) ) {
                    fge.setCounted(true);
                    countedEntries.add(fge);
                    // add to the queue any that aren't already counted
                    queue.addAll(fge.getMatchingNeighbours().stream().filter(e -> !e.isCounted()).toList());
                    fences += fge.getFences();
                }
            }
            System.out.println("Counted = " + countedEntries.size() + " - fences = " + fences);
            total += ((long) countedEntries.size() * fences);
        }
        System.out.println("total = " + total);
    }

    @Getter
    public static class FieldGridEntry extends GridEntry<FieldGridEntry> {
        @Setter
        private boolean counted = false;

        private final List<FieldGridEntry> matchingNeighbours = new ArrayList<>();

        private int fences = 0;

        public FieldGridEntry(@NonNull String data, Coordinate coordinate) {
            super(data, coordinate);
        }

        @Override
        public void postInitialisation() {
            for( Direction d: Arrays.asList(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT)) {
                FieldGridEntry entry = getEntryDirection().get(d);
                if ( entry == null || !getData().equals(entry.getData())) {
                    fences++;
                } else {
                    matchingNeighbours.add(entry);
                }
            }
        }
    }
}
