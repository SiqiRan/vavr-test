package io.vavr;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BehaviorExperimentTest {
    @Test
    public void testWithJavaOptional() {
        // Java Optional
        String result = Optional.of("hello")
                .map(str -> (String) null)
                .orElseGet(() -> "world");

        // result = "world"
        assertEquals("world", result);
    }

    @Test
    public void testWithVavrOption() {
        // Vavr Option
        String  result = Option.of("hello")
                .map(str -> (String) null)
                .getOrElse(() -> "world");

        // result = null
        assertNull(result);
    }

    @Test
    public void testRemoveBehaviour(){
        List<Integer> original = List.of(122,333,789,421,522,61);
        List<Integer> res = original.removeAt(4);
        assertEquals(res,List.of(122,333,789,421,61));
    }

}
