package com.myparkinglot.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.myparkinglot.OutputPrinter;
import com.myparkinglot.exception.NoFreeSlotAvailableException;
import com.myparkinglot.model.Car;
import com.myparkinglot.model.Command;
import com.myparkinglot.service.ParkingLotService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class ParkCommandExecutorTest {
  private ParkingLotService parkingLotService;
  private OutputPrinter outputPrinter;
  private ParkCommandExecutor parkCommandExecutor;

  @Before
  public void setUp() throws Exception {
    parkingLotService = mock(ParkingLotService.class);
    outputPrinter = mock(OutputPrinter.class);
    parkCommandExecutor = new ParkCommandExecutor(parkingLotService, outputPrinter);
  }

  @Test
  public void testValidCommand() {
    assertTrue(parkCommandExecutor.validate(new Command("park test-command-number")));
  }

  @Test
  public void testInvalidCommand() {
    assertFalse(parkCommandExecutor.validate(new Command("park")));
    assertFalse(parkCommandExecutor.validate(new Command("park test-car-number 2 abcd")));
  }

  @Test
  public void testCommandExecutionWhenParkingSucceeds() {
    when(parkingLotService.park(any())).thenReturn(1);
    parkCommandExecutor.execute(new Command("park test-car-number"));

    final ArgumentCaptor<Car> argument = ArgumentCaptor.forClass(Car.class);
    verify(parkingLotService).park(argument.capture());
    assertEquals("test-car-number", argument.getValue().getRegistrationNumber());

    verify(outputPrinter).printInNextLine("Allocated slot number: 1");
  }

  @Test
  public void testCommandExecutionWhenParkingIsFull() {
    when(parkingLotService.park(any())).thenThrow(new NoFreeSlotAvailableException());
    parkCommandExecutor.execute(new Command("park test-car-number"));

    final ArgumentCaptor<Car> argument = ArgumentCaptor.forClass(Car.class);
    verify(parkingLotService).park(argument.capture());
    assertEquals("test-car-number", argument.getValue().getRegistrationNumber());

    verify(outputPrinter).parkingLotFull();
  }
}
