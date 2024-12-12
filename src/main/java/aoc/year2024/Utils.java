package aoc.year2024;

import aoc.common.Grid;
import aoc.common.GridEntry;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utils {

    public static List<String> getLines( String path ) throws URISyntaxException, IOException {
        return Files.readAllLines(
                Paths.get(Objects.requireNonNull(Utils.class.getResource(path)).toURI()), Charset.defaultCharset());
    }

    public static String[][] splitOnWhiteSpace(List<String> input) throws URISyntaxException, IOException {
        return null;
    }

    public static String getString(String path) throws URISyntaxException, IOException {
        return Files.readString(
                Paths.get(Objects.requireNonNull(Utils.class.getResource(path)).toURI()), Charset.defaultCharset());
    }

    public static String[][] splitPerCharacter(List<String> input) {
        return input.stream()
                .map(s -> s.split(""))
                .toList()
                .toArray(new String[][] {});
    }

    public static <T extends GridEntry> Grid<T> parseToGrid(String path, Class<T> gridEntryType) throws URISyntaxException, IOException {
        return new Grid<>(splitPerCharacter(getLines(path)), gridEntryType);
    }
}
