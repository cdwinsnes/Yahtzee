package yatzy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public enum ScoreType {
    ONES(p -> atLeastOneDiceWithValue(p, DiceValue.ONE), s -> sumOfDices(s, DiceValue.ONE.value())),
    TWOS(p -> atLeastOneDiceWithValue(p, DiceValue.TWO), s -> sumOfDices(s, DiceValue.TWO.value())),
    THREES(p -> atLeastOneDiceWithValue(p, DiceValue.THREE), s -> sumOfDices(s, DiceValue.THREE.value())),
    FOURS(p -> atLeastOneDiceWithValue(p, DiceValue.FOUR), s -> sumOfDices(s, DiceValue.FOUR.value())),
    FIVES(p -> atLeastOneDiceWithValue(p, DiceValue.FIVE), s -> sumOfDices(s, DiceValue.FIVE.value())),
    SIXES(p -> atLeastOneDiceWithValue(p, DiceValue.SIX), s -> sumOfDices(s, DiceValue.SIX.value())),
    PAIR(p -> p.stream().distinct().count() <= 4, s -> sumOfTopDices(s, 2)),
    THREE_OF_A_KIND(p -> p.stream().distinct().count() <= 3 && requiredNumberOfDicesWithSameValue(p, 3), s -> sumOfTopDices(s, 3)),
    FOUR_OF_A_KIND(p -> p.stream().distinct().count() == 2 && requiredNumberOfDicesWithSameValue(p, 1), s -> sumOfTopDices(s, 4)),
    FULL_HOUSE(p -> p.stream().distinct().count() == 2 && requiredNumberOfDicesWithSameValue(p, 2), s -> 25),
    SMALL_STRAIGHT(p -> p.stream().distinct().count() == 5 && p.stream().sorted().findFirst().get().equals(1) && p.stream().noneMatch(DiceValue.SIX.value()), s -> 30),
    LARGE_STRAIGHT(p -> p.stream().distinct().count() == 5 && p.stream().sorted().findFirst().get().equals(2), s -> 40),
    TWO_PAIRS(p -> p.stream().distinct().count() <= 3 && p.stream().distinct().count() > 1, s -> sumOfTopDices(s, 2) + sumOfTopDices(reduceByTopDices(s, 2), 2)),
    CHANCE(p -> !p.isEmpty(), s -> s.stream().collect(Collectors.summingInt(Integer::intValue))),
    YAHTZEE(p -> p.stream().distinct().count() == 1, s -> 50);

    private final Predicate<List<Integer>> rule;
    private final Function<List<Integer>, Integer> score;

    ScoreType(Predicate<List<Integer>> rule, Function<List<Integer>, Integer> score) {
        this.rule = rule;
        this.score = score;
    }

    public Predicate<List<Integer>> rule() {
        return this.rule;
    }

    public Function<List<Integer>, Integer> score() {
        return this.score;
    }

    private static boolean atLeastOneDiceWithValue(List<Integer> p, DiceValue diceValue) {
        return p.stream().anyMatch(diceValue.value());
    }

    private static boolean requiredNumberOfDicesWithSameValue(List<Integer> p, int numberOfDices) {
        return p.stream().collect(Collectors.groupingBy(Integer::intValue)).values().stream().anyMatch(list -> list.size() == numberOfDices);
    }

    private static Integer sumOfDices(List<Integer> s, Predicate<Integer> dicesOfInterest) {
        return s.stream().filter(dicesOfInterest).collect(Collectors.summingInt(Integer::intValue));
    }

    private static Integer sumOfTopDices(List<Integer> dices, int numberOfDices) {
        Map<Integer, Integer> grouped = Stream.of(dices.toArray(new Integer[dices.size()])).collect(Collectors.toMap(i -> i, j -> j, (i, j) -> i + j));
        IntStream.Builder builder = IntStream.builder();
        grouped.entrySet().forEach(c -> Stream.of(c.getValue()).filter(f -> f/c.getKey() >= numberOfDices).forEach(d -> builder.add(c.getKey())));
        Integer topDice = (Integer) builder.build().boxed().collect(Collectors.toCollection(LinkedList::new)).descendingIterator().next();
        return topDice * numberOfDices;
    }

    private static List<Integer> reduceByTopDices(List<Integer> dices, int numberOfDicesToRemove) {
        Collections.sort(dices);
        return dices.size() < numberOfDicesToRemove ? Collections.emptyList() : dices.subList(0, dices.size() - numberOfDicesToRemove);

    }

}
