package io.pantherhub.aws.dynamodb;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TableTest {
	
	Table underTest;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty(TableTest.class.getPackage().toString(), "true");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.setProperty(TableTest.class.getPackage().toString(), "");
	}

	@Before
	public void setUp() throws Exception {
		underTest = new Table();
	}

	@After
	public void tearDown() throws Exception {
		underTest.delete("dynamo");	
	}

	@Test
	public void testCreateString() {
		assertEquals("Create a table must return status 200", new Integer(200), underTest.create("dynamo").getStatus());
		assertEquals("Create the same table must return status 500", new Integer(500), underTest.create("dynamo").getStatus());
	}

	@Test
	public void testCreateStringLongLong() {
		assertEquals("Create a table must return status 200", new Integer(200), underTest.create("dynamo", 5L, 10L).getStatus());
		assertEquals("Create the same table must return status 500", new Integer(500), underTest.create("dynamo").getStatus());

	}

	@Test
	public void testUpdate() {
		assertEquals("Update a non existing table must return status 500", new Integer(500), underTest.update("dynamo", 10L, 10L).getStatus());
		
		underTest.create("dynamo");
		assertEquals("Update an existing table must return status 200", new Integer(200), underTest.update("dynamo", 100L, 100L).getStatus());
		
		assertEquals("Update an existing table with non changing capacities must return status 500", new Integer(500), underTest.update("dynamo", 100L, 100L).getStatus());
	}

	@Test
	public void testDelete() {
		assertEquals("Delete non existing table must return status 500", new Integer(500), underTest.delete("dynamo").getStatus());
		
		underTest.create("dynamo");
		assertEquals("Delete an existing table must return status 200", new Integer(200), underTest.delete("dynamo").getStatus());
	}

	@Test
	public void testIsLocalEnv() {
		assertTrue("It must be a local environment", underTest.isLocalEnv());
	}

}
