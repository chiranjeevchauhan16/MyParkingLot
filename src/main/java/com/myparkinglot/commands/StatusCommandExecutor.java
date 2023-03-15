package com.myparkinglot.commands;

import com.myparkinglot.OutputPrinter;
import com.myparkinglot.model.Car;
import com.myparkinglot.model.Command;
import com.myparkinglot.model.Slot;
import com.myparkinglot.service.ParkingLotService;
import java.util.List;

/**
 * Executor to handle command of fetching the current status of the parking lot. It gives which
 * slot has which car. Car details will have both its registration number and its color.
 */
public class StatusCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "status";

  public StatusCommandExecutor(final ParkingLotService parkingLotService,
      final OutputPrinter outputPrinter) {
    super(parkingLotService, outputPrinter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final Command command) {
    return command.getParams().isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(Command command) {
    final List<Slot> occupiedSlots = parkingLotService.getOccupiedSlots();

    if (occupiedSlots.isEmpty()) {
      outputPrinter.parkingLotEmpty();
      return;
    }

    outputPrinter.parkingStatus();
    for (Slot slot : occupiedSlots) {
      final Car parkedCar = slot.getParkedCar();
      final String slotNumber = slot.getSlotNumber().toString();

      outputPrinter.printInNextLine(padString(slotNumber, 12)
          + padString(parkedCar.getRegistrationNumber(), 19));
    }
  }

  private static String padString(final String word, final int length) {
    String newWord = word;
    for(int count = word.length(); count < length; count++) {
      newWord = newWord + " ";
    }
    return newWord;
  }
}
