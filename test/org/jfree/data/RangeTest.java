package org.jfree.data;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.*;

class RangeTest {
	private Range exampleRange;
	private Range exampleRange1;
	private Range exampleRange2;
	private Range expected;
	private double expectedValue;
	private double expectedValue2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	/* COMBINE METHOD TEST CASES */

	// Testing combine method with 2 Ranges that are positive and do not overlap.
	// Result should be a combined range of their lower and upper bound values.
	@Test
	void test_CombineMethod_PositiveNoOverlap() {
		// (3,5) (7, 13) -> (3,13)
		exampleRange1 = new Range(3, 5);
		exampleRange2 = new Range(7, 13);
		expected = new Range(3, 13);
		Range actual = Range.combine(exampleRange1, exampleRange2);

		assertEquals(expected, actual, "Combining (3,5) with (7,13) should result in (3,13)");
	}

	// Testing combine method with 2 Ranges that are positive but overlaps with each
	// other.
	// Result should be a combined range of their lower and upper bound values.
	@Test
	void test_CombineMethod_PositivewithOverlap() {
		// (3,5) (4, 13) -> (3,13)
		exampleRange1 = new Range(3, 5);
		exampleRange2 = new Range(4, 13);
		expected = new Range(3, 13);
		Range actual = Range.combine(exampleRange1, exampleRange2);

		assertEquals(expected, actual, "Combining (3,5) with (4,13) should result in (3,13)");
	}

	// Testing combine method with 2 Ranges that are positive but overlaps with each
	// other.
	// Result should be a combined range of their lower and upper bound values.
	@Test
	void test_CombineMethod_PositivewithOverlap2() {
		// (3,15) (1,7) -> (1,15)
		exampleRange1 = new Range(3, 15);
		exampleRange2 = new Range(1, 7);
		expected = new Range(1, 15);
		Range actual = Range.combine(exampleRange1, exampleRange2);

		assertEquals(expected, actual, "Combining (3,15) with (1,7) should result in (1,15)");
	}

	// Testing combine method with 2 Ranges that are positive but overlaps with each
	// other.
	// Result should be a combined range of their lower and upper bound values.
	@Test
	void test_CombineMethod_PositivewithOverlap3() {
		// (3,15) (4,6) -> (3,15)
		exampleRange1 = new Range(3, 15);
		exampleRange2 = new Range(4, 6);
		expected = new Range(3, 15);
		Range actual = Range.combine(exampleRange1, exampleRange2);

		assertEquals(expected, actual, "Combining (3,15) with (4,6) should result in (3,15)");
	}

	// Testing combine method with 2 Ranges that are positive but overlaps with each
	// other.
	// Result should be a combined range of their lower and upper bound values.
	@Test
	void test_CombineMethod_PositivewithOverlap4() {
		// (7,12) (3,8) -> (3,12)
		exampleRange1 = new Range(7, 12);
		exampleRange2 = new Range(3, 8);
		expected = new Range(3, 12);
		Range actual = Range.combine(exampleRange1, exampleRange2);

		assertEquals(expected, actual, "Combining (7,12) with (3,8) should result in (3,12)");
	}

	// Testing combine method with 2 Similarly Valued Ranges.
	// Result should be the same range.
	@Test
	void test_CombineMethod_SimilarRanges() {
		// (3,5) (3,5) -> (3,5)
		exampleRange1 = new Range(3, 5);
		exampleRange2 = new Range(3, 5);
		expected = new Range(3, 5);
		Range actual = Range.combine(exampleRange1, exampleRange2);

		assertEquals(expected, actual, "Combining (3,5) with (3,5) should result in (3,5)");
	}

	// Possible Test Case -- Invalid
	// Testing combine method with one range being invalid because the upper bound
	// is less than lower bound. (upper < lower)
	// Result should throw an exception due to the invalid creation of first range
	// (5,3) (7, 13) -> Throw Exception -> (invalid)

