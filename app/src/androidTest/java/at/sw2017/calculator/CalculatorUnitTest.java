package at.sw2017.calculator;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CalculatorUnitTest {

    @Test
    public void testDoAddition() {
        int result = Calculations.doAddition(2, 3);
        assertEquals(5, result);
    }

    @Test
    public void testDoSubtraction() {
        int result = Calculations.doSubtraction(7, 2);
        assertEquals(5, result);
    }

    @Test
    public void testDoMultiplication() {
        int result = Calculations.doMultiplication(2, 3);
        assertEquals(6, result);
    }

    @Test
    public void testDoDivision() {
        int result = Calculations.doDivision(8, 4);
        assertEquals(2, result);
    }

    @Test
    public void testDoDivision1() {
        int result = Calculations.doDivision(8, 0);
        assertEquals(0, result);
    }

    @Test
    public void testDoDivision2() {
        int result = Calculations.doDivision(11, 4);
        assertEquals(2, result);
    }

    @Test
    public void testPrivateConstructor() throws Exception {
        final Constructor<?>[] constructors = Calculations.class.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        }

        constructors[0].setAccessible(true);
        constructors[0].newInstance((Object[]) null);
    }

    @Test
    public void testEnum() {
        assertNotNull(Calculator.State.valueOf("ADD"));
        assertNotNull(Calculator.State.valueOf("SUB"));
        assertNotNull(Calculator.State.valueOf("MUL"));
        assertNotNull(Calculator.State.valueOf("DIV"));
        assertNotNull(Calculator.State.valueOf("INIT"));
        assertNotNull(Calculator.State.valueOf("NUM"));
    }
}