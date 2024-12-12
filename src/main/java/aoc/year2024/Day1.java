package aoc.year2024;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day1 {
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

        long diff = 0;

        for (int i = 0; i < firstNumbers.size(); i++) {
            long tmp = firstNumbers.get(i) - secondNumbers.get(i);
            if (tmp < 0) {
                tmp *= -1;
            }
            diff += tmp;
        }

        System.out.println("diff = " + diff);
    }
}
