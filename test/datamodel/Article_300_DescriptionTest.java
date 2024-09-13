package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Tests for Article class: [300..399] setDescription() and setUnitPrice() tests.
 * 
 * @author sgra64
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Article_300_DescriptionTest {
    //
    final String descr1 = "coffee maker";
    final String descr2 = "egg boiler";
    final String descrSmall = "c";
    final String descrLarge = descr1.repeat(100000);	// 700k id
    //
    final Article a1 = new Article(descr1, 100L);


    /*
     * Test cases 100: set description with regular value.
     */
    @Test @Order(100)
    void test100_setDescriptionRegularValue() {
        assertEquals(a1.getDescription(), descr1);
        a1.setDescription(descr2);
        assertEquals(a1.getDescription(), descr2);
    }


    /*
     * Test cases 110: set description with null argument, throws IllegalArgumentException
     * with message "invalid description (null or \"\")." Description remains unchanged.
     */
    @Test @Order(110)
    void test110_setDescriptionWithNullArgument() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> {
                a1.setDescription(null);		// setDescription(null) must throw IllegalArgumentException
        });
        // verify exception message
        assertEquals("invalid description (null or \"\").", thrown.getMessage());
        //
        assertEquals(a1.getDescription(), descr1);	// description remains unchanged
    }


    /*
     * Test cases 111: set description with empty argument "", throws IllegalArgumentException
     * with message "invalid description (null or \"\")." Description remains unchanged.
     */
    @Test @Order(111)
    void test111_setDescriptionWithEmptyArgument() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> {
                a1.setDescription("");		// setDescription(null) must throw IllegalArgumentException
        });
        // verify exception message
        assertEquals("invalid description (null or \"\").", thrown.getMessage());
        //
        assertEquals(a1.getDescription(), descr1);	// description remains unchanged
    }
}