	// Testing combine method with 2 Ranges that are of null value.
	// Result should be returning a null range.
	@Test
	void test_CombineMethod_TwoNullRanges() {
		// (null) (null) -> (null)
		exampleRange1 = null;
		exampleRange2 = null;
		expected = null;
		Range actual = Range.combine(exampleRange1, exampleRange2);

		assertAll(() -> assertEquals(expected, actual, "Combining (null) with (null) should result in (null)"), () -> assertNull(actual));
	}

	// Testing combine method with 1 Range that is of null value and the other an
	// actual range.
	// Result should be returning the range that exists from the two values passed.
	@Test
	void test_CombineMethod_OneNullRange() {
		// (3,5) (null) -> (3,5) and
		// (null) (3,5) -> (3,5)
		exampleRange1 = new Range(3, 5);
		exampleRange2 = null;
		expected = new Range(3, 5);
		Range actual = Range.combine(exampleRange1, exampleRange2);
		Range actual2 = Range.combine(exampleRange2, exampleRange1);

		assertAll(() -> assertEquals(expected, actual, "Combining (3,5) with (null) should result in (3,5)"), 
				() -> assertEquals(expected, actual2, "Combining (null) with (3,5) should result in (3,5)"));
	}

	/* SHIFT METHOD (2 parameters) TEST CASES */

	// Testing shift method with Range parameter being null.
	// Result should be returning the InvalidParameterException.
	@Test
	void test_ShiftMethod_OneNullValue() {
		// (null) (2) -> InvalidParameterException Throws an exception if null Range
		// base parameter is passed in
		exampleRange1 = null;

		assertThrows(InvalidParameterException.class, () -> Range.shift(exampleRange1, 2));
	}

	// (5,10) (null) -> InvalidParameterException Throws an exception if null object
	// is passed in

	// Testing shift method with 1 Positive Range that is shifted by a positive
	// value.
	// Result should be returning the range with its lower and upper bound shifted
	// by the value passed.
	@Test
	void test_ShiftMethod_PositiveRangeValue() {
		// (2,6) (2) -> (4,8) Positive Range shifted by a positive value
		exampleRange1 = new Range(2, 6);
		expected = new Range(4, 8);
		Range actual = Range.shift(exampleRange1, 2);

		assertEquals(expected, actual, "Range should be shifted to (4,8)");
	}

	// Testing shift method with 1 Positive Range that is shifted by a negative
	// value.
	// Result should be returning the range with its lower and upper bound shifted
	// by the value passed.
	@Test
	void test_ShiftMethod_PositiveRange_NegativeValue() {
		// (5,10) (-5) -> (0, 5) Positive Range shifted by a negative value
		exampleRange1 = new Range(5, 10);
		expected = new Range(0, 5);
		Range actual = Range.shift(exampleRange1, -5);

		assertEquals(expected, actual, "Range should be shifted to (0,5)");
	}

	// Testing shift method with 1 Positive Range that is shifted by a negative
	// value.
	// Result should be returning the range with its lower and upper bound shifted
	// by the value passed.
	// Zero crossing is not allowed in this method so if the upper or lower bound
	// crosses 0, the upper or lower bound values will change to 0.
	@Test
	void test_ShiftMethod_PositiveRange_NegativeValue2() {
		// (0 2) (-3) -> (0, 0) Positive Range shifted by a negative value (Does not
		// allow zero crossing so lower/upper becomes 0)
		exampleRange1 = new Range(0, 2);
		expected = new Range(0, 0);
		Range actual = Range.shift(exampleRange1, -3);

		assertEquals(expected, actual, "Range should be shifted to (0,0)");
	}

	// Testing shift method with 1 Negative Range that is shifted by a negative
	// value.
	// Result should be returning the range with its lower and upper bound shifted
	// by the value passed.
	@Test
	void test_ShiftMethod_NegativeRange_NegativeValue() {
		// (-5, -3) (-6) -> (-11, -9) Negative Range shifted by a negative value
		exampleRange1 = new Range(-5, -3);
		expected = new Range(-11, -9);
		Range actual = Range.shift(exampleRange1, -6);

		assertEquals(expected, actual, "Range should be shifted to (-11,-9)");
	}

