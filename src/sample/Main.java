package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.controller.CrossingController;
import sample.controller.OrdinaryController;
import sample.model.Crossing;
import sample.model.enums.LineDirection;
import sample.model.enums.RoadOrientation;
import sample.view.CrossingView;
import sample.view.lightView.DriverLightView;
import sample.view.drawers.CrossingDrawer;

public class Main extends Application {
    CrossingDrawer crDrTest;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        primaryStage.setResizable(false);
        primaryStage.setTitle("TCLS");
        CrossingView crTest = new CrossingView();
        root.getChildren().add(crTest);
        primaryStage.setScene(new Scene(root, 700, 600));
        crDrTest = new CrossingDrawer(crTest);
        testDrawingCrossing();
        primaryStage.show();

    }


    public static void main(String[] args) {


        launch(args);


    }

    public void testDrawingCrossing() {
        Crossing testCr = new Crossing();
        testCr.addNewLine(LineDirection.TO_RIGHT, RoadOrientation.NORTH);
        testCr.addNewLine(LineDirection.TO_RIGHT, RoadOrientation.NORTH);
        //testCr.addNewRoad(RoadOrientation.NORTH);
        testCr.addNewRoad(RoadOrientation.WEST);
        testCr.addNewRoad(RoadOrientation.EAST);
        testCr.addNewCrosswalk(RoadOrientation.WEST);
        testCr.addNewCrosswalk(RoadOrientation.NORTH);
        testCr.addNewCrosswalk(RoadOrientation.SOUTH);
        testCr.addNewCrosswalk(RoadOrientation.EAST);
        testCr.addNewLine(LineDirection.TO_RIGHT, RoadOrientation.WEST);
        CrossingController controllerTest = new OrdinaryController(testCr);
        controllerTest.makeGroupOfLights();
        //System.out.println(controllerTest.toString());
        //controllerTest.playCrossing();
        crDrTest.drawCrossing(testCr);
        /*for(Road road : testCr.getAllRoads())
            for(Line line : road.getLines())
            line.printConflict();*/


        System.out.println(testCr);
    }

}
