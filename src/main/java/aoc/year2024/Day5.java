package aoc.year2024;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Day5 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Utils.getLines("/aoc.year2024/day5.txt");

        boolean rules = true;
        Map<Integer, List<Integer>> numberPrerequisite = new HashMap<>();
        long total = 0;

        for (String line: lines) {
            if ( rules && !"".equals(line) ) {
                String[] rule = line.split("\\|");
                int number1 = Integer.parseInt(rule[0]);
                int number2 = Integer.parseInt(rule[1]);

                List<Integer> preReqs = numberPrerequisite.getOrDefault(number1, new ArrayList<>());
                preReqs.add(number2);
                numberPrerequisite.put(number1, preReqs);
            } else if ( "".equals(line)) {
                rules = false;
            } else {
                String[] printQueue = line.split(",");
                List<Integer> tmpList = new ArrayList<>(Arrays.stream(printQueue)
                        .map(Integer::parseInt)
                        .toList());

                boolean hadChanges = false;
                boolean keepChecking = true;
                CHECKING: while(keepChecking) {
                    for (int i = 0; i < tmpList.size(); i++) {
                        if (i == 0) continue;
                        //if (i == tmpList.size() - 1) continue;

                        List<Integer> beforeNumbers = numberPrerequisite.getOrDefault(tmpList.get(i), new ArrayList<>());

                        for (Integer before : beforeNumbers) {
                            if (tmpList.contains(before) && tmpList.indexOf(before) < i) {
                                hadChanges = true;
                                Integer current = tmpList.remove(i);
                                tmpList.add(tmpList.indexOf(before), current);
                                continue CHECKING;
                            }
                        }
                    }
                    keepChecking = false;
                }
                if (hadChanges) {
                    int middleIndex = tmpList.size() / 2;
                    total += tmpList.get(middleIndex);
                    System.out.println("middleIndex = " + middleIndex);
                }
            }
        }
        System.out.println(total);
    }
}
