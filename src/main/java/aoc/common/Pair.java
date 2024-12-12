package aoc.common;

import lombok.Data;

@Data
public class Pair<X, Y>{
    private X first;
    private Y second;
    public Pair(X first, Y second) {
        this.first = first;
        this.second = second;
    }

    public static <X, Y> Pair<X, Y> of(X first, Y second) {
        return new Pair<>(first, second);
    }
}
