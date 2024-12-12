package aoc.year2024;

import lombok.Data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Day4 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Utils.getLines("/aoc.year2024/day4.txt");
        String[][] e = Utils.splitPerCharacter(lines);

        Entry[][] entries = new Entry[e.length][e[0].length];
        List<Entry> startLetters = new ArrayList<>();

        for (int i = 0; i < e.length; i++) {
            String[] line = e[i];
            for (int j = 0; j < line.length; j++) {
                String letter = e[i][j];
                Letters l = Letters.get(letter);
                Entry entry = new Entry(l);
                entries[i][j] = entry;

                if (l == Letters.X) {
                    startLetters.add(entry);
                }
            }
        }

        for (int i = 0; i < entries.length; i++) {
            Entry[] line = entries[i];
            for (int j = 0; j < line.length; j++) {
                Entry entry = entries[i][j];
                for (Dir dir: Dir.values()) {
                    Entry neighbour;
                    try {
                        neighbour = entries[i + dir.getY()][j + dir.getX()];
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        neighbour = null;
                    }
                    entry.entryDirection.put(dir, neighbour);
                }
            }
        }

        AtomicInteger count = new AtomicInteger();
        startLetters.forEach(ent -> count.addAndGet(ent.findWords()));

        System.out.println("count = " + count);
    }

    @Data
    public static class Entry {
        private final Letters letter;

        private Map<Dir, Entry> entryDirection = new HashMap<>();

        public Entry(Letters letter) {
            this.letter = letter;
        }

        @Override
        public String toString() {
            return "[" + letter + "]";
        }

        public int findWords() {
            int count = 0;
            for (Map.Entry<Dir, Entry> entry: entryDirection.entrySet()) {
                if (entry.getValue() != null && letter.getNextLetter() == entry.getValue().letter) {
                    boolean resp = entry.getValue().checkLetterInDirection(entry.getKey());
                    if (resp) count++;
                }
            }
            return count;
        }

        private boolean checkLetterInDirection(Dir dir) {
            if ( letter.getNextLetter() == null ) {
                return true;
            } else if (entryDirection.get(dir) == null || letter.getNextLetter() != entryDirection.get(dir).letter) {
                return false;
            }
            return entryDirection.get(dir).checkLetterInDirection(dir);
        }
    }

    public enum Dir {
        TOP_LEFT(-1, -1),
        TOP_CENTRE(0, -1),
        TOP_RIGHT(1, -1),
        RIGHT(1, 0),
        BOTTOM_RIGHT(1, 1),
        BOTTOM_CENTRE(0, 1),
        BOTTOM_LEFT(-1, 1),
        LEFT(-1, 0);

        private int x;
        private int y;
        Dir(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    private enum Letters {
        S(null),
        A(S),
        M(A),
        X(M),
        OTHER(null);

        private Letters nextLetter;

        Letters(Letters nextLetter) {
            this.nextLetter = nextLetter;
        }

        public Letters getNextLetter() {
            return nextLetter;
        }

        public static Letters get(String s) {
            Letters l;
            try {
                l = valueOf(s);
            } catch (IllegalArgumentException ex) {
                l = OTHER;
            }
            return l;
        }
    }
}