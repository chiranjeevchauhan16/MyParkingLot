package com.myparkinglot.commands;

import com.myparkinglot.OutputPrinter;
import com.myparkinglot.exception.NoFreeSlotAvailableException;
import com.myparkinglot.model.Car;
import com.myparkinglot.model.Command;
import com.myparkinglot.service.ParkingLotService;

/**
 * Executor to handle command of parking a car into the parking lot. This outputs the alloted slot
 * in which the car is parked.
 */
public class ParkCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "park";

  public ParkCommandExecutor(
      final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
    super(parkingLotService, outputPrinter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final Command command) {
    return command.getParams().size() == 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final Command command) {
    final Car car = new Car(command.getParams().get(0));
    try {
      final Integer slot = parkingLotService.park(car);
      outputPrinter.printInNextLine("Allocated slot number: " + slot);
    } catch (NoFreeSlotAvailableException exception) {
      outputPrinter.parkingLotFull();
    }
  }
}
