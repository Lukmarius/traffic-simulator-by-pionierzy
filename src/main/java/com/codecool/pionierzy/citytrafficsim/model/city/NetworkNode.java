package com.codecool.pionierzy.citytrafficsim.model.city;

import com.codecool.pionierzy.citytrafficsim.view.city.NetworkNodeDisplay;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.HashSet;

public class NetworkNode {
    private HashSet<NetworkNode> neighbours = new HashSet<NetworkNode>();
    private int x;
    private int y;
    private HashMap<NetworkNode, Edge> roads = new HashMap<NetworkNode, Edge>();
    private NetworkNodeDisplay display;

    public NetworkNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public HashSet<NetworkNode> getNeighbours() {
        return neighbours;
    }

    public Edge addNeighbour(NetworkNode node) {
        neighbours.add(node);
        Edge edge = new Edge(this, node);
        roads.put(node, edge);
        return edge;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public HashMap<NetworkNode, Edge> getRoads() {
        return roads;
    }
}
