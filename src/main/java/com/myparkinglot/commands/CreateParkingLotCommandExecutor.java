package com.myparkinglot.commands;

import com.myparkinglot.OutputPrinter;
import com.myparkinglot.model.Command;
import com.myparkinglot.model.ParkingLot;
import com.myparkinglot.model.parking.strategy.NaturalOrderingParkingStrategy;
import com.myparkinglot.service.ParkingLotService;
import com.myparkinglot.validator.IntegerValidator;
import java.util.List;

/**
 * Executor to handle command of creating the initial parking lot.
 */
public class CreateParkingLotCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "create";

  public CreateParkingLotCommandExecutor(
      final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
    super(parkingLotService, outputPrinter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final Command command) {
    final List<String> params = command.getParams();
    if (params.size() != 1) {
      return false;
    }
    return IntegerValidator.isInteger(params.get(0));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final Command command) {
    final int parkingLotCapacity = Integer.parseInt(command.getParams().get(0));
    final ParkingLot parkingLot = new ParkingLot(parkingLotCapacity);
    parkingLotService.createParkingLot(parkingLot, new NaturalOrderingParkingStrategy());
    outputPrinter.printInNextLine(
        "Created parking lot with " + parkingLot.getCapacity() + " slots");
  }
}
