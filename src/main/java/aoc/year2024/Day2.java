package aoc.year2024;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Day2 {
    private static final Set<Integer> DATA = new HashSet<>(Arrays.asList(1, 2, 3, -1, -2, -3));
    
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = Utils.getLines("/aoc.year2024/day2.txt");
        String[][] parsed = Utils.splitOnWhiteSpace(lines);
        int success = 0;
        int failure = 0;

        for (int i = 0; i < parsed.length; i++) {
            String[] data = parsed[i];
            if( check(data) ) {
                success++;
            } else {
                failure++;
            }

        }

        System.out.println("success = " + success);
        System.out.println("failure = " + failure);
    }

    private static boolean check(String[] dataIn) {
        List<Integer> lineData = Arrays.stream(dataIn).map(Integer::parseInt).toList();

        boolean ret = check(lineData);
        if (ret) return true;

        for (int i = 0; i < lineData.size(); i++) {
            List<Integer> lineDataCopy = new ArrayList<>(lineData);
            lineDataCopy.remove(i);
            if ( check(lineDataCopy) ) {
                return true;
            }
        }

        return false;
    }

    private static  boolean check(List<Integer> dataIn) {
        Boolean positive = null;
        for (int j = 0; j < dataIn.size(); j++) {
            if ( j == 0 ) continue;
            int diff = dataIn.get(j) - dataIn.get(j - 1);
            if ( DATA.contains(diff) ) {
                if (positive == null) {
                    positive = diff > 0;
                } else {
                    if ( positive ^ diff > 0 ) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
