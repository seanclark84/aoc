package aoc.year2024;

import aoc.common.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        // Parse all the data into a grid, GridEntry objects will be linked together with their neighbours
        Grid<DefaultGridEntry> grid = Utils.parseToGrid("/aoc.year2024/day6.txt", DefaultGridEntry.class);

        // Get a list of all GridEntry object (effectively all coordinates)
        List<DefaultGridEntry> gridEntries = grid.getAllGridEntries();

        // counter of number of infinite loops found
        long infiniteLoops = 0;

        // Loop thrugh each coordinate
        for (DefaultGridEntry entry: gridEntries) {
            // If this coordinate isn't a "." then we're not changing it so move on to next
            if(!entry.getData().equals(".")) continue;

            // set the usual defaults
            DefaultGridEntry currentLocation = grid.findFirst(test -> "^".equals(test.getData())).get();
            Direction currentDirection = Direction.UP;

            // Update this entry to be a blocker
            entry.setData("#");
            System.out.println("entry.getCoordinate() + \" \" + entry.getData() = " + entry.getCoordinate() + " " + entry.getData());

            // loop counter
            int loopCount = 0;
            while (true) {
                loopCount++;
                // if this goes too high, we're probably in an infinite loop, probably a better way to do this
                if (loopCount == Integer.MAX_VALUE/1000) {
                    infiniteLoops++;
                    break;
                }

                // get the next location based on direction we're facing
                DefaultGridEntry nextLocation = currentLocation.getInDirection(currentDirection).orElse(null);

                // if we get a null back we have escaped the grid, so skip to next one
                if (nextLocation == null) break;

                // if the next location is a blocker we turn right 90 degrees  but stay at same location, otherwise move
                // to next location
                if ("#".equals(nextLocation.getData())) {
                    currentDirection = currentDirection.turnRight();
                } else {
                    currentLocation = nextLocation;
                }
            }
            // remember to reset the data
            entry.setData(".");
        }

        System.out.println("infiniteLoops = " + infiniteLoops);
    }
}
