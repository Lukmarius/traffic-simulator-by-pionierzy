package com.codecool.pionierzy.citytrafficsim.model.vehicles;

import com.codecool.pionierzy.citytrafficsim.model.city.Edge;
import com.codecool.pionierzy.citytrafficsim.model.city.NetworkNode;

import java.util.HashSet;
import java.util.Random;

public abstract class Vehicle {

    protected double maxSpeed;
    protected double speed = 0;
    protected double acceleration;
    protected double deceleration;
    protected Edge currentRoad;
    protected double distanceTravelled = 0;
    protected NetworkNode destination;


    public void move() {
        distanceTravelled += speed;
        if (distanceTravelled >= currentRoad.getLength()) {
            setRndDirection();
        }
    }

    public void setRndDirection() {
        NetworkNode node = currentRoad.getEnding();
        HashSet<NetworkNode> neighbours = node.getNeighbours();
        int size = neighbours.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for(NetworkNode obj : neighbours)
        {
            if (i == item)
                this.destination = obj;
            i++;
        }
    }
    public NetworkNode getDestination(){
        return destination;
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }
}
