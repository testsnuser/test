/**
 * Subtraction Test
 */
package com.tarun.test;

import org.junit.Assert;
import org.junit.Test;

import com.tarun.Subtraction;

/**
 * @author tarun.murala
 *
 */
public class SubtractionTest {

	public SubtractionTest() {
		// default constructor
	}
	
	@Test
	public void testSubtract() {
		System.out.println("Into SubstractionTest.testSubtract");
		Assert.assertEquals(2, (new Subtraction()).subtract(4, 2));
	}

}
