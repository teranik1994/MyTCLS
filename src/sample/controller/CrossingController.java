package sample.controller;


import sample.model.Crossing;
import sample.model.enums.LineDirection;
import sample.model.enums.RoadOrientation;
import sample.model.road.Line;
import sample.model.road.Road;
import sample.view.drawers.CrossingDrawer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

public abstract class CrossingController {


    Crossing controlledCrossing;
    CrossingDrawer drawer;
    int sessionTime;
    int middleTime;
    Timer timer;


    public CrossingController(Crossing crossing, CrossingDrawer drawer) {
        controlledCrossing = crossing;
        this.drawer = drawer;
        sessionTime = 10;
        middleTime = 5;
        timer = new Timer("Session controller", true);
    }

    public abstract void playCrossing();

    public void addNewCrosswalk(RoadOrientation orientation) {
        controlledCrossing.addNewCrosswalk(orientation);
        makeGroupOfLights();
    }

    public void addNewLine(LineDirection direction, RoadOrientation orientation) {
        controlledCrossing.addNewLine(direction, orientation);
        makeGroupOfLights();
    }

    public void addNewRoad(RoadOrientation orientation) {
        controlledCrossing.addNewRoad(orientation);
        makeGroupOfLights();
    }

    public List<String> getRoadOrientations() {
        return controlledCrossing.getRoadOrientations();
    }

    public List<String> getAbsentRoadOrientations() {
        List<String> allOrientations = new ArrayList<String>(Arrays.asList("NORTH", "SOUTH", "WEST", "EAST"));
        allOrientations.removeAll(getRoadOrientations());
        return allOrientations;
    }

    public  void stop(){
        timer.cancel();
    }


    public void checkDetector() {
        int maxCountCarsOfVerticalRoads = 0;
        int maxCountCarsOfHorizontalRoads = 0;
        RoadOrientation orientation = null;
        for (Road road : controlledCrossing.getAllRoads()) {
            orientation = road.getOrientation();
            for (Line line : road.getLines()) {
                if (orientation == RoadOrientation.NORTH || orientation == RoadOrientation.SOUTH) {
                    maxCountCarsOfVerticalRoads  = Math.max(maxCountCarsOfVerticalRoads, line.getCarCount());
                    //System.out.println(road + "First " + line.getCarCount());
                }
                if (orientation == RoadOrientation.WEST || orientation == RoadOrientation.EAST){
                    maxCountCarsOfHorizontalRoads = Math.max(maxCountCarsOfHorizontalRoads, line.getCarCount());
                    //System.out.println(road + "Second" + line.getCarCount());
                }
            }
        }
        researchMiddleTime(maxCountCarsOfVerticalRoads, maxCountCarsOfHorizontalRoads);
    }

    public abstract void makeGroupOfLights();

    //public abstract void setConflictedLightsToAllLights();

    public void researchMiddleTime(int firstGroupCount, int secondGroupCount){
        if (firstGroupCount != 0)
            middleTime = (int)(((double)sessionTime) /(firstGroupCount + secondGroupCount) * firstGroupCount);
        else
            middleTime = 0;
    }

    public void setSessionTime(int sessionTime) {
        this.sessionTime = sessionTime;
    }

    public void drawCrossing() {
        drawer.drawCrossing(controlledCrossing);
    }

    public int getMiddleTime() {
        return middleTime;
    }

    public void setCrossing(Crossing controlledCrossing) {
        this.controlledCrossing = controlledCrossing;
    }
}