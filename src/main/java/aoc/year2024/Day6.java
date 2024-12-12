package aoc.year2024;

import aoc.common.Coordinate;
import aoc.common.Direction;
import aoc.common.Grid;
import aoc.common.GridEntry;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Grid<GridEntry> grid = Utils.parseToGrid("/aoc.year2024/day6.txt", GridEntry.class);


        List<GridEntry> gridEntries = grid.getAllGridEntries();
        long infiniteLoops = 0;
        for (GridEntry entry: gridEntries) {
            GridEntry currentLocation = grid.findFirst(test -> "^".equals(test.getData())).get();
            Direction currentDirection = Direction.UP;
            if(!entry.getData().equals(".")) continue;
            entry.setData("#");
            System.out.println("entry.getCoordinate() + \" \" + entry.getData() = " + entry.getCoordinate() + " " + entry.getData());
            
            int loopCount = 0;
            while (true) {
                loopCount++;
                if (loopCount == Integer.MAX_VALUE/1000) {
                    infiniteLoops++;
                    break;
                }
                GridEntry nextLocation = currentLocation.getInDirection(currentDirection).orElse(null);
                if (nextLocation == null) break;
                if ("#".equals(nextLocation.getData())) {
                    currentDirection = currentDirection.turnRight();
                } else {
                    currentLocation = nextLocation;
                }
            }
            entry.setData(".");
        }

        System.out.println("infiniteLoops = " + infiniteLoops);
    }
}
