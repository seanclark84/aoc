package aoc.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class CombinationGenerator<T> {
    private final List<CombinationPiece<T>> pieces;

    public CombinationGenerator(Collection<CombinationPiece<T>> pieces) {
        this(new ArrayList<>(pieces));
    }

    public CombinationGenerator(List<CombinationPiece<T>> pieces) {
        this.pieces = pieces;
        this.pieces.get(pieces.size() - 1).setCurrentValue(null);
    }

    public CombinationGenerator(CombinationPiece<T> piece, int numberOfPieces) {
        List<CombinationPiece<T>> pieces = new ArrayList<>();
        for (int i = 0; i < numberOfPieces; i++) {
            pieces.add(piece.copy());
        }

        this.pieces = pieces;
        this.pieces.get(pieces.size() - 1).setCurrentValue(null);
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