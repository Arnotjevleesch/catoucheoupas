package fr.arnotjevleesch;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static final Supplier<Stream<String>> LETTERS = () -> Stream.of("M", "B", "P");
    public static final Supplier<Stream<String>> COMBI_TO_EXCLUDE = () -> Stream.of(
        "MM", "BB", "PP", "MB", "MP", "BM", "BP", "PM", "PB"
    );
    public static final String FILENAME = "ods3.txt";

    public static void main(String[] args) {
        System.out.println(howMuchWordsTouch());
        System.out.println(howMuchWordsNotTouch());
        printNTouch(max());
    }

    public static int howManyTimesTouch(String word) {
        int pos = LETTERS.get().collect(Collectors.summingInt(l -> StringUtils.countMatches(word, l)));
        int neg = COMBI_TO_EXCLUDE.get().collect(Collectors.summingInt(l -> StringUtils.countMatches(word, l)));

        return pos - neg;
    }

    public static void printNTouch(int nb) {
        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {

            stream.filter(s -> howManyTimesTouch(s) == nb).forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int max() {
       //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {

            return howManyTimesTouch(stream.max(Comparator.comparingInt(Main::howManyTimesTouch)).get());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static long howMuchWordsTouch() {
        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {

            return stream.filter(s -> howManyTimesTouch(s) > 0).count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static long howMuchWordsNotTouch() {
        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {

            return stream.filter(s -> howManyTimesTouch(s) == 0).count();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
