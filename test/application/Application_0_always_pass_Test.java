package application;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Application_0_always_pass_Test {

    @Test
    @Order(001)
    void test_001_always_pass() {
        int expected = 10;
        int actual = 10;
        assertEquals(expected, actual);
    }

}