	// Testing shift method with 1 Negative Range that is shifted by a positive
	// value.
	// Result should be returning the range with its lower and upper bound shifted
	// by the value passed.
	// Zero crossing is not allowed in this method so if the upper or lower bound
	// crosses 0, the upper or lower bound values will change to 0.
	@Test
	void test_ShiftMethod_NegativeRange_PositiveValue() {
		// (-3, -1) (5) -> (0, 0) Negative Range shifted by a positive value (Does not
		// allow zero crossing so lower/upper becomes 0)
		exampleRange1 = new Range(-3, -1);
		expected = new Range(0, 0);
		Range actual = Range.shift(exampleRange1, 5);

		assertEquals(expected, actual, "Range should be shifted to (0,0)");
	}

	/* CONSTRAIN METHOD TEST CASES */

	// Testing constrain method with 1 Positive Range using constrain on a value
	// existing within the range.
	// Result should be returning the existing passed value
	@Test
	void test_ConstrainMethod_PositiveRange_Middle() {
		// (10,15) (13) -> (13) Positive Range with value within Range returns specified
		// value
		exampleRange1 = new Range(10, 15);
		expectedValue = 13;
		double actual = exampleRange1.constrain(13);

		assertEquals(expectedValue, actual, "constrained result should be 13");
	}

	// Testing constrain method with 1 Positive Range using constrain on a value
	// existing outside the Upper Bound value.
	// Result should be returning the upperBound value
	@Test
	void test_ConstrainMethod_PositiveRange_UpperBound() {
		// (10,15) (18) -> (15) Positive Range with value outside upperBound range
		// returns upperBound value
		exampleRange1 = new Range(10, 15);
		expectedValue = 15;
		double actual = exampleRange1.constrain(18);

		assertEquals(expectedValue, actual, "constrained result should be 15");
	}

	// Testing constrain method with 1 Positive Range using constrain on a value
	// existing outside the Lower Bound value.
	// Result should be returning the lowerBound value
	@Test
	void test_ConstrainMethod_PositiveRange_LowerBound() {
		// (10,15) (7) -> (10) Positive Range with value outside lowerBound range
		// returns lowerBound value
		exampleRange1 = new Range(10, 15);
		expectedValue = 10;
		double actual = exampleRange1.constrain(7);

		assertEquals(expectedValue, actual, "constrained result should be 10");
	}

	// Testing constrain method with 1 Positive Range using constrain on a values
	// being exactly the upper and lower bound values.
	// Result should be returning the lowerBound or upperBound value
	@Test
	void test_ConstrainMethod_PositiveRange_EdgeBounds() {
		// (10, 15) (10) & (15) -> (10) (15) Positive Range testing edge cases of
		// upper/lower bound value to return upper/lower values.
		exampleRange1 = new Range(10, 15);
		expectedValue = 10;
		expectedValue2 = 15;

		double actual = exampleRange1.constrain(10);
		double actual2 = exampleRange1.constrain(15);

		assertAll(() -> assertEquals(expectedValue, actual, "constrained result should be 10"), 
				() -> assertEquals(expectedValue2, actual2, "constrained result should be 15"));
	}

	// Testing constrain method with 1 Negative Range using constrain on a value
	// existing within the range.
	// Result should be returning the existing passed value
	@Test
	void test_ConstrainMethod_NegativeRange_Middle() {
		// (-8,-4) (-5) -> (-5) Negative range with value within Range returns specified
		// value
		exampleRange1 = new Range(-8, -4);
		expectedValue = -5;
		double actual = exampleRange1.constrain(-5);

		assertEquals(expectedValue, actual, "constrained result should be -5");
	}

