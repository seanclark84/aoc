package aoc.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    public void test() {
        Coordinate c1 = new Coordinate(2, 2);
        Coordinate c2 = new Coordinate(2, 9);
        Coordinate c3 = new Coordinate(3, 9);

        assertEquals(7, c1.distanceFrom(c2));
        assertEquals(7, c2.distanceFrom(c1));
        assertEquals(8, c1.distanceFrom(c3));
        assertEquals(8, c3.distanceFrom(c1));
    }

}