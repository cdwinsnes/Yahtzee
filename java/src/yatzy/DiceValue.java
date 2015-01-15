package yatzy;

import java.util.function.Predicate;

/**
 * @author Christian Darre-Winsnes
 */
public enum DiceValue {
    ONE(p -> p.equals(1)),
    TWO(p -> p.equals(2)),
    THREE(p -> p.equals(3)),
    FOUR(p -> p.equals(4)),
    FIVE(p -> p.equals(5)),
    SIX(p -> p.equals(6));

    private Predicate<Integer> value;

    DiceValue(Predicate<Integer> value) {
        this.value = value;
    }

    public Predicate<Integer> value() {
        return this.value;
    }

}
