package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Tests for Article class: [600..699] setTAX() tests.
 * 
 * @author sgra64
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Article_600_TAXTest {
    //
    final Article a1 = new Article("coffee maker", 100L);


    /*
     * Test cases 100: set tax rate with regular value.
     */
    @Test @Order(100)
    void test100_setTAXRegularValue() {
        assertEquals(a1.getTax(), TAX.GER_VAT);
        a1.setTax(TAX.GER_VAT_REDUCED);
        assertEquals(a1.getTax(), TAX.GER_VAT_REDUCED);
    }


    /*
     * Test cases 110: set tax rate with null value, throws IllegalArgumentException.
     */
    @Test @Order(110)
    void test110_setCurrencyNullArgument() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> {
                a1.setTax(null);		// setTax(null) must throw IllegalArgumentException
        });
        assertEquals("invalid tax (null).", thrown.getMessage());	// verify exception message
        //
        assertEquals(a1.getTax(), TAX.GER_VAT);		// tax remains unchanged
    }
}
