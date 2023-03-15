package com.myparkinglot;

/**
 * Printer to help in printing the output back to the user. Output medium can be changed here
 * anytime without affecting any of the other code. Currently, System.out is used. Tomorrow if file
 * has to be used to output, it can be changed here easily.
 */
public class OutputPrinter {

  public void welcome() {
    printInNextLine("Welcome to My Parking lot.");
  }

  public void end() {
    printInNextLine("Thanks for using My Parking lot service.");
  }

  public void notFound() {
    printInNextLine("Not found");
  }

  public void parkingStatus() {
    printInNextLine("Slot No.    Registration No.");
  }

  public void parkingLotFull() {
    printInNextLine("Sorry, parking lot is full");
  }

  public void parkingLotEmpty() {
    printInNextLine("Parking lot is empty");
  }

  public void invalidFile() {
    printInNextLine("Invalid file given.");
  }

  public void printInNextLine(final String msg) {
    System.out.println(msg);
  }
}
