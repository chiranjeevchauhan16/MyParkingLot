package com.myparkinglot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.myparkinglot.exception.InvalidCommandException;
import com.myparkinglot.exception.InvalidModeException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainTest {
  private InputStream sysInBackup;
  private PrintStream sysOutBackup;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setUp() throws Exception {
    sysInBackup = System.in; // backup System.in to restore it later
    sysOutBackup = System.out; // backup System.out to restore it later
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void tearDown() throws Exception {
    System.setIn(sysInBackup);
    System.setOut(sysOutBackup);
  }

  @Test
  public void testInteractiveMode() throws IOException {
    final String commands =
        "create 6\r\n"
            + "park KA-01-HH-1234\r\n"
            + "park KA-01-HH-9999\r\n"
            + "park KA-01-BB-0001\r\n"
            + "park KA-01-HH-7777\r\n"
            + "park KA-01-HH-2701\r\n"
            + "park KA-01-HH-3141\r\n"
            + "leave KA-01-HH-1234 4\r\n"
            + "status\r\n"
            + "park KA-01-P-333\r\n"
            + "park DL-12-AA-9999\r\n"
            + "exit\r\n";

    final ByteArrayInputStream in = new ByteArrayInputStream(commands.getBytes());
    System.setIn(in);

    Main.main(new String[] {});
    assertNotNull(outContent.toString());
  }

  @Test
  public void testStatusOfEmptyParkingLot() throws IOException {
    final String commands = "create 6\r\n" + "status\r\n" + "exit\r\n";
    final String expectedOutput =
        "Welcome to My Parking lot.\n"
            + "Created parking lot with 6 slots\n"
            + "Parking lot is empty\n"
            + "Thanks for using My Parking lot service.\n";

    final ByteArrayInputStream in = new ByteArrayInputStream(commands.getBytes());
    System.setIn(in);

    Main.main(new String[] {});
    assertEquals(expectedOutput, outContent.toString());
  }

  @Test(expected = InvalidCommandException.class)
  public void testInvalidCommandParams() throws IOException {
    final String commands = "create 6 1\r\n";
    final ByteArrayInputStream in = new ByteArrayInputStream(commands.getBytes());
    System.setIn(in);

    Main.main(new String[] {});
  }

  @Test
  public void testFileMode() throws IOException {
    Main.main(new String[] {"file_input.txt"});
    assertNotNull(outContent.toString());
  }

  @Test
  public void testFileModeWithInvalidFile() throws IOException {
    final String expectedOutput = "Invalid file given.\n";
    Main.main(new String[] {"some_random_file.txt"});
    assertEquals(expectedOutput, outContent.toString());
  }

  @Test(expected = InvalidModeException.class)
  public void testInvalidMode() throws IOException {
    Main.main(new String[] {"file_input.txt", "some-other-input"});
  }
}