	// Testing constrain method with 1 Negative Range using constrain on a value
	// existing outside the Upper Bound value.
	// Result should be returning the upperBound value
	@Test
	void test_ConstrainMethod_NegativeRange_UpperBound() {
		// (-8,-4) (-2) -> (-4) Negative range with value outside upperBound range
		// returns upperBound value
		exampleRange1 = new Range(-8, -4);
		expectedValue = -4;
		double actual = exampleRange1.constrain(-2);

		assertEquals(expectedValue, actual, "constrained result should be -4");
	}

	// Testing constrain method with 1 Negative Range using constrain on a value
	// existing outside the Lower Bound value.
	// Result should be returning the lowerBound value
	@Test
	void test_ConstrainMethod_NegativeRange_LowerBound() {
		// (-8,-4) (-20) -> (-8) Negative range with value outside lowerBound range
		// returns lowerBound value
		exampleRange1 = new Range(-8, -4);
		expectedValue = -8;
		double actual = exampleRange1.constrain(-20);

		assertEquals(expectedValue, actual, "constrained result should be -8");
	}

	// Testing constrain method with 1 Negative Range using constrain on a values
	// being exactly the upper and lower bound values.
	// Result should be returning the lowerBound or upperBound value
	@Test
	void test_ConstrainMethod_NegativeRange_EdgeBounds() {
		// (-8, -4) (-8) & (-4) -> (-8) (-4) Negative Range testing edge cases of
		// upper/lower bound value to return upper/lower values.
		exampleRange1 = new Range(-8, -4);
		expectedValue = -8;
		expectedValue2 = -4;

		double actual = exampleRange1.constrain(-8);
		double actual2 = exampleRange1.constrain(-4);

		assertAll(() -> assertEquals(expectedValue, actual, "constrained result should be -8"), 
				() -> assertEquals(expectedValue2, actual2, "constrained result should be -4"));
	}

	/* CONTAINS METHOD TEST CASES */

	// Testing contains method with verifying that using a Positive Range with the
	// LowerBound value to check its existence in the range.
	// Result should be True.
	@Test
	void test_ContainsMethod_PositiveRange_LowerBound() {
		// (2,10) (2) -> Returns True: LowerBound value is checked
		exampleRange1 = new Range(2, 10);
		assertTrue(exampleRange1.contains(2));
	}

	// Testing contains method with verifying that using a Positive Range with the
	// UpperBound value to check its existence in the range.
	// Result should be True.
	@Test
	void test_ContainsMethod_PositiveRange_UpperBound() {
		// (2,10) (10) -> Returns True: UpperBound value is checked
		exampleRange1 = new Range(2, 10);
		assertTrue(exampleRange1.contains(10));
	}

	// Testing contains method with verifying that using a Positive Range with any
	// value within the Range to check its existence.
	// Result should be True.
	@Test
	void test_ContainsMethod_PositiveRange_Middle() {
		// (2,10) (5) -> Returns True: A value existing in between lower and upper bound
		// values in range.
		exampleRange1 = new Range(2, 10);
		assertTrue(exampleRange1.contains(5));
	}

	// Testing contains method with verifying that checking if an invalid value
	// below lower bound exists in a Positive Range.
	// Result should be False.
	@Test
	void test_ContainsMethod_InvalidValue_PositiveRange_LowerBound() {
		// (5, 8) (3) -> Returns False: checking number below lower bound value of range
		exampleRange1 = new Range(5, 8);
		assertFalse(exampleRange1.contains(3));
	}

	// Testing contains method with verifying that checking if an invalid value
	// above upper bound exists in a Positive Range.
	// Result should be False.
	@Test
	void test_ContainsMethod_InvalidValue_PositiveRange_UpperBound() {
		// (5, 8) (11) -> Returns False: checking number above upper bound value of
		// range
		exampleRange1 = new Range(5, 8);
		assertFalse(exampleRange1.contains(11));
	}

