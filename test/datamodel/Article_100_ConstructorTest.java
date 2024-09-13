package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Tests for Article class: [100..199] Constructor tests.
 * 
 * @author sgra64
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Article_100_ConstructorTest {
    //
    final String invalidDescrMsg = "invalid description (null or \"\").";

    /*
     * Test case: Constructor with no name argument.
     */
    @Test @Order(100)
    void test100_Constructor() {
        final Article a1 = new Article();		// Constructor with no name argument
        assertEquals(a1.getId(), null);			// returns null for unassigned id
        assertEquals(a1.getDescription(), "");	// description: ""
        assertEquals(a1.getUnitPrice(), 0L);	// unitPrice: 0L
        assertEquals(a1.getCurrency(), Currency.EUR);	// EUR is default currency
        assertEquals(a1.getTax(), TAX.GER_VAT);	// default tax
    }


    /*
     * Test case: Constructor with regular name argument.
     */
    @Test @Order(101)
    void test101_ConstructorWithRegularArguments() {
        final Article a1 = new Article("article", 10L);	// Constructor with regular arguments
        assertEquals(a1.getId(), null);					// returns null for unassigned id
        assertEquals(a1.getDescription(), "article");	// description: ""
        assertEquals(a1.getUnitPrice(), 10L);			// unitPrice: 0L
        assertEquals(a1.getCurrency(), Currency.EUR);	// EUR is default currency
        assertEquals(a1.getTax(), TAX.GER_VAT);			// default tax
    }


    /*
     * Test case: Constructor with empty name argument: new Article("", price)
     * throws IllegalArgumentException with message.
     */
    @Test @Order(102)
    void test102_ConstructorWithEmptyDescriptionArgument() {
        IllegalArgumentException thrown =
            assertThrows(
                IllegalArgumentException.class, () -> {
                    new Article("", 10L);	// must throw IllegalArgumentException
        });
        assertEquals(invalidDescrMsg, thrown.getMessage());
    }


    /*
     * Test case: Constructor with null name argument: new Article(null, price)
     * throws IllegalArgumentException with message.
     */
    @Test @Order(103)
    void test103_ConstructorWithNullArgument() {
        IllegalArgumentException thrown =
            assertThrows(
                IllegalArgumentException.class, () -> {
                    new Article(null, 10L);
        });
        assertEquals(invalidDescrMsg, thrown.getMessage());
    }


    /*
     * Test case: Constructor with null name argument: new Article("regular", -1)
     * throws IllegalArgumentException with message.
     */
    @Test @Order(104)
    void test1034_ConstructorWithNegativePriceArgument() {
        IllegalArgumentException thrown =
            assertThrows(
                IllegalArgumentException.class, () -> {
                    new Article("regular", -1L);
        });
        assertEquals("invalid unitPrice ( < 0).", thrown.getMessage());
    }


    /*
     * Test case: Constructor with empty name argument: new Article("")
     * throws IllegalArgumentException with message "name empty."
     */
    @Test @Order(104)
    void test104_ChainableSetters() {
        final Article a1 = new Article();
        assertSame(a1, a1.setId("SKU-0000"));	// setter must return same object reference
        assertSame(a1, a1.setDescription("some text"));
        assertSame(a1, a1.setUnitPrice(100L));
        assertSame(a1, a1.setCurrency(Currency.EUR));
        assertSame(a1, a1.setTax(TAX.GER_VAT));
    }
}
