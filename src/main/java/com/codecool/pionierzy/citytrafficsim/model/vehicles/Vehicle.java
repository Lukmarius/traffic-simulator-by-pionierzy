package com.codecool.pionierzy.citytrafficsim.model.vehicles;

import com.codecool.pionierzy.citytrafficsim.model.city.Edge;
import com.codecool.pionierzy.citytrafficsim.model.city.NetworkNode;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public abstract class Vehicle {

    double speed = 0;
    double acceleration;
    double deceleration;
    Edge currentRoad;
    double MAXSPEED;
    private double distanceTravelled = 0;
    NetworkNode destination;
    private Rectangle carView;


    public void move() {
        boolean canSpeedUp = true;
        List<Vehicle> vehicleList = currentRoad.getVehicles();
        if (distanceTravelled + 300 * speed >= currentRoad.getLength() && speed > acceleration * 180) {
            slowDown(0.2);
            canSpeedUp = false;
        }
        if (!vehicleList.isEmpty()) {
            for (Vehicle vehicle : vehicleList) {
                if (!vehicle.equals(this)) continue;
                if (this.distanceTravelled < vehicle.distanceTravelled && this.distanceTravelled + 180 * this.speed >= vehicle.distanceTravelled) {
                    if (vehicle.getSpeed() <= this.speed) {
                        slowDown(1.0);
                    } else {
                        if (this.distanceTravelled + 60 * speed >= vehicle.getDistanceTravelled()) {
                            slowDown(0.8);
                        } else {
                            slowDown(0.2);
                        }
                    }
                    canSpeedUp = false;
                    break;
                }
            }
        }
        if (distanceTravelled >= currentRoad.getLength()) {
            setRndDirection();
        }
        if (canSpeedUp) {
            if (speed < MAXSPEED - acceleration) {
                speed += acceleration;
            } else if (speed < MAXSPEED) {
                speed = MAXSPEED;
            }
        }
        distanceTravelled += speed;
    }

    private void slowDown(Double decelerationSpeed) {
        // deceleration Speed must be a double between 0 and 1
        if (speed >= deceleration * decelerationSpeed) {
            speed -= deceleration * decelerationSpeed;
        } else {
            speed = 0;
        }
    }

    public void setRndDirection() {
        NetworkNode node = currentRoad.getEnding();
        ArrayList<NetworkNode> neighbours = node.getNeighbours();
        int size = neighbours.size();
        int index;
        do {
            index = new Random().nextInt(size);
        } while (neighbours.get(index).equals(currentRoad.getBeginning()));
        HashMap roads = node.getRoads();
        this.destination = neighbours.get(index);
        this.currentRoad.removeVehicle(this);
        this.distanceTravelled = 0;
        this.currentRoad = (Edge) roads.get(this.destination);
    }

    public NetworkNode getDestination() {
        return destination;
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public Edge getCurrentRoad() {
        return currentRoad;
    }

    public Rectangle getCarView() {
        return carView;
    }

    public void setCarView(Rectangle carView) {
        this.carView = carView;
    }

    public double getSpeed() {
        return this.speed;
    }
}
