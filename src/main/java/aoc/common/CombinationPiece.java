package aoc.common;

import lombok.Data;

import java.util.Arrays;
import java.util.LinkedList;

@Data
public class CombinationPiece<T> {
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