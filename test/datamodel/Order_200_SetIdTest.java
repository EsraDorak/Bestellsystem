package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Tests for Order class: [200..299] setId() tests.
 * 
 * @author sgra64
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Order_200_SetIdTest {
    //
    final Customer c1 = new Customer().setId(1L);
    final datamodel.Order o1 = new datamodel.Order(c1);
    final String id1 = "X222222";
    final String id2 = "X222223";
    final String idSmall = "X";
    final String idLarge = id1.repeat(100000);	// 700k id


    /*
     * Test cases 200: set id with regular value.
     */
    @Test @Order(200)
    void test200_setIdRegularValue() {
        assertEquals(o1.getId(), null);	// returns null for unassigned id
        o1.setId(id1);					// assign id with long value
        assertEquals(o1.getId(), id1);	// return assigned id
    }


    /*
     * Test case: set id with smallest regular value.
     */
    @Test @Order(201)
    void test201_setIdMinValue() {
        o1.setId(idSmall);
        assertEquals(o1.getId(), idSmall);
    }


    /*
     * Test case: set id with largest regular value.
     */
    @Test @Order(202)
    void test202_setIdMaxValue() {
        o1.setId(idLarge);
        assertEquals(o1.getId(), idLarge);
    }


    /*
     * Test cases 210: set id with illegal value ( < 0) throws IllegalArgumentException
     * with message "invalid id (negative)." Id remains unchanged.
     */
    @Test @Order(210)
    void test210_setIdWithNullArgument() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> {
                o1.setId(null);		// setId(null) must throw IllegalArgumentException
        });
        assertEquals("invalid id (null or \"\").", thrown.getMessage());	// verify exception message
        //
        assertEquals(o1.getId(), null);		// id remains unchanged, unassigned in this case
    }


    @Test @Order(211)
    void test211_setIdWithEmptyArgument() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> {
                o1.setId("");		// setId(null) must throw IllegalArgumentException
        });
        assertEquals("invalid id (null or \"\").", thrown.getMessage());	// verify exception message
        //
        assertEquals(o1.getId(), null);		// id remains unchanged, unassigned in this case
    }


    /*
     * Test case 220: set id only once.
     */
    @Test @Order(220)
    void test220_setIdOnce() {
        o1.setId(id1);				// set id first time with regular value
        assertEquals(o1.getId(), id1);
        o1.setId(id2);				// attempt to set id new regular value: id2
        assertEquals(o1.getId(), id1);	// id remains unchanged: id1
        o1.setId(idSmall);				// attempt to set other id value
        assertEquals(o1.getId(), id1);	// id unchanged
        o1.setId(idLarge);				// attempt to set other id value
        assertEquals(o1.getId(), id1);	// id unchanged
    }
}
