import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
  Calculator calculator = new Calculator();
  @Test
  void testAdd() {
    assertEquals(5, calculator.add(2,3));
    assertEquals(0, calculator.add(0, 0));
    assertEquals(-1, calculator.add(-2,1));
    assertEquals(-5, calculator.add(-6,1));
  }
  @Test
  void testSubtract() {
    assertEquals(-1, calculator.subtract(2, 3));
    assertEquals(2, calculator.subtract(2, 0));
    assertEquals(-3, calculator.subtract(-2, 1));
  }

  @Test
  void testMultiply() {
    assertEquals(6, calculator.multiply(2, 3));
    assertEquals(0, calculator.multiply(0, 10));
    assertEquals(-2, calculator.multiply(-1, 2));
  }

  @Test
  void testDivide() {
    assertEquals(2, calculator.divide(6, 3));
    assertEquals(-3, calculator.divide(6, -2));
  }

  @Test
  void testDivideByZero() {
    assertThrows(IllegalArgumentException.class, () -> calculator.divide(5, 0));
  }
}
