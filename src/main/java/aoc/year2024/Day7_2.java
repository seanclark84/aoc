package aoc.year2024;

import lombok.Data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Day7_2 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Utils.getLines("/aoc.year2024/day7.txt");
        long success = 0;
        for (String line: lines) {
            String[] split1 = line.split(":");
            long total = Long.parseLong(split1[0]);
            String remainder = split1[1].trim();
            String[] numbers = remainder.split(" ");

            // try with plus first
            if( tryAll(numbers, total) ) {
                success += total;
            }
        }

        System.out.println("success = " + success);
    }

    private static boolean tryAll(String[] numbers, long expected) {
        CombinationPiece<Operators> op1 = new CombinationPiece<>(Operators.PLUS, Operators.MULTIPLY, Operators.CONCAT);
        CombinationGenerator<Operators> generator = new CombinationGenerator<>(op1, numbers.length - 1);
        List<Long> longNumbers = Arrays.stream(numbers).map(Long::parseLong).toList();
        List<Operators> combination;
        while ((combination = generator.generateCombination()) != null) {
            Iterator<Operators> iter = combination.iterator();
            long total = 0;
            for (int i = 0; i < longNumbers.size(); i++) {
                if ( i == 0 ) {
                    total = longNumbers.get(i);
                    continue;
                }

                total = iter.next().apply(total, longNumbers.get(i));
            }
            if ( expected == total ) {
                return true;
            }
        }
        return false;
    }

    @Data
    public static class CombinationPiece<T> {
        private final LinkedList<T> possibleValues;
        private T currentValue;

        public CombinationPiece(LinkedList<T> possibleValues) {
            this.possibleValues = possibleValues;
            currentValue = possibleValues.getFirst();
        }

        public CombinationPiece(T... values) {
            this(new LinkedList<>(Arrays.stream(values).toList()));
        }

        public boolean isEnd() {
            return possibleValues.getLast().equals(currentValue);
        }

        public void reset() {
            currentValue = possibleValues.getFirst();
        }

        public void next() {
            if ( isEnd() || currentValue == null ) {
                currentValue = possibleValues.getFirst();
            } else {
                currentValue = possibleValues.get(possibleValues.indexOf(currentValue) + 1);
            }
        }

        public CombinationPiece<T> copy() {
            return new CombinationPiece<>(new LinkedList<>(possibleValues));
        }
    }

    @Data
    public static class CombinationGenerator<T> {
        private final List<CombinationPiece<T>> pieces;

        public CombinationGenerator(List<CombinationPiece<T>> pieces) {
            this.pieces = pieces;
            this.pieces.get(pieces.size() - 1).currentValue = null;
        }

        public CombinationGenerator(CombinationPiece<T> piece, int numberOfPieces) {
            List<CombinationPiece<T>> pieces = new ArrayList<>();
            for (int i = 0; i < numberOfPieces; i++) {
                pieces.add(piece.copy());
            }

            this.pieces = pieces;
            this.pieces.get(pieces.size() - 1).currentValue = null;
        }

        public List<T> generateCombination() {
            int startIndex = pieces.size() - 1;
            boolean newCombination = update(startIndex);
            if (newCombination) {
                return pieces.stream()
                        .map(CombinationPiece::getCurrentValue)
                        .toList();
            }
            return null;
        }

        private boolean update(int index) {
            CombinationPiece<T> current = pieces.get(index);
            if (current.isEnd()) {
                if (index == 0) {
                    // all done, no more combos
                    return false;
                } else {
                    current.reset();
                    return update(index - 1);
                }
            }
            current.next();
            return true;
        }
    }

    enum Operators {
        PLUS(Long::sum),
        MULTIPLY((n1, n2) -> n1 * n2),
        CONCAT((n1, n2) -> Long.parseLong(n1.toString() + n2.toString() ));

        private final BiFunction<Long, Long, Long> application;
        Operators(BiFunction<Long, Long, Long> application) {
            this.application = application;
        }

        public long apply(long n1, long n2) {
            return application.apply(n1, n2);
        }
    }
}
