package com.makotojava.learn.hellojunit5;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JUnit 5 (with JUnitPlatform.class)
 * 
 */
@RunWith(JUnitPlatform.class)
@DisplayName("Tutorial for Homework 1")
public class JUnit5AppTest {

  // Create a JDK Logger here
  private static final Logger logger = LoggerFactory.getLogger(JUnit5AppTest.class);

  // Create a fixture for the class under test
  private App app;

  // Do something before ANY test is run in this class
  @BeforeAll
  public static void init() {
    logger.info("before all");
  }

  // Do something after ALL tests in this class are run
  @AfterAll
  public static void done() {
    logger.info("after all");
  }

  // Create an instance of the test class before each @Test method is executed
  @BeforeEach
  public void setUp() throws Exception {
    logger.info("set up");
    app = new App();
  }

  // Destroy reference to the instance of the test class after each @Test method is executed
  @AfterEach
  public void tearDown() throws Exception {
    logger.info("tear down");
    app = null;
  }

  // Disabled test
  @Test
  @Disabled
  @DisplayName("disabled test")
  void testNotRun() {
    logger.info("Nothing to see here");
  }

  @Test
  @DisplayName("testing add")
  public void testAdd() {
    logger.info("normal test add");
    assertNotNull(app);
    
    assertAll(
      () -> {
        long[] nums1 = {1, 2, 3, 4};
        long add1 = app.add(nums1);
        long exp1 = 10;
        assertEquals(exp1, add1);
      },
      () -> {
        long[] nums2 = {20, 934, 110};
        long add2 = app.add(nums2);
        long exp2 = 1064;
        assertEquals(exp2, add2);
      },
      () -> {
        long[] nums3 = {2, 4, 6};
        long add3 = app.add(nums3);
        long exp3 = 12;
        assertEquals(exp3, add3);
      });
  }

  @Nested
  @DisplayName("nested class for negative numbers")
  class NegativeNumbersTest {
    private App app;

    // Create an instance of the test class before each @Test method is executed
    @BeforeEach
    public void setUp() throws Exception {
      logger.info("set up");
      app = new App();
    }

    // Destroy reference to the instance of the test class after each @Test method is executed
    @AfterEach
    public void tearDown() throws Exception {
      logger.info("tear down");
      app = null;
    }

    @Test
    @DisplayName("add negative numbers")
    public void testAdd() {
      logger.info("negative test add");
      assertNotNull(app);
      
      assertAll(
        () -> {
          long[] nums1 = {-1, -2, -3, -4};
          long add1 = app.add(nums1);
          long exp1 = -10;
          assertEquals(exp1, add1);
        },
        () -> {
          long[] nums2 = {-20, -934, -110};
          long add2 = app.add(nums2);
          long exp2 = -1064;
          assertEquals(exp2, add2);
        },
        () -> {
          long[] nums3 = {-2, -4, -6};
          long add3 = app.add(nums3);
          long exp3 = -12;
          assertEquals(exp3, add3);
        });
    }
  }

  @Nested
  @DisplayName("class for mixed positive and negative numbers")
  class PositiveAndNegativeNumbersTest {

    @Test
    @DisplayName("add positive and negative numbers")
    public void testAdd() {
      logger.info("positive negative test add");
      assertNotNull(app);
      
      assertAll(
        () -> {
          long[] nums1 = {-1, 2, -3, 4};
          long add1 = app.add(nums1);
          long exp1 = 2;
          assertEquals(exp1, add1);
        },
        () -> {
          long[] nums2 = {-20, 934, -110};
          long add2 = app.add(nums2);
          long exp2 = 804;
          assertEquals(exp2, add2);
        },
        () -> {
          long[] nums3 = {-2, -4, 6};
          long add3 = app.add(nums3);
          long exp3 = 0;
          assertEquals(exp3, add3);
        });
    }

    @Test
    @DisplayName("test to be run only on fridays")
    public void testAdd_OnlyOnFriday() {
      logger.info("test add friday");
      LocalDateTime ldt = LocalDateTime.now();
      assumeTrue(ldt.getDayOfWeek().getValue() == 5);

      long[] nums = {1, 2, 3, 4, 5};
      long add_result = app.add(nums);
      long expected = 15;
      assertEquals(expected, add_result);
    }

    @Test
    @DisplayName("test to be run only on fridays and it has a lambda")
    public void testAdd_OnlyOnFriday_WithLambda() {
      logger.info("test add friday lambda");
      LocalDateTime ldt = LocalDateTime.now();
      assumingThat(ldt.getDayOfWeek().getValue() == 5,
      () -> {
        long[] nums = {1, 2, 3, 4, 5};
        long add_result = app.add(nums);
        long expected = 15;
        assertEquals(expected, add_result);
      });
    }

  }

  @Nested
  @DisplayName("single operand test class")
  class JUnit5AppSingleOperandTest {

    @Test
    @DisplayName("test array of 1 positive number")
    public void testAdd_NumbersGt0() {
      logger.info("1 positive test add");
      assertNotNull(app);

      assertAll(
        () -> {
          long[] nums = {1};
          long add_result = app.add(nums);
          long expected = 1;
          assertEquals(expected, add_result);
        }, 
        () -> {
          long[] nums2 = {0};
          long add_result2 = app.add(nums2);
          long expected2 = 0;
          assertEquals(expected2, add_result2);
        }
      );
    }

    @Test
    @DisplayName("test array of 1 negative number")
    public void testAdd_NumbersLt0() {
      logger.info("1 negative test add");
      assertNotNull(app);

      assertAll(
        () -> {
          long[] nums = {-1};
          long add_result = app.add(nums);
          long expected = -1;
          assertEquals(expected, add_result);
        }, 
        () -> {
          long[] nums2 = {-10};
          long add_result2 = app.add(nums2);
          long expected2 = -10;
          assertEquals(expected2, add_result2);
        }
      );
    }
  }

  @Nested
  @DisplayName("class for empty array")
  class JUnit5AppZeroOperandsTest {

    @Test
    @DisplayName("test empty array")
    public void testAdd_ZeroOperands_EmptyArgument() {
      logger.info("empty array test add");
      assertNotNull(app);

      long[] nums = {};
      assertThrows(IllegalArgumentException.class, () -> app.add(nums));
    }

    @Test
    @DisplayName("test null")
    public void testAdd_ZeroOperands_NullArgument() {
      logger.info("null array test add");
      assertNotNull(app);

      long[] nums = null;
      Throwable excep = assertThrows(IllegalArgumentException.class, () -> app.add(nums));
      assertEquals("Operands argument cannot be null", excep.getLocalizedMessage());
    }

  }

}
