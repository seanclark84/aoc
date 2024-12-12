package aoc.year2024;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day1_2 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Utils.getLines("/aoc.year2024/day1.txt");
        String[][] parsed = Utils.splitOnWhiteSpace(lines);

        List<Integer> firstNumbers = new ArrayList<>();
        List<Integer> secondNumbers = new ArrayList<>();
        for (int i = 0; i < parsed.length; i++) {
            firstNumbers.add(Integer.parseInt(parsed[i][0]));
            secondNumbers.add(Integer.parseInt(parsed[i][1]));
        }
        firstNumbers.sort(Comparator.naturalOrder());
        secondNumbers.sort(Comparator.naturalOrder());

        Map<Integer, Integer> rightCounts = secondNumbers.stream()
                .collect(Collectors.toMap(i -> i, i -> 1, (i, j) -> i + 1));

        long diff = 0;

        for (int i = 0; i < firstNumbers.size(); i++) {
            int tmp = firstNumbers.get(i);
            Integer count = rightCounts.get(tmp);

            diff += (long) tmp * ( count == null ? 0 : count );
        }

        System.out.println("diff = " + diff);
    }
}
