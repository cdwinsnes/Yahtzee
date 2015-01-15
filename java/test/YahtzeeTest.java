import org.junit.Test;
import yatzy.ScoreType;
import yatzy.Yahtzee;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Christian Darre-Winsnes
 */
public class YahtzeeTest {

    @Test
    public void testScoreTypeRules() {
        assertTrue(ScoreType.ONES.rule().test(Arrays.asList(1, 2, 4, 1, 5)));
        assertFalse(ScoreType.ONES.rule().test(Arrays.asList(2, 3, 4, 5, 3)));
        assertTrue(ScoreType.TWOS.rule().test(Arrays.asList(1, 2, 4, 1, 5)));
        assertFalse(ScoreType.TWOS.rule().test(Arrays.asList(1, 3, 4, 5, 3)));
        assertTrue(ScoreType.THREES.rule().test(Arrays.asList(1, 2, 3, 3, 5)));
        assertFalse(ScoreType.THREES.rule().test(Arrays.asList(2, 6, 4, 5, 6)));
        assertTrue(ScoreType.FOURS.rule().test(Arrays.asList(4, 2, 4, 1, 5)));
        assertFalse(ScoreType.FOURS.rule().test(Arrays.asList(2, 3, 3, 5, 3)));
        assertTrue(ScoreType.FIVES.rule().test(Arrays.asList(1, 2, 4, 5, 5)));
        assertFalse(ScoreType.FIVES.rule().test(Arrays.asList(2, 3, 4, 4, 3)));
        assertTrue(ScoreType.SIXES.rule().test(Arrays.asList(6, 6, 4, 1, 5)));
        assertFalse(ScoreType.SIXES.rule().test(Arrays.asList(2, 3, 4, 5, 3)));
        assertTrue(ScoreType.PAIR.rule().test(Arrays.asList(1, 2, 4, 1, 5)));
        assertFalse(ScoreType.PAIR.rule().test(Arrays.asList(2, 1, 4, 5, 3)));
        assertTrue(ScoreType.TWO_PAIRS.rule().test(Arrays.asList(6, 1, 6, 5, 5)));
        assertFalse(ScoreType.TWO_PAIRS.rule().test(Arrays.asList(2, 1, 3, 5, 5)));
        assertTrue(ScoreType.THREE_OF_A_KIND.rule().test(Arrays.asList(6, 5, 6, 5, 5)));
        assertFalse(ScoreType.THREE_OF_A_KIND.rule().test(Arrays.asList(2, 1, 3, 5, 5)));
        assertTrue(ScoreType.FOUR_OF_A_KIND.rule().test(Arrays.asList(6, 6, 6, 5, 6)));
        assertFalse(ScoreType.FOUR_OF_A_KIND.rule().test(Arrays.asList(2, 5, 3, 5, 5)));
        assertTrue(ScoreType.SMALL_STRAIGHT.rule().test(Arrays.asList(2, 1, 3, 5, 4)));
        assertFalse(ScoreType.SMALL_STRAIGHT.rule().test(Arrays.asList(2, 1, 3, 5, 5)));
        assertTrue(ScoreType.LARGE_STRAIGHT.rule().test(Arrays.asList(6, 2, 3, 5, 4)));
        assertFalse(ScoreType.LARGE_STRAIGHT.rule().test(Arrays.asList(2, 4, 3, 5, 5)));
        assertTrue(ScoreType.YAHTZEE.rule().test(Arrays.asList(6, 6, 6, 6, 6)));
        assertFalse(ScoreType.YAHTZEE.rule().test(Arrays.asList(5, 1, 5, 5, 5)));
        assertTrue(ScoreType.CHANCE.rule().test(Arrays.asList(6, 1, 6, 5, 5)));
    }

    @Test
    public void testScoreTypeScores() {
        assertTrue(ScoreType.ONES.score().apply(Arrays.asList(6, 1, 6, 5, 5)).equals(1));
        assertTrue(ScoreType.TWOS.score().apply(Arrays.asList(2, 1, 6, 2, 5)).equals(4));
        assertTrue(ScoreType.THREES.score().apply(Arrays.asList(6, 3, 3, 3, 5)).equals(9));
        assertTrue(ScoreType.FOURS.score().apply(Arrays.asList(6, 4, 4, 2, 1)).equals(8));
        assertTrue(ScoreType.FIVES.score().apply(Arrays.asList(5, 1, 6, 5, 5)).equals(15));
        assertTrue(ScoreType.SIXES.score().apply(Arrays.asList(6, 1, 6, 5, 5)).equals(12));
        assertTrue(ScoreType.PAIR.score().apply(Arrays.asList(6, 1, 6, 5, 5)).equals(12));
        assertTrue(ScoreType.TWO_PAIRS.score().apply(Arrays.asList(6, 1, 6, 5, 5)).equals(22));
        assertTrue(ScoreType.THREE_OF_A_KIND.score().apply(Arrays.asList(5, 5, 6, 5, 5)).equals(15));
        assertTrue(ScoreType.FOUR_OF_A_KIND.score().apply(Arrays.asList(6, 6, 6, 6, 6)).equals(24));
        assertTrue(ScoreType.SMALL_STRAIGHT.score().apply(Arrays.asList(1, 2, 4, 5, 3)).equals(30));
        assertTrue(ScoreType.LARGE_STRAIGHT.score().apply(Arrays.asList(6, 4, 3, 5, 2)).equals(40));
        assertTrue(ScoreType.YAHTZEE.score().apply(Arrays.asList(6, 6, 6, 6, 6)).equals(50));
        assertTrue(ScoreType.CHANCE.score().apply(Arrays.asList(6, 1, 6, 5, 5)).equals(23));
    }

    @Test
    public void testSpecificScoreCalculation() {
        List<Integer> dices = Arrays.asList(6, 1, 6, 6, 6);
        assertTrue(Yahtzee.calculateScore(dices, ScoreType.TWO_PAIRS) == 24);
    }

    @Test
    public void testTopScoreCalculation() {
        List<Integer> dices = Arrays.asList(4, 2, 6, 3, 5);
        assertTrue(Yahtzee.calculateTopScore(dices) == 40);
    }

}
