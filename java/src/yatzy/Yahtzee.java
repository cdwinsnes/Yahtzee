package yatzy;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Yahtzee {


    public static int calculateScore(List<Integer> dices, ScoreType scoreType) {
        return scoreType.rule().test(dices) ? scoreType.score().apply(dices) : 0;
    }

    public static int calculateTopScore(List<Integer> dices) {
        ScoreType score = (ScoreType) Stream.of(ScoreType.values()).filter(
                p -> p.rule().test(dices)).collect(Collectors.maxBy(
                (o1, o2) -> o1.score().apply(dices).compareTo(o2.score().apply(dices)))).get();
        return score != null ? score.score().apply(dices) : 0;
    }

    public static List<Integer> rollDices(int numberOfDices) {
        Integer[] dices = new Integer[numberOfDices];
        Random random = new Random();
        for(int i = 0; i < numberOfDices; i++) {
            dices[i] = 1 + random.nextInt(6);
        }
        return Arrays.asList(dices);
    }
}
