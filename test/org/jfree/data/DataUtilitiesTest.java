package org.jfree.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.mockito.Mockito.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;

class DataUtilitiesTest {
	private double[] oldArray;
	private double[][] old2DArray;
	private java.lang.Number[] newArray;
	private java.lang.Number[][] new2DArray;
	private KeyedValues kvalues;
	private Values2D value; // instance of Values2D

	@BeforeAll
	static void setupBeforeClass() throws Exception {

	}

	@BeforeEach
	void setUp() throws Exception {
		value = mock(Values2D.class); // all test cases will use this mocked value variable with changed values.
		when(value.getColumnCount()).thenReturn(4); // 3x4 whatever you want
		when(value.getRowCount()).thenReturn(3);
		kvalues = mock(KeyedValues.class);

		when(kvalues.getItemCount()).thenReturn(3);

		when(kvalues.getValue(0)).thenReturn(-4);
		when(kvalues.getValue(1)).thenReturn(6);
		when(kvalues.getValue(2)).thenReturn(12);

		when(kvalues.getKey(0)).thenReturn(0);
		when(kvalues.getKey(1)).thenReturn(1);
		when(kvalues.getKey(2)).thenReturn(2);

		when(kvalues.getIndex(0)).thenReturn(0);
		when(kvalues.getIndex(1)).thenReturn(1);
		when(kvalues.getIndex(2)).thenReturn(2);

		ArrayList keyList = new ArrayList<>();
		keyList.add(0);
		keyList.add(1);
		keyList.add(2);

		when(kvalues.getKeys()).thenReturn(keyList);
	}

	/* CALCULATECOLUMNTOTAL METHOD TEST CASES */

	// POSITIVE COLUMN VALUES
	// Testing calculateColumnTotal method with column values having positive
	// values.
	// Result should be returning a total of the column's values that should all
	// return a positive total.
	@ParameterizedTest
	@CsvSource({ "2,3,4,3,9", "10,15,20,2,45", "123,523,200,1,846" })
	void test_calculateColumnTotalMethod_AllPositive(double val1, double val2, double val3, int column,
			double expected) {
		// (2,3,4) in column 3 => (9) All positive in column of array v1
		// (10,15,20) in column 2 => (45) All positive in column of array v2
		// (123, 523, 200) in column 1 => (846) All positive in column of array v3
		when(value.getValue(0, column)).thenReturn(val1);
		when(value.getValue(1, column)).thenReturn(val2);
		when(value.getValue(2, column)).thenReturn(val3);
		double actual = DataUtilities.calculateColumnTotal(value, column);

		assertEquals(expected, actual);

		// assertEquals(13, DataUtilities.calculateColumnTotal(value, 2), .01d);
		verify(value, times(3)).getValue(anyInt(), anyInt()); // verify that the getValue method is called exactly 3
																// times
		// use verify only if that count makes sense.
	}

	// NEGATIVE COLUMN VALUES
	// Testing calculateColumnTotal method with column values having negative
	// values.
	// Result should be returning a total of the column's values that should all
	// return a negative total.
	@ParameterizedTest
	@CsvSource({ "-5,-3,-7,3,-15", "-12,-16,-10,2,-38", "-100,-222,-320,1,-642" })
	void test_calculateColumnTotalMethod_AllNegative(double val1, double val2, double val3, int column,
			double expected) {
		// (-5,-3,-7) in column 3 => (-15) All negative in column of array v1
		// (-12,-16,-10) in column 2 => (-38) All negative in column of array v2
		// (-100, -222, -320) in column 1 => (-642) All negative in column of array v3
		when(value.getValue(0, column)).thenReturn(val1);
		when(value.getValue(1, column)).thenReturn(val2);
		when(value.getValue(2, column)).thenReturn(val3);
		double actual = DataUtilities.calculateColumnTotal(value, column);

		assertEquals(expected, actual);

		verify(value, times(3)).getValue(anyInt(), anyInt());
	}

	// MIXED COLUMN VALUES
	// Testing calculateColumnTotal method with column values having mixed values of
	// either a negative or positive value.
	// Result should be returning a total of the column's values.
	@ParameterizedTest
	@CsvSource({ "-5,5,-2,3,-2", "70,-30,50,2,90" })
	void test_calculateColumnTotalMethod_MixedValues(double val1, double val2, double val3, int column,
			double expected) {
		// (-5,5,-2) in column 3 => (-2) 2 negative, 1 positive
		// (70,-30,50) in column 2 => (90) 2 positive, 1 negative
		when(value.getValue(0, column)).thenReturn(val1);
		when(value.getValue(1, column)).thenReturn(val2);
		when(value.getValue(2, column)).thenReturn(val3);
		double actual = DataUtilities.calculateColumnTotal(value, column);

		assertEquals(expected, actual);
		verify(value, times(3)).getValue(anyInt(), anyInt());
	}

