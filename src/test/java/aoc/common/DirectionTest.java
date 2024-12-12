package aoc.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    public void testDirection() {
        assertEquals(Direction.RIGHT, Direction.UP.turnRight());
        assertEquals(Direction.UP, Direction.LEFT.turnRight());
    }
}