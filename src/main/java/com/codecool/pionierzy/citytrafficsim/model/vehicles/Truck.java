package com.codecool.pionierzy.citytrafficsim.model.vehicles;

import com.codecool.pionierzy.citytrafficsim.model.city.Edge;

public class Truck extends Vehicle {
    private static final double TRUCK_ACCELERATION = 0.001;
    private static final double TRUCK_DECELERATION = 0.012;

    public Truck(Edge road) {
        MAXSPEED = 0.8;
        acceleration = TRUCK_ACCELERATION;
        deceleration = TRUCK_DECELERATION;
        currentRoad = road;
        destination = road.getEnding();
    }
}