	// Testing contains method with verifying that using a Negative Range with the
	// LowerBound value to check its existence in the range.
	// Result should be True.
	@Test
	void test_ContainsMethod_NegativeRange_LowerBound() {
		// (-6,-2) (-6) -> Returns True: Negative Range Negative Lower Bound is checked
		exampleRange1 = new Range(-6, -2);
		assertTrue(exampleRange1.contains(-6));
	}

	// Testing contains method with verifying that using a Negative Range with the
	// UpperBound value to check its existence in the range.
	// Result should be True.
	@Test
	void test_ContainsMethod_NegativeRange_UpperBound() {
		// (-6, -2) (-2) -> Returns True: Negative Range Negative Upper Bound is checked
		exampleRange1 = new Range(-6, -2);
		assertTrue(exampleRange1.contains(-2));
	}

	// Testing contains method with verifying that using a Negative Range with any
	// value within the Range to check its existence.
	// Result should be True.
	@Test
	void test_ContainsMethod_NegativeRange_Middle() {
		// (-6, -2) (-4) -> Returns True: Negative Range Middle
		exampleRange1 = new Range(-6, -2);
		assertTrue(exampleRange1.contains(-4));
	}

	// Testing contains method with verifying that checking if an invalid value
	// below lower bound exists in a Negative Range.
	// Result should be False.
	@Test
	void test_ContainsMethod_InvalidValue_NegativeRange_LowerBound() {
		// (-12, -8) (-15) -> Returns False: Negative Range Below Lower Bound
		exampleRange1 = new Range(-12, -8);
		assertFalse(exampleRange1.contains(-15));
	}

	// Testing contains method with verifying that checking if an invalid value
	// above upper bound exists in a Negative Range.
	// Result should be False.
	@Test
	void test_ContainsMethod_InvalidValue_NegativeRange_UpperBound() {
		// (-12, -8) (-6) -> Returns False: Negative Range Above Upper Bound
		exampleRange1 = new Range(-12, -8);
		assertFalse(exampleRange1.contains(-6));
	}

	/* EQUALS METHOD TEST CASES */

	// Testing equals method with similar Positive Ranges.
	// Result should be True.
	@Test
	void test_EqualsMethod_PositiveRange_SimilarValues() {
		// (3,5) (3,5) -> Returns True: Positive Range Similar Values
		exampleRange1 = new Range(3, 5);
		exampleRange2 = new Range(3, 5);
		assertTrue(exampleRange1.equals(exampleRange2));
	}

	// Testing equals method with different Positive Ranges.
	// Result should be False.
	@Test
	void test_EqualsMethod_PositiveRange_DifferentValues() {
		// (3,5) (7,10) -> Returns False: Positive Ranges Different Values
		exampleRange1 = new Range(3, 5);
		exampleRange2 = new Range(7, 10);
		assertFalse(exampleRange1.equals(exampleRange2));
	}

	// Testing equals method with similar Null Ranges. Null is permitted to function
	// in this method. So the equals method should execute.
	// Result should be True.
	@Test
	void test_EqualsMethod_NullRange() {
		// (null) (null) -> Returns True: Similar Object values
		exampleRange1 = null;
		exampleRange2 = null;
		assertTrue(exampleRange1.equals(exampleRange2));
	}

	// Testing equals method with similar Null Ranges. Null is permitted to function
	// in this method. So the equals method should execute.
	// Result should be False.
	@Test
	void test_EqualsMethod_OneNullRange_OneActualRange() {
		// (12,15) (null) -> Returns False: One null different values
		// (null) (12,15) -> Returns False: One null different values
		exampleRange1 = new Range(12, 15);
		exampleRange2 = null;

		assertAll(() -> assertFalse(exampleRange1.equals(exampleRange2)),
				() -> assertFalse(exampleRange2.equals(exampleRange1)));
	}

	// Testing equals method with similar Negative Ranges.
	// Result should be True.
	@Test
	void test_EqualsMethod_NegativeRange_SimilarValues() {
		// (-5,-1) (-5,-1) -> Returns True: Negative Ranges Similar Values
		exampleRange1 = new Range(-5, -1);
		exampleRange2 = new Range(-5, -1);
		assertTrue(exampleRange1.equals(exampleRange2));
	}

