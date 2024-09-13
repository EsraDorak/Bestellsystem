package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Tests for Article class: [200..299] setId() tests.
 * 
 * @author sgra64
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Article_200_SetIdTest {
    //
    final Article a1 = new Article("description", 100L);
    final String id1 = "SKU-222222";
    final String id2 = "SKU-X222223";
    final String idSmall = "S";
    final String idLarge = id1.repeat(100000);	// 700k id


    /*
     * Test cases 200: set id with regular value.
     */
    @Test @Order(200)
    void test200_setIdRegularValue() {
        assertEquals(a1.getId(), null);	// returns null for unassigned id
        a1.setId(id1);					// assign id with long value
        assertEquals(a1.getId(), id1);	// return assigned id
    }


    /*
     * Test case: set id with smallest regular value.
     */
    @Test @Order(201)
    void test201_setIdMinValue() {
        a1.setId(idSmall);
        assertEquals(a1.getId(), idSmall);
    }


    /*
     * Test case: set id with largest regular value.
     */
    @Test @Order(202)
    void test202_setIdMaxValue() {
        a1.setId(idLarge);
        assertEquals(a1.getId(), idLarge);
    }


    /*
     * Test cases 210: set id with illegal value ( < 0) throws IllegalArgumentException
     * with message "invalid id (negative)." Id remains unchanged.
     */
    @Test @Order(210)
    void test210_setIdWithNullIllegalArguments() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> {
                a1.setId(null);		// setId(null) must throw IllegalArgumentException
        });
        assertEquals("invalid id (null or \"\").", thrown.getMessage());	// verify exception message
        //
        assertEquals(a1.getId(), null);		// id remains unchanged, unassigned in this case
    }


    @Test @Order(211)
    void test211_setIdWithEmptyIdIllegalArguments() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class, () -> {
                a1.setId("");		// setId(null) must throw IllegalArgumentException
        });
        assertEquals("invalid id (null or \"\").", thrown.getMessage());	// verify exception message
        //
        assertEquals(a1.getId(), null);		// id remains unchanged, unassigned in this case
    }


    /*
     * Test case 220: set id only once.
     */
    @Test @Order(220)
    void test220_setIdOnce() {
        a1.setId(id1);				// set id first time with regular value
        assertEquals(a1.getId(), id1);
        a1.setId(id2);				// attempt to set id new regular value: id2
        assertEquals(a1.getId(), id1);	// id remains unchanged: id1
        a1.setId(idSmall);				// attempt to set other id value
        assertEquals(a1.getId(), id1);	// id unchanged
        a1.setId(idLarge);				// attempt to set other id value
        assertEquals(a1.getId(), id1);	// id unchanged
    }
}
