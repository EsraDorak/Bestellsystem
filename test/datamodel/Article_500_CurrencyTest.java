package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Tests for Article class: [500..599] setCurrency() tests.
 * 
 * @author sgra64
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Article_500_CurrencyTest {
    //
    final Article a1 = new Article("coffee maker", 100L);


    /*
     * Test cases 100: set currency with regular value.
     */
    @Test @Order(100)
    void test100_setCurrencyRegularValue() {
        assertEquals(a1.getCurrency(), Currency.EUR);
        a1.setCurrency(Currency.USD);
        assertEquals(a1.getCurrency(), Currency.USD);
    }

    /*
     * Test cases 110: set currency with null value, throws IllegalArgumentException.
     */
    @Test @Order(110)
    void test110_setCurrencyNullArgument() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> {
                a1.setCurrency(null);		// setCurrency(null) must throw IllegalArgumentException
        });
        assertEquals("invalid currency (null).", thrown.getMessage());	// verify exception message
        //
        assertEquals(a1.getCurrency(), Currency.EUR);	// currency remains unchanged
    }
}