	// Testing equals method with different Negative Ranges.
	// Result should be False.
	@Test
	void test_EqualsMethod_NegativeRange_DifferentValues() {
		// (-5,-1) (-20, -17) -> Returns False: Negative Ranges Different Values
		exampleRange1 = new Range(-5, -1);
		exampleRange2 = new Range(-20, -17);
		assertFalse(exampleRange1.equals(exampleRange2));
	}

	// Testing equals method with 1 different Positive Range and 1 different
	// Negative Range.
	// Result should be False.
	@Test
	void test_EqualsMethod_PositiveandNegativeRange_DifferentValues() {
		// (6,8) (-9, -7) -> Returns False: One Positive and One Negative different
		// Values
		exampleRange1 = new Range(6, 8);
		exampleRange2 = new Range(-9, -7);
		assertFalse(exampleRange1.equals(exampleRange2));
	}

	/* EXPAND TEST */
	// testing expand to create a faulty range of 3-2 should throw error
	@Test
	void expand_ErrorRangeTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			exampleRange = Range.expand(new Range(2, 6), -0.25, -1);
		}, "The lowerrange should be 3, the upperrange 2");
	}

	// testing if null value is not permitted like it says in the api
	@Test
	void expand_NullRangeTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			exampleRange = Range.expand(null, -0.25, -1);
		}, "The lowerrange should be 3, the upperrange 2");
	}

	@ParameterizedTest
	@CsvSource({ "2, 6, 0.25, 0.5, 1, 8, 'Lower should be 1 Upper should be 8'", // Standard situation provided api
			"2, 6, -0.25, 0.5, 3, 8, 'Lower should be 3 Upper should be 8'", // Testing negative lower margin
			"2, 6, 0.25, -0.5, 1, 4, 'Lower should be 1 Upper should be 4'", // Testing negative upper margin
			"-2, 6, 0.25, 0.5, -6, 10, 'Lower should be -4 Upper should be 10'", // Testing negative lower range bound
			"-6, -2, 0.5, 0.25, -8, -1, 'Lower should be -8 Upper should be -1'", // Testing negative range
			"1,1,1,1,1,1, 'Lower and Upper should be 1'", // Testing when the range length is zero
			"0,1,0.5,0.5,-0.5,1.5, 'Unspecified what should happen in the documentation, should be whole value? or decimal?'" // Testing
																																// if
																																// the
																																// range
																																// returns
																																// a
																																// decimal
																																// number
	})
	void expand_example_test(double origLower, double origHigher, double lowerMod, double higherMod, double newLower,
			double newHigher, String expected) {
		exampleRange = Range.expand(new Range(origLower, origHigher), lowerMod, higherMod);
		assertAll(() -> assertEquals(newLower, exampleRange.getLowerBound(), expected),
				() -> assertEquals(newHigher, exampleRange.getUpperBound(), expected));
	}

	/* EXPAND TO INCLUDE TEST */
	@ParameterizedTest
	@CsvSource({ "1,1,2,1,2, 'The top range should be 2'", // Expand a range of 1-1 to 1-2
			"1,1,0,0,1, 'The lower range should be 1'", // Expand a range of 1-1 to 0-1
			"1,10,5,1,10, 'The lower range should be 1, the upper should be 10 '", // Expand a range that doesn't need
																					// expanding
			"1,1,10,1,10, 'The lower range should be 1, the upper should be 10 '", // Expand a range by a larger value
			"-1,0,10,-1,10, 'The lower range should be -1, the upper should be 10 '", // Expand a range containing a
																						// negative value
			"1,10,-5,-5,10, 'The lower range should be -5, the upper should be 10 '", // Expand a range by a negative
																						// value
			"-10,-1,10,-10,10, 'The lower range should be -10, the upper should be 10 '", // Expand a negative range by
																							// a positive value
			"1,10,0.5,1,10, 'The lower range should be 0.5, the upper should be 10 '", // Expand a range by a decimal
																						// number
			"50,100,-100,-100,100, 'The lower range should be -100, the upper should be 100 '", // Expand by a larger
																								// sum
			"1000,1000000,5,5,1000000, 'The lower range should be 5, the upper should be 1000000 '", // Expand a large
																										// range to a
																										// smaller
																										// number
	})
	void expandToInclude_example_test(double origLower, double origHigher, double newValue, double newLower,
			double newHigher, String expected) {
		exampleRange = Range.expandToInclude(new Range(origLower, origHigher), newValue);
		assertAll(() -> assertEquals(true, exampleRange.contains(newValue), expected),
				() -> assertEquals(newLower, exampleRange.getLowerBound(), expected),
				() -> assertEquals(newHigher, exampleRange.getUpperBound(), expected));
	}

	@ParameterizedTest
	@CsvSource({ "0", "1", "10", "0.5", "-1", "-10", "-0.5" })
	void expandToInclude_null_test(double value) {
		exampleRange = Range.expandToInclude(null, value);
		assertEquals(true, exampleRange.contains(value));
	}

	/* CONTAINS TEST */
	@ParameterizedTest
	@CsvSource({ "0,1,1", "0,1,0", "-1,0,0", "-1,0,-1", "5,10,7", "-2,-1,-1", "-2,-1,-2", "-10,-5,-7", "0,0,0", "1,1,1",
			"-1,-1,-1" })
	void contains_true_test(double lower, double upper, double contains) {
		exampleRange = new Range(lower, upper);
		assertEquals(true, exampleRange.contains(contains));
	}

	@ParameterizedTest
	@CsvSource({ "0,1,-1", "0,1,10", "-1,0,1", "-1,0,1", "5,10,3", "-2,-1,0", "-2,-1,-3", "-10,-5,-3", "0,0,1", "1,1,2",
			"-1,-1,0" })
	void contains_false_test(double lower, double upper, double contains) {
		exampleRange = new Range(lower, upper);
		assertEquals(false, exampleRange.contains(contains));
	}

	/* GET CENTRAL VALUE TEST */
	@ParameterizedTest
	@CsvSource({ "0,10,5", // positives
			"-10,0,-5", // negatives
			"0,0,0", // range of 0
			"0,1,0.5", // non whole numbers
			"-1,0,-0.5", // negative non whole numbers
			"-10,10,0", // negative and positive
			"0,0.5,0.25", // testing decimals
			"-0.2,-0.1,-0.15", // testing negative decimals
			"0.1,0.2,0.15", // testing positive decimals
			"0.11,0.12,0.115" // testing hundreths
	})
	void getCentralValue_example_test(double lower, double upper, double central) {
		exampleRange = new Range(lower, upper);
		assertEquals(central, exampleRange.getCentralValue());
	}

	/* GET LENGTH TEST */
	@ParameterizedTest
	@CsvSource({ "0,10,10", // positives
			"-10,0,10", // negatives
			"0,0,0", // range of 0
			"0,1,1", // length of 1
			"-1,0,1", // negative length of 1
			"-10,10,20", // negative and positive
			"0,0.5,0.5", // testing decimals
			"-0.2,-0.1,0.1", // testing negative decimals
			"0.1,0.2,0.1", // testing positive decimals
			"0.11,0.12,0.01" // testing hundreths
	})
	void getLength_example_test(double lower, double upper, double length) {
		exampleRange = new Range(lower, upper);
		assertEquals(length, exampleRange.getLength());
	}

	@Test
	void getLength_voidRange_test() {
		exampleRange = null;
		assertThrows(NullPointerException.class, () -> {
			exampleRange.getLength();
		}, "Null range should throw error");
	}

	/* TO STRING TEST */
	@ParameterizedTest
	@CsvSource({ "0,1", // range of 1
			"0,0", // range of 0
			"-1,0", // negative range of 1
			"1,10", // Range of 9
			"-10,-1", // Negative range of 9
			"0,0.5", // positive decimals
			"0.5,0.75", // hundreths
			"-0.5,0", // negative decimals
			"-0.75,0.5", // negative hundreths
			"-100,100", // larger ranges
			"5000,1000000", // massive ranges
			"-1000000,-5000", // massive ranges
			"-1000000,1000000" // massive ranges
	})
	void toString_example_test(double lower, double upper) {
		exampleRange = new Range(lower, upper);
		assertEquals("Range[" + lower + "," + upper + "]", exampleRange.toString());
	}

	@Test
	void toString_voidRange_test() {
		exampleRange = null;
		assertThrows(NullPointerException.class, () -> {
			exampleRange.toString();
		}, "Null range should throw error");
	}

	@Test
	void testShiftException() {
		exampleRange = null;
		assertThrows(InvalidParameterException.class, () -> Range.shift(exampleRange, 1, true));
	}

	@ParameterizedTest
	@CsvSource({ "-3, -3, -1, Lower bound should be -3", // Testing a range with negative values
			"0, 0, 5, Lower bound should be 0", // Testing a range with 0
			"3, 3, 6, Lower bound should be 3", // Testing a range with positive values
			"0, 0, 0, Lower bound should be 0" // Testing range (0, 0)
	})
	void testLowerBound(double expected, double a, double b, String s) {
		exampleRange = new Range(a, b);
		assertEquals(expected, exampleRange.getLowerBound(), s);
	}

	@ParameterizedTest
	@CsvSource({ "-1, -3, -1, Upper bound should be -1", // Testing a range with negative values
			"5, 0, 5, Upper bound should be 5", // Testing a range with 0
			"6, 3, 6, Upper bound should be 6", // Testing a range with positive values
			"0, 0, 0, Upper bound should be 0" // Testing range (0, 0)
	})
	void testUpperBound(double expected, double a, double b, String s) {
		exampleRange = new Range(a, b);
		assertEquals(expected, exampleRange.getUpperBound(), s);
	}

	@ParameterizedTest
	@CsvSource({ "true, -3, 11, -10, -3, Ranges intersect at -3", // Testing when ranges intersect at 1 point
			"false, 11, 15, 0, 10, Ranges don't intersect", // Testing when ranges don't intersect
			"true, 6, 9, -12, 6, Ranges intersect at 6", // Testing when ranges intersect via many points
			"true, 6, 9, -12, 20, Range 1 is inside of range 2", // Testing when 1 range belongs to another range
			"true, 0, 0, 0, 0, Both ranges are the same" // Testing when ranges are the same
	})
	void testIntersects(boolean expected, double a, double b, double c, double d, String s) {
		exampleRange = new Range(c, d);
		assertEquals(expected, exampleRange.intersects(a, b), s);
	}

	@ParameterizedTest
	@CsvSource({ "-5, -1, -1, 0, 4, false, Should return (-1, 0)", // Testing when shift crosses 0 with ZeroCrossing not
																	// allowed
			"-5, -1, -1, 3, 4, true, Should return (-1, 3)", // Testing when shift crosses 0 with ZeroCrossing allowed
			"0, 2, 2, 4, 2, false, Should return (2, 4)", // Testing when lower bound equals to 0 with ZeroCrossing not
															// allowed
			"-5, 0, -3, 2, 2, false, Should return (-3, 2)", // Testing when upper bound equals to 0 with ZeroCrossing
																// not allowed
			"0, 0, 2, 2, 2, false, Should return (2, 2)" // Testing when the range is (0, 0)
	})
	void testShift(double a, double b, double c, double d, double e, boolean isAllowed, String s) {
		exampleRange = new Range(a, b);
		Range expectedRange = new Range(c, d);
		assertEquals(expectedRange, Range.shift(exampleRange, e, isAllowed), s);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

}
