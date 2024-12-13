package aoc.year2024;

import aoc.common.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Day12_2 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        // Parse all the data into a grid, GridEntry objects will be linked together with their neighbours
        Grid<FieldGridEntry> grid = Utils.parseToGrid("/aoc.year2024/day12.txt", FieldGridEntry.class);
        long total = 0;

        while (true) {
            FieldGridEntry entry = grid.findFirst(e -> !e.isCounted()).orElse(null);

            // quick exit, they are all counted
            if ( entry == null ) break;

            System.out.println("entry.getData() = " + entry.getData());

            // while we're not at the starting entry (or we are and there's fences left)
            List<Alignment> mergedFences = getFences(entry, true);

            // Do the counts from the previous
            Set<FieldGridEntry> countedEntries = new HashSet<>();
            Queue<FieldGridEntry> queue = new ArrayDeque<>();
            queue.add(entry);
            while(queue.peek() != null) {
                FieldGridEntry fge = queue.poll();
                if ( !countedEntries.contains(fge) ) {
                    fge.setCounted(true);
                    countedEntries.add(fge);
                    // add to the queue any that aren't already counted
                    queue.addAll(fge.getMatchingNeighbours().stream().filter(e -> !e.isCounted()).toList());
                }
            }

            // Looks for any of the internal fences
            while (countedEntries.stream().anyMatch(e -> e.fences > 0 )) {
                // this ensures we always get the topmost element from the grid
                FieldGridEntry first = grid.findFirst(e -> countedEntries.contains(e) && e.fences > 0).orElse(null);
                // this time we go anticlockwise (left)
                mergedFences.addAll(getFences(first, false));
            }

            System.out.println("Counted = " + countedEntries.size() + " - fences = " + mergedFences.size());
            total += (long) countedEntries.size() * mergedFences.size();
        }
        System.out.println("total = " + total);
    }

    private static List<Alignment> getFences(final FieldGridEntry start, boolean external ) {
        // We're going around the field clockwise so left will always be the "outside" when we're looking
        // for external fences and right when we're looking for internal fences
        Function<Direction, Direction> fenceCheck = d -> external ? d.turnLeft() : d.turnRight();
        // the direction to turn when we hit a wall changes depending on whether it's internal or external
        Function<Direction, Direction> avoidFence = d -> external ? d.turnRight() : d.turnLeft();
        FieldGridEntry current = start;
        List<Alignment> fences = new ArrayList<>();
        Direction lastDirection = Direction.RIGHT;

        while ( !current.equals(start) || ( current.equals(start) && current.getFences() > 0 )) {
            if ( current.isDirectionFence(fenceCheck.apply(lastDirection)) ) {
                // if we have a field on the correct side, record it
                fences.add(lastDirection.getAlignment());
                current.fences--;
                // if there's a field in the direction we're facing turn right
                // (we already know there's a fence on our left) or vice versa for an internal fence
                if ( current.isDirectionFence(lastDirection) ) {
                    lastDirection = avoidFence.apply(lastDirection);
                } else {
                    // otherwise keep moving in the same direction
                    current = current.getEntryDirection().get(lastDirection);
                }
            } else {
                // if we didn't have a fence on oue left, turn left and then move in that direction (for external fences)
                // opposite for internal.
                lastDirection = fenceCheck.apply(lastDirection);
                current = current.getEntryDirection().get(lastDirection);
            }



            System.out.println("lastDirection = " + lastDirection);
            System.out.println("coords = " + current.getCoordinate());
        }

        List<Alignment> mergedFences = new ArrayList<>();
        Alignment lastAdded = null;
        for (Alignment al : fences) {
            if ( al !=  lastAdded ) {
                lastAdded = al;
                mergedFences.add(al);
            }
        }

        return mergedFences;
    }

    @Getter
    public static class FieldGridEntry extends GridEntry<FieldGridEntry> {
        @Setter
        private boolean counted = false;

        private final List<FieldGridEntry> matchingNeighbours = new ArrayList<>();

        private int fences = 0;

        public FieldGridEntry(@NonNull String data, @NonNull Grid<FieldGridEntry> grid, Coordinate coordinate) {
            super(data, grid, coordinate);
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

        public boolean isDirectionFence(Direction direction) {
            return getEntryDirection().get(direction) == null || !getEntryDirection().get(direction).getData().equals(getData());
        }

        public boolean isDirectionNeighbour(Direction direction) {
            return !isDirectionFence(direction);
        }
    }
}
