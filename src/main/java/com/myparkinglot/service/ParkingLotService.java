package com.myparkinglot.service;

import com.myparkinglot.exception.ParkingLotException;
import com.myparkinglot.model.Car;
import com.myparkinglot.model.ParkingLot;
import com.myparkinglot.model.Slot;
import com.myparkinglot.model.parking.strategy.ParkingStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service for enable the functioning of a parking lot. This will have all the business logic of
 * how the parking service will operate.
 */
public class ParkingLotService {
  private ParkingLot parkingLot;
  private ParkingStrategy parkingStrategy;

  /**
   * Allots a parking lot into the parking service. Throws {@link ParkingLotException} if there is
   * already a parking lot alloted to the service previously.
   *
   * @param parkingLot Parking lot to be alloted.
   * @param parkingStrategy Strategy to be used while parking.
   */
  public void createParkingLot(final ParkingLot parkingLot, final ParkingStrategy parkingStrategy) {
    if (this.parkingLot != null) {
      throw new ParkingLotException("Parking lot already exists.");
    }
    this.parkingLot = parkingLot;
    this.parkingStrategy = parkingStrategy;
    for (int i = 1; i <= parkingLot.getCapacity(); i++) {
      parkingStrategy.addSlot(i);
    }
  }

  /**
   * Parks a {@link Car} into the parking lot. {@link ParkingStrategy} is used to decide the slot
   * number and then the car is parked into the {@link ParkingLot} into that slot number.
   *
   * @param car Car to be parked.
   * @return Slot number in which the car is parked.
   */
  public Integer park(final Car car) {
    validateParkingLotExists();
    final Integer nextFreeSlot = parkingStrategy.getNextSlot();
    parkingLot.park(car, nextFreeSlot);
    parkingStrategy.removeSlot(nextFreeSlot);
    return nextFreeSlot;
  }

  /**
   * Unparks a car from a slot. Free slot number is given back to the parking strategy so that it
   * becomes available for future parkings.
   *
   * @param slotNumber Slot number to be freed.
   */
  public void makeSlotFree(final Integer slotNumber) {
    validateParkingLotExists();
    parkingLot.makeSlotFree(slotNumber);
    parkingStrategy.addSlot(slotNumber);
  }

  /**
   * Calculate the parking charge for given hours
   * Charge applicable is $10 for the first 2 hours and
   * $10 for every additional hour.
   * @param hours No. of hrs car parked for.
   */
  public int calculateCharge(final int hours) {

    int amount=10;
    int tempHours=hours;

    if(hours<0){
      return -1;
    }

    if(hours>0&&hours<=2) {
    }

    else{
          tempHours-=2;
          amount=amount+tempHours*10;
    }
    return amount;
  }
  /**
   * Gets the list of all the slots which are occupied.
   */
  public List<Slot> getOccupiedSlots() {
    validateParkingLotExists();
    final List<Slot> occupiedSlotsList = new ArrayList<>();
    final Map<Integer, Slot> allSlots = parkingLot.getSlots();

    for (int i = 1; i <= parkingLot.getCapacity(); i++) {
      if (allSlots.containsKey(i)) {
        final Slot slot = allSlots.get(i);
        if (!slot.isSlotFree()) {
          occupiedSlotsList.add(slot);
        }
      }
    }

    return occupiedSlotsList;
  }

  /**
   * Helper method to validate whether the parking lot exists or not. This is used to validate the
   * existence of parking lot before doing any operation on it.
   */
  private void validateParkingLotExists() {
    if (parkingLot == null) {
      throw new ParkingLotException("Parking lot does not exists to park.");
    }
  }




}