	// Testing calculateColumnTotal method with column values all to equal null.
	// Result should be returning a total of Zero.
	@Test
	void test_calculateColumnTotalMethod_InvalidInput() {
		// (null,null,null) in column 1 => 0 if invalid inputs are entered in Values2D
		// object, a total of zero should be returned
		when(value.getValue(0, 1)).thenReturn(null);
		when(value.getValue(1, 1)).thenReturn(null);
		when(value.getValue(2, 1)).thenReturn(null);
		double actual = DataUtilities.calculateColumnTotal(value, 1);
		double expected = 0.0;

		assertEquals(expected, actual);
		verify(value, times(3)).getValue(anyInt(), anyInt());
	}

	// Testing calculateColumnTotal method with Values2D object being null making it
	// an invalid object.
	// Result should be throwing an InvalidParameterException exception.
	// Cannot test with different data objects like a string 2D array or a double 2D
	// array because the method cannot process it at all
	// and provides a red underline on code warning that the test program will not
	// run.
	// It throws a NullPointerException instead of the InvalidParameterException
	// when tested.
	@Test
	void test_calculateColumnTotalMethod_ExceptionThrows() {
		// Pass a null Values2D object => Throws InvalidParameterException
		value = null;
		// double testArray[][] = {{5,10,15},{20,22,23},{85,100,64}};

		assertAll(
				() -> assertThrows(InvalidParameterException.class, () -> DataUtilities.calculateColumnTotal(value, 1)),
				() -> assertThrows(NullPointerException.class, () -> DataUtilities.calculateColumnTotal(value, 1)));
	}

	/* CALCULATEROWTOTAL METHOD TEST CASES */

	// POSITIVE ROW VALUES
	// Testing calculateRowTotal method with row values having positive values.
	// Result should be returning a total of the row's values that should all return
	// a positive total.
	@ParameterizedTest
	@CsvSource({ "2,3,4,2,9", "10,15,20,1,45", "123,523,200,0,846" })
	void test_calculateRowTotalMethod_AllPositive(double val1, double val2, double val3, int row, double expected) {
		// (2,3,4) in row 3 => (9) All positive in row of array v1
		// (10,15,20) in row 2 => (45) All positive in row of array v2
		// (123, 523, 200) in row 1 => (846) All positive in row of array v3
		when(value.getValue(row, 0)).thenReturn(val1);
		when(value.getValue(row, 1)).thenReturn(val2);
		when(value.getValue(row, 2)).thenReturn(val3);
		double actual = DataUtilities.calculateRowTotal(value, row);

		assertEquals(expected, actual);

		verify(value, times(3)).getValue(anyInt(), anyInt()); // verify that the getValue method is called exactly 3
																// times
	}

	// NEGATIVE ROW VALUES
	// Testing calculateRowTotal method with row values having negative values.
	// Result should be returning a total of the row's values that should all return
	// a negative total.
	@ParameterizedTest
	@CsvSource({ "-5,-3,-7,2,-15", "-12,-16,-10,1,-38", "-100,-222,-320,0,-642" })
	void test_calculateRowTotalMethod_AllNegative(double val1, double val2, double val3, int row, double expected) {
		// (-5,-3,-7) in column 3 => (-15) All negative in row of array v1
		// (-12,-16,-10) in column 2 => (-38) All negative in row of array v2
		// (-100, -222, -320) in column 1 => (-642) All negative in row of array v3
		when(value.getValue(row, 0)).thenReturn(val1);
		when(value.getValue(row, 1)).thenReturn(val2);
		when(value.getValue(row, 2)).thenReturn(val3);
		double actual = DataUtilities.calculateRowTotal(value, row);

		assertEquals(expected, actual);

		verify(value, times(3)).getValue(anyInt(), anyInt());
	}

	// MIXED ROW VALUES
	// Testing calculateRowTotal method with row values having mixed values of
	// either a negative or positive value.
	// Result should be returning a total of the row's values.
	@ParameterizedTest
	@CsvSource({ "-5,5,-2,2,-2", "70,-30,50,1,90" })
	void test_calculateRowTotalMethod_MixedValues(double val1, double val2, double val3, int row, double expected) {
		// (-5,5,-2) in column 3 => (-2) 2 negative, 1 positive
		// (70,-30,50) in column 2 => (90) 2 positive, 1 negative
		when(value.getValue(row, 0)).thenReturn(val1);
		when(value.getValue(row, 1)).thenReturn(val2);
		when(value.getValue(row, 2)).thenReturn(val3);
		double actual = DataUtilities.calculateRowTotal(value, row);

		assertEquals(expected, actual);
		verify(value, times(3)).getValue(anyInt(), anyInt());
	}

