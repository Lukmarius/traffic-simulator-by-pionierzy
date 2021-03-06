package com.codecool.pionierzy.citytrafficsim.controller;

import com.codecool.pionierzy.citytrafficsim.model.city.Edge;
import com.codecool.pionierzy.citytrafficsim.model.vehicles.Car;
import com.codecool.pionierzy.citytrafficsim.model.vehicles.Motorcycle;
import com.codecool.pionierzy.citytrafficsim.model.vehicles.Truck;
import com.codecool.pionierzy.citytrafficsim.model.vehicles.Vehicle;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VehicleGenerator implements Runnable {
    private final int CAR_PARTICIPATION = 9;
    private final int TRUCK_PARTICIPATION = 2;
    private final int MOTORCYCLE_PARTICIPATION = 1;
    private final int INTERVAL = 1; // sec
    private ArrayList<Edge> startEdges = new ArrayList<Edge>();
    private SimLoop simLoop;
    private Random random = new Random();
    private int randint;


    public VehicleGenerator(SimLoop simLoop) {
        this.simLoop = simLoop;
    }

    @Override
    public void run() {
        for (Edge startEdge : startEdges) {
            randint = random.nextInt(CAR_PARTICIPATION + TRUCK_PARTICIPATION + MOTORCYCLE_PARTICIPATION);

            if (randint < CAR_PARTICIPATION) {
                Car v = new Car(startEdge);
                createVehicle(v, startEdge);
            } else if (randint < CAR_PARTICIPATION + TRUCK_PARTICIPATION) {
                Truck v = new Truck(startEdge);
                createVehicle(v, startEdge);
            } else if (randint < CAR_PARTICIPATION + TRUCK_PARTICIPATION + MOTORCYCLE_PARTICIPATION) {
                Motorcycle v = new Motorcycle(startEdge);
                createVehicle(v, startEdge);
            } else {
                Car v = new Car(startEdge);
                createVehicle(v, startEdge);
            }
        }
    }

    private void createVehicle(Vehicle v, Edge startEdge) {
        simLoop.addToVehicleList(v);
        startEdge.addVehicle(v);
        Platform.runLater(
                // JavaFX doesn't allow modify View from non-JavaFX threads, so Platform is needed.
                () -> {
                    simLoop.addVehicleToLane(v);
                }
        );
    }

    public void addToStartEdges(Edge startEdge) {
        this.startEdges.add(startEdge);
    }

    public void generateOneCar(Edge startEdge) {
        Car car = new Car(startEdge);
        simLoop.addToVehicleList(car);
        startEdge.addVehicle(car);
        simLoop.addVehicleToLane(car);
    }


    public void startScheduledExecutorService() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(
                () -> this.run(),
                0,
                INTERVAL,
                TimeUnit.SECONDS);
    }
}
