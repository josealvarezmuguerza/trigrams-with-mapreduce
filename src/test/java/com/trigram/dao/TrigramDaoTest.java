package com.trigram.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.trigram.dao.TrigramDao;

/**
 * This UT takes real data from file located under resources/MR/
 * @author jose
 *
 */
public class TrigramDaoTest {

	private TrigramDao trigramDao;

	// keys
	private String key1;
	private String key2;
	private String key3;
	private String key4;

	@Before
	public void setup()  {
		trigramDao = new TrigramDao(getClass().getResource("/").getPath());

		// keys
		key1 = "i wish";
		key2 = "wish i";
		key3 = "may i";
		key4 = "i may";

	}

	@After
	public void tearDown() {
		trigramDao = null;
		key1 = null;
		key2 = null;
	}

	@Test
	public void testConstructor() {
		trigramDao = new TrigramDao("/home");
		assertFalse(trigramDao.hasData());
		
		trigramDao = new TrigramDao(getClass().getResource("/").getPath());
		assertTrue(trigramDao.hasData());
	}
	
	@Test
	public void testHasData() {
		assertTrue(trigramDao.hasData());
	}

	@Test
	public void testGetTrigramByFirstKey() throws IllegalArgumentException,
			IllegalAccessException {

		assertEquals("i", trigramDao.getTrigramByKey(key1));
		assertEquals("i", trigramDao.getTrigramByKey(key1));
		assertEquals("i", trigramDao.getTrigramByKey(key1));
	}

	@Test
	public void testGetTrigramBySecondKey() {
		assertEquals("may", trigramDao.getTrigramByKey(key2));
		assertEquals("might", trigramDao.getTrigramByKey(key2));
		assertEquals("may", trigramDao.getTrigramByKey(key2));
	}

	@Test
	public void testGetKeyRandomly() {
		assertNotNull(trigramDao.getKeyRandomly());
		assertTrue(trigramDao.getKeyRandomly().matches(
				"i wish|wish i|i may|may i"));
	}

}
