package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Tests for Order class: [300..399] setCreationDate() tests.
 * 
 * @author sgra64
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Order_300_CreationDateTest {
    //
    final Customer c1 = new Customer().setId(1L);
    final datamodel.Order o1 = new datamodel.Order(c1);
    final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final long lowerBound = dateFormat("2020-01-01 00:00:00");
    final long now = System.currentTimeMillis();
    final long upperBound = now + (24*60*60*1000L);	// +1day
    final String outOfBoundsMsg = "invalid datetime argument (outside bounds 01/01/2020 <= datetime <= now() + 1day).";


    /*
     * Test case: Constructor creation date.
     */
    @Test @Order(300)
    void test300_Constructor() {
        final var o1 = new datamodel.Order(c1);
        long age = o1.getCreationDate() - now;	// time passed sind creation in ms
        assertTrue(age < 1000L);		// order just created must not be older than 1000ms
    }


    /*
     * Test case: set creation date with regular value.
     */
    @Test @Order(310)
    void test310_setCreationDateRegularCase() {
        long created = o1.getCreationDate();
        long newCreationDate = created - 2*(24*60*60*1000L);	// -2days
        o1.setCreationDate(newCreationDate);
        assertEquals(o1.getCreationDate(), newCreationDate);
    }


    /*
     * Test case: set creation date with regular, lower bounds value.
     */
    @Test @Order(311)
    void test311_setCreationDateRegularLowerBounds() {
        o1.setCreationDate(lowerBound);
        assertEquals(o1.getCreationDate(), lowerBound);
    }


    /*
     * Test case: set creation date with regular, upper bounds value.
     */
    @Test @Order(312)
    void test312_setCreationDateRegularUpperBounds() {
        long delta = 10000L;	//use 10sec tolerance
        o1.setCreationDate(upperBound - delta);
        assertEquals(o1.getCreationDate(), upperBound - delta);
    }


    /*
     * Test case: set creation date with invalid lower value.
     */
    @Test @Order(320)
    void test320_setCreationDateInvalidLowerDateTimeRange() {
        long current = o1.getCreationDate();
        var thrown = assertThrows(
            IllegalArgumentException.class, () -> {
                o1.setCreationDate(Long.MIN_VALUE);		// must throw IllegalArgumentException
        });
        assertEquals(outOfBoundsMsg, thrown.getMessage());	// verify exception message
        assertEquals(o1.getCreationDate(), current);		// verify value unchanged
    }


    /*
     * Test case: set creation date with invalid upper value.
     */
    @Test @Order(321)
    void test321_setCreationDateInvalidUpperDateTimeRange() {
        long current = o1.getCreationDate();
        var thrown = assertThrows(
            IllegalArgumentException.class, () -> {
                o1.setCreationDate(Long.MAX_VALUE);		// must throw IllegalArgumentException
        });
        assertEquals(outOfBoundsMsg, thrown.getMessage());	// verify exception message
        assertEquals(o1.getCreationDate(), current);		// verify value unchanged
    }


    /*
     * Test case: set creation date with largest invalid value from lower range.
     */
    @Test @Order(322)
    void test322_setCreationDateInvalidLowerDateTimeBoundary() {
        long current = o1.getCreationDate();
        var thrown = assertThrows(
            IllegalArgumentException.class, () -> {
                o1.setCreationDate(lowerBound - 1L);		// must throw IllegalArgumentException
        });
        assertEquals(outOfBoundsMsg, thrown.getMessage());	// verify exception message
        assertEquals(o1.getCreationDate(), current);		// verify value unchanged
    }


    /*
     * Test case: set creation date with smallest invalid value from upper range.
     */
    @Test @Order(323)
    void test323_setCreationDateInvalidUpperDateTimeRange() {
        long current = o1.getCreationDate();
        var thrown = assertThrows(
            IllegalArgumentException.class, () -> {
                o1.setCreationDate(upperBound + 1L);		// must throw IllegalArgumentException
        });
        assertEquals(outOfBoundsMsg, thrown.getMessage());	// verify exception message
        assertEquals(o1.getCreationDate(), current);		// verify value unchanged
    }


    private long dateFormat(String datetime) {
        long datetime_ms = 0L;
        try {
            datetime_ms = df.parse(datetime).getTime();
        } catch(ParseException e) {
            e.printStackTrace();
        }
        //System.err.println(String.format("\"%s\" -> %d", datetime, datetime_ms));
        return datetime_ms;
    }
}
