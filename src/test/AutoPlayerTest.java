package test;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.AutoPlayer;
import main.Rules;

public class AutoPlayerTest {
	private String name;
	private Rules rules;
	private AutoPlayer auto;

	@Before
	public void setUp() {
		name = "player name";
		rules = new Rules();
		auto = new AutoPlayer(name, rules);
	}

	@After
	public void tearDown() {
		name = null;
		rules = null;
		auto = null;
	}

	@Test
	public void testConstructor_Ok() {
		assertNotNull("The instance is not created.", auto);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_StringNull() {
		new AutoPlayer(null, rules);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_RulesNull() {
		new AutoPlayer(name, null);
	}
}