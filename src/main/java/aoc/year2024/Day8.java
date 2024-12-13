package aoc.year2024;

import aoc.common.*;
import lombok.NonNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Day8 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Grid<AntennaGridEntry> grid = Utils.parseToGrid("/aoc.year2024/day8.txt", AntennaGridEntry.class);
        Map<String, List<AntennaGridEntry>> perFreqAntennas = new HashMap<>();
        for (AntennaGridEntry e : grid.getAllGridEntries()) {
            if (e.getData().equals(".")) continue;
            List<AntennaGridEntry> tmp = new ArrayList<>(List.of(e));
            perFreqAntennas.merge(e.getData(), tmp, (l, t) -> {
                l.addAll(t);
                return l;
            });
        }
        System.out.println(perFreqAntennas);
        
        Set<AntennaGridEntry> antiNodes = new HashSet<>();
        for (Map.Entry<String, List<AntennaGridEntry>> entry: perFreqAntennas.entrySet()) {
            CombinationPiece<AntennaGridEntry> combPiece = new CombinationPiece<>(new LinkedList<>(entry.getValue()));
            CombinationGenerator<AntennaGridEntry> gen = new CombinationGenerator<>(combPiece, 2);
            List<AntennaGridEntry> tmpAntennaGridEntry;
            while ((tmpAntennaGridEntry = gen.generateCombination()) != null) {
                if ( tmpAntennaGridEntry.get(0).equals(tmpAntennaGridEntry.get(1)) ) continue;
                int xdiff = tmpAntennaGridEntry.get(1).getCoordinate().getFirst() - tmpAntennaGridEntry.get(0).getCoordinate().getFirst();
                int ydiff = tmpAntennaGridEntry.get(1).getCoordinate().getSecond() - tmpAntennaGridEntry.get(0).getCoordinate().getSecond();
                AntennaGridEntry translated = tmpAntennaGridEntry.get(0).translate(xdiff, ydiff);
                while (translated != null) {
                    antiNodes.add(translated);
                    translated = translated.translate(xdiff, ydiff);
                }
            }
        }
        System.out.println("antiNodes.size() = " + antiNodes.size());
        System.out.println("antiNodes = " + antiNodes.stream().map(AntennaGridEntry::getCoordinate).toList());
    }
    
    public static class AntennaGridEntry extends GridEntry<AntennaGridEntry> {

        public AntennaGridEntry(@NonNull String data, @NonNull Grid<AntennaGridEntry> grid, Coordinate coordinate) {
            super(data, grid, coordinate);
        }

        public boolean isAntiNode() {
            return true;
        }
    }
}
