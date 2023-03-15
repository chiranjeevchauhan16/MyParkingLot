package com.myparkinglot.commands;

import com.myparkinglot.OutputPrinter;
import com.myparkinglot.exception.InvalidSlotException;
import com.myparkinglot.exception.ParkingLotException;
import com.myparkinglot.model.Command;
import com.myparkinglot.model.Slot;
import com.myparkinglot.service.ParkingLotService;
import com.myparkinglot.validator.IntegerValidator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Executor to handle command of freeing of slot from a car.
 */
public class LeaveCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "leave";

  public LeaveCommandExecutor(final ParkingLotService parkingLotService,
      final OutputPrinter outputPrinter) {
    super(parkingLotService, outputPrinter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final Command command) {
    final List<String> params = command.getParams();
    if (params.size() != 2) {
      return false;
    }

    if(params.get(0).isEmpty()){
      return false;
    }
    return IntegerValidator.isInteger(params.get(1));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final Command command) {

    final String registrationNumber = command.getParams().get(0);


    final List<Slot> occupiedSlots = parkingLotService.getOccupiedSlots();

    Map<String,Integer> slotNoAndCarRegNoMap = occupiedSlots.stream().collect(Collectors.toMap(x->x.getParkedCar().getRegistrationNumber(),x->x.getSlotNumber()));
    Integer slotNum=slotNoAndCarRegNoMap.get(registrationNumber);

    if(slotNum==null)
        throw new ParkingLotException("Invalid Registration Number");

    int amount=parkingLotService.calculateCharge(Integer.parseInt(command.getParams().get(1)));
    parkingLotService.makeSlotFree(slotNum);
    outputPrinter.printInNextLine("Registration number "+registrationNumber+" from Slot " + slotNum + " has left with Charge "+amount);
  }
}
