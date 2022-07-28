package com.company;

import java.util.concurrent.Callable;

interface LiftBaseInterface {

    void displayFloor(String floor);

    void openDoor(Callable<Void> onDone);

    void closeDoor(Callable<Void> onDone);

}
