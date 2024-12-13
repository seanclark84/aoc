package aoc.year2024;

import aoc.common.CombinationGenerator;
import aoc.common.CombinationPiece;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {

    @Test
    public void testIterator() {
        CombinationPiece<Integer> piece = new CombinationPiece<>(1, 2, 3);
        CombinationPiece<Integer> piece2 = new CombinationPiece<>(1, 3);
        CombinationGenerator<Integer> gen = new CombinationGenerator<>(Arrays.asList(piece2, piece));
        List<Integer> comb;
        while ((comb = gen.generateCombination()) != null) {
            System.out.println(comb);
        }


        CombinationGenerator<Integer> gen2 = new CombinationGenerator<>(piece2, 2);
        while ((comb = gen2.generateCombination()) != null) {
            System.out.println(comb);
        }
    }

}