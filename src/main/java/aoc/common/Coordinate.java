package aoc.common;

import java.util.stream.IntStream;

public class Coordinate extends Pair<Integer, Integer>{
    public Coordinate(Integer first, Integer second) {
        super(first, second);
    }

    public int distanceFrom(Coordinate other) {
        return IntStream.of(this.getFirst() - other.getFirst(), this.getSecond() - other.getSecond())
                .map(i -> i < 0 ? i * -1 : i)
                .sum();
    }
}