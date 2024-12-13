package aoc.year2024;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {

    @Test
    public void testIterator() {
        Day7.CombinationPiece<Integer> piece = new Day7.CombinationPiece<>(1, 2, 3);
        Day7.CombinationPiece<Integer> piece2 = new Day7.CombinationPiece<>(1, 3);
        Day7.CombinationGenerator<Integer> gen = new Day7.CombinationGenerator<>(Arrays.asList(piece2, piece));
        List<Integer> comb;
        while ((comb = gen.generateCombination()) != null) {
            System.out.println(comb);
        }

    }

}