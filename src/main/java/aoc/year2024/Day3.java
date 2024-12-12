package aoc.year2024;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Pattern p = Pattern.compile("(?:mul\\((\\d{1,3}),(\\d{1,3})\\))|(do\\(\\))|(don't\\(\\))");
        String s = Utils.getString("/aoc.year2024/day3.txt");
        System.out.println("s = " + s);

        Matcher m = p.matcher(s);

        long total = 0;
        boolean enabled = true;
        while (m.find()) {
            String g1 = m.group(1);
            String g2 = m.group(2);
            String g3 = m.group(3);
            String g4 = m.group(4);
            System.out.println(g1 + "|||" + g2 + "|||" + g3 + "|||" + g4);
            if ( g3 != null ) {
                enabled = true;
            } else if (g4 != null) {
                enabled = false;
            } else if (enabled) {
                total += (long) Integer.parseInt(g1) * Integer.parseInt(g2);
            }
        }
        System.out.println("total = " + total);
    }
}
