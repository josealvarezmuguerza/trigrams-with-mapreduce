package com.trigram.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.trigram.dao.TrigramDao;
import com.trigram.model.Trigram;

/**
 * 
 * @author jose
 *
 */
public class Kata14Test {

	TrigramDao daoMock; 
	Trigram trigram ;
	Kata14 kata14;
	Method generateNewBookMethod;
	Method writeFileMethod;
	Field daoField;
	
	@Before
	public void setup() {
		daoMock = EasyMock.createMock(TrigramDao.class);
		kata14 = new Kata14();	
	}
	
	@Test
	public void testGenerateNewBook() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException{
		EasyMock.reset(daoMock);
		EasyMock.expect(daoMock.hasData()).andReturn(true);
        EasyMock.expect(daoMock.getTrigramByKey("I may")).andReturn("I");
        EasyMock.expect(daoMock.getTrigramByKey("may I")).andReturn("do");
        EasyMock.expect(daoMock.getTrigramByKey("I do")).andReturn("test");
        EasyMock.expect(daoMock.getTrigramByKey("do test")).andReturn(null);
        EasyMock.expect(daoMock.getKeyRandomly()).andReturn("I may");
        EasyMock.replay(daoMock);

      //retrieve private method via reflection
		generateNewBookMethod = kata14.getClass().getDeclaredMethod("generateNewBook",new Class[] {});
		generateNewBookMethod.setAccessible(true);
		daoField = kata14.getClass().getDeclaredField("dao");
		daoField.setAccessible(true);
		daoField.set(kata14, daoMock);
		
	    StringBuilder stringB = (StringBuilder) generateNewBookMethod.invoke(kata14, new Class[] {});
		assertEquals("I may I do test ", stringB.toString());
	}
	
	
	@Test
	public void testWriteFile() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		StringBuilder content = new StringBuilder("Test of file creation.");

		writeFileMethod = kata14.getClass().getDeclaredMethod("writeFile", new Class[] { StringBuilder.class , String.class });
		writeFileMethod.setAccessible(true);

		writeFileMethod.invoke(kata14, content, Kata14Test.class.getResource("/").getPath());
		
		assertTrue(new File(getClass().getResource("/new_book.txt").getFile()).exists());
		
	
	}
}
