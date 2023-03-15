package com.myparkinglot.commands;

import com.myparkinglot.OutputPrinter;
import com.myparkinglot.exception.InvalidCommandException;
import com.myparkinglot.model.Command;
import com.myparkinglot.service.ParkingLotService;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory to get correct {@link CommandExecutor} from a given command.
 */
public class CommandExecutorFactory {
  private Map<String, CommandExecutor> commands = new HashMap<>();

  public CommandExecutorFactory(final ParkingLotService parkingLotService) {
    final OutputPrinter outputPrinter = new OutputPrinter();
    commands.put(
        CreateParkingLotCommandExecutor.COMMAND_NAME,
        new CreateParkingLotCommandExecutor(parkingLotService, outputPrinter));
    commands.put(
        ParkCommandExecutor.COMMAND_NAME,
        new ParkCommandExecutor(parkingLotService, outputPrinter));
    commands.put(
        LeaveCommandExecutor.COMMAND_NAME,
        new LeaveCommandExecutor(parkingLotService, outputPrinter));
    commands.put(
        StatusCommandExecutor.COMMAND_NAME,
        new StatusCommandExecutor(parkingLotService, outputPrinter));
    commands.put(
        ExitCommandExecutor.COMMAND_NAME,
        new ExitCommandExecutor(parkingLotService, outputPrinter));
  }

  /**
   * Gets {@link CommandExecutor} for a particular command. It basically uses name of command to
   * fetch its corresponding executor.
   *
   * @param command Command for which executor has to be fetched.
   * @return Command executor.
   */
  public CommandExecutor getCommandExecutor(final Command command) {
    final CommandExecutor commandExecutor = commands.get(command.getCommandName());
    if (commandExecutor == null) {
      throw new InvalidCommandException();
    }
    return commandExecutor;
  }
}
