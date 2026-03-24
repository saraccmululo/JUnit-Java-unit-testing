package calculator;

import com.sara.unittestingpractice.entity.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
  Calculator calculator;

  @BeforeEach
  void setUp() {
    calculator = new Calculator();
  }

  @ParameterizedTest
  @CsvSource({
      "2,3,5",
      "3,4,7",
      "0,0,0",
      "-2,1,-1"
  })
  void testAdd(int a, int b, int expected) {
    assertEquals(expected, calculator.add(a,b));
  }

  @ParameterizedTest
  @CsvSource({
      "3,2,1",
      "2,0,2",
      "-2,1,-3"
  })
  void testSubtract(int a, int b, int expected) {
    assertEquals(expected, calculator.subtract(a,b));
  }

  @ParameterizedTest
  @CsvSource({
      "2,3,6",
      "0,10,0",
      "-1,2,-2"
  })
  void testMultiply(int a, int b, int expected) {
    assertEquals(expected, calculator.multiply(a,b));
  }

  @ParameterizedTest
  @CsvSource({
      "6,3,2",
      "6,-2,-3"
  })
  void testDivide(int a, int b, int expected) {
    assertEquals(expected, calculator.divide(a,b));
  }

  @ParameterizedTest
  @CsvSource({
      "5,0",
      "-10, 0",
      "0,0"
  })
  void testDivideByZero(int a, int b) {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> calculator.divide(a,b));
    assertEquals("Cannot divide by zero", exception.getMessage());
  }
}
