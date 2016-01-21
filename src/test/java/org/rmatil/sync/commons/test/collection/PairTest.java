package org.rmatil.sync.commons.test.collection;

import org.junit.Test;
import org.rmatil.sync.commons.collection.Pair;

import static org.junit.Assert.*;

public class PairTest {

    @Test
    public void test() {
        Pair<String, String> pair = new Pair<>("", "");

        assertEquals("First should be empty", "", pair.getFirst());
        assertEquals("2nd should be empty", "", pair.getSecond());

        String first = "first";
        String second = "second";

        pair.setFirst(first);
        pair.setSecond(second);

        assertEquals("First should not be empty", first, pair.getFirst());
        assertEquals("2nd should be empty", second, pair.getSecond());

        Pair<String, String> secondPair = new Pair<>(first, second);

        assertEquals("First should be equal to second pair", pair, secondPair);

    }
}