	// Testing calculateRowTotal method with row values all to equal null.
	// Result should be returning a total of Zero.
	@Test
	void test_calculateRowTotalMethod_InvalidInput() {
		// (null,null,null) in row 1 => 0 if invalid inputs are entered in Values2D
		// object, a total of zero should be returned
		when(value.getValue(1, 0)).thenReturn(null);
		when(value.getValue(1, 1)).thenReturn(null);
		when(value.getValue(1, 2)).thenReturn(null);
		double actual = DataUtilities.calculateRowTotal(value, 1);
		double expected = 0.0;

		assertEquals(expected, actual);
		verify(value, times(3)).getValue(anyInt(), anyInt());
	}

	// Testing calculateRowTotal method with Values2D object being null making it an
	// invalid object.
	// Result should be throwing an InvalidParameterException exception.
	// Cannot test with different data objects like a string 2D array or a double 2D
	// array because the method cannot process it at all
	// and provides a red underline on code warning that the test program will not
	// run.
	// It throws a NullPointerException instead of the InvalidParameterException
	// when tested.
	@Test
	void test_calculateRowTotalMethod_ExceptionThrows() {
		// Pass a null Values2D object => Throws InvalidParameterException
		value = null;

		assertAll(() -> assertThrows(InvalidParameterException.class, () -> DataUtilities.calculateRowTotal(value, 1)),
				() -> assertThrows(NullPointerException.class, () -> DataUtilities.calculateRowTotal(value, 1)));

	}

	@ParameterizedTest
	@CsvSource({ "-0.33, 0", // Testing negative number
			"0.5, 1", // Testing regular number
			"1.0, 2" // Testing regular number
	})
	void getCumulativePercentageTest(double expected, int a) {
		KeyedValues actual = DataUtilities.getCumulativePercentages(kvalues);

		assertEquals(expected, actual.getValue(a).doubleValue(), 0.01d);
	}

	@Test
	void testCumulativePercentageExceptionNull() {
		kvalues = null;
		assertThrows(InvalidParameterException.class, () -> DataUtilities.getCumulativePercentages(kvalues));
	}

	/* CREATE NUMBER ARRAY */
	@Test
	void createNumberArray_Length() {
		oldArray = new double[] { 1, 3, 4, 5, 6, 1, 0, 3, -4, -2, -10, 0.5, -0.5, 1.25, -1.25 };
		newArray = DataUtilities.createNumberArray(oldArray);

		assertEquals(newArray.length, oldArray.length);
	}

	@Test
	void createNumberArray_single_test() {
		oldArray = new double[] { 1 };
		newArray = DataUtilities.createNumberArray(oldArray);

		for (int i = 0; i < newArray.length; i++) {
			assertTrue(newArray[i] != null);
			assertEquals(oldArray[i], newArray[i].doubleValue());
		}
		;
	}

	@Test
	void createNumberArray_null_test() {
		oldArray = null;
		assertThrows(IllegalArgumentException.class, () -> {
			newArray = DataUtilities.createNumberArray(oldArray);
		});
		;
	}

	@Test
	void createNumberArray_test() {
		oldArray = new double[] { 1, 3, 4, 5, 6, 1, 0, 3, -4, -2, -10, 0.5, -0.5, 1.25, -1.25 };
		newArray = DataUtilities.createNumberArray(oldArray);

		for (int i = 0; i < newArray.length; i++) {
			assertTrue(newArray[i] != null);
			assertEquals(oldArray[i], newArray[i].doubleValue());
		}
		;
	}

	/* CREATE NUMBER ARRAY 2D */
	@Test
	void createNumberArray2D_null_test() {
		old2DArray = null;
		assertThrows(IllegalArgumentException.class, () -> {
			new2DArray = DataUtilities.createNumberArray2D(old2DArray);
		});
	}

	@Test
	void createNumberArray2D_single_test() {
		old2DArray = new double[][] { new double[] { 1 } };
		new2DArray = DataUtilities.createNumberArray2D(old2DArray);

		for (int i = 0; i < new2DArray.length; i++) {
			for (int e = 0; e < new2DArray[i].length; e++) {
				assertTrue(new2DArray[i][e] != null);
				assertEquals(new2DArray[i][e].doubleValue(), old2DArray[i][e]);
			}
			;
		}
	}

	@Test
	void createNumberArray2D_test() {
		old2DArray = new double[][] { new double[] { 1, 3, 4, 5, 6, 1, 0, 3, -4, -2, -10, 0.5, -0.5, 1.25, -1.25 },
				new double[] { 0.5, -0.5, 1.25, -1.25, 10, 1000000 }, new double[] { 0 }, };
		new2DArray = DataUtilities.createNumberArray2D(old2DArray);

		for (int i = 0; i < new2DArray.length; i++) {
			for (int e = 0; e < new2DArray[i].length; e++) {
				assertTrue(new2DArray[i][e] != null);
				assertEquals(new2DArray[i][e].doubleValue(), old2DArray[i][e]);
			}
			;
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {

	}

	@AfterEach
	void tearDown() throws Exception {

	}

}
