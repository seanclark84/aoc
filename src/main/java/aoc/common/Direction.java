package aoc.common;

import aoc.year2024.Day4;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Supplier;

public enum Direction {
    UP(0, -1),
    UP_RIGHT(1, -1),
    RIGHT(1, 0),
    DOWN_RIGHT(1, 1),
    DOWN(0, 1),
    DOWN_LEFT(-1, 1),
    LEFT(-1, 0),
    UP_LEFT(-1, -1);

    private static final LinkedList<Direction> DIRECTIONS = new LinkedList<>(Arrays.stream(Direction.values()).toList());

    private int x;
    private int y;
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction turnRight() {
        int ordinal = this.ordinal();
        ordinal += 2;
        int diff = ordinal - (Direction.values().length - 1);
        if ( diff == 1) {
            ordinal = 0;
        } else if ( diff == 2 ) {
            ordinal = 1;
        }
        return Direction.values()[ordinal];
    }

    public Direction turnLeft() {
        int ordinal = this.ordinal();
        ordinal-= 2;
        if ( ordinal < 0) {
            ordinal = Direction.values().length + ordinal;
        }
        return Direction.values()[ordinal];
    }

    public Alignment getAlignment() {
        return this == RIGHT || this == LEFT ? Alignment.HORIZONTAL :
                this == UP || this == DOWN ? Alignment.VERTICAL :
                        Alignment.DIAGONAL;
    }

    public Iterator<Direction> iterateFromThis() {
        return iterateFrom(this);
    }

    public static Iterator<Direction> iterateFrom(Direction direction) {
        return DIRECTIONS.listIterator(DIRECTIONS.indexOf(direction));
    }
}
