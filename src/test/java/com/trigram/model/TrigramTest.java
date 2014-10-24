package com.trigram.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import com.trigram.model.Trigram;

/**
 * 
 * @author jose
 *
 */
public class TrigramTest {
	Trigram trigram1;
	Trigram trigram2;

	// keys
	private String key1;
	private String key2;

	@Before
	public void setup() throws FileNotFoundException,
			UnsupportedEncodingException, IllegalArgumentException,
			IllegalAccessException {
		// keys
		key1 = "i wish";
		key2 = "wish i";

		trigram1 = new Trigram(key1, "i i");
		trigram2 = new Trigram(key2, "may might");
	}

	@Test
	public void testOffset() {
		assertEquals(0, trigram1.getOffset());
		trigram1.incrementOffset();
		assertEquals(1, trigram1.getOffset());
		// test cycle
		trigram1.incrementOffset();
		assertEquals(0, trigram1.getOffset());
	}

	@Test
	public void testGetValue() {
		assertEquals("i", trigram1.getValue());
		assertEquals("i", trigram1.getValue());
		trigram1.incrementOffset();
		assertEquals("i", trigram1.getValue());
		trigram1.incrementOffset();
		assertEquals("i", trigram1.getValue());

		assertFalse("any Key".equalsIgnoreCase(trigram1.getValue()));

		trigram1.setValues(new String[] { "i", "i", "another" });
		assertEquals("i", trigram1.getValue());
		trigram1.incrementOffset();
		assertEquals("i", trigram1.getValue());
		trigram1.incrementOffset();
		assertEquals("another", trigram1.getValue());
		trigram1.incrementOffset();
		assertEquals("i", trigram1.getValue());
	}

	@Test
	public void testGetKey() {
		assertEquals("i wish", trigram1.getKey());
		assertEquals("wish i", trigram2.getKey());

		trigram1.setKey("any key");
		assertEquals("any key", trigram1.getKey());
	}

}
