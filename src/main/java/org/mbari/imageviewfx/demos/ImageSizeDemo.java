package org.mbari.imageviewfx.demos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.mbari.imageviewfx.ImageViewDecorator;

/**
 * @author Brian Schlining
 * @since 2019-10-02T15:30:00
 */
public class ImageSizeDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        var image = new Image(
                "http://dsg.mbari.org/images/dsg/external/Mollusca/Cephalopoda/Opisthoteuthis_spA_01.png");
        var imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        var stackPane = new StackPane(imageView);
        stackPane.prefHeightProperty().bind(imageView.fitHeightProperty());
        stackPane.prefWidthProperty().bind(imageView.fitWidthProperty());
        stackPane.minHeight(0);
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());
        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        var borderPane = new BorderPane(stackPane);
        var scene = new Scene(borderPane);

        // Information layer
        var gridPane = new GridPane();
        borderPane.setBottom(gridPane);
        var l1 = new Label("Actual width: ");
        var l2 = new Label("Actual height: ");
        var l3 = new Label("Actual scale: ");
        var l4 = new Label("x in local: ");
        var l5 = new Label("y in local: ");
        var l6 = new Label("x in parent: ");
        var l7 = new Label("y in parent: ");
        var t1 = new Label();
        var t2 = new Label();
        var t3 = new Label();
        var t4 = new Label();
        var t5 = new Label();
        var t6 = new Label();
        var t7 = new Label();
        gridPane.addRow(0, l1, t1);
        gridPane.addRow(1, l2, t2);
        gridPane.addRow(2, l3, t3);
        gridPane.addRow(3, l4, t4);
        gridPane.addRow(4, l5, t5);
        gridPane.addRow(5, l6, t6);
        gridPane.addRow(6, l7, t7);
        imageView.boundsInLocalProperty().addListener((obs, oldv, newv) -> {
            // t1.setText(newv.getWidth() + "");
            // t2.setText(newv.getHeight() + "");
            t4.setText(newv.getMinX() + "");
            t5.setText(newv.getMinY() + "");
        });
        imageView.boundsInParentProperty().addListener((obs, oldv, newv) -> {
            t1.setText(newv.getWidth() + "");
            t2.setText(newv.getHeight() + "");
            t6.setText(newv.getMinX() + "");
            t7.setText(newv.getMinY() + "");
        });

        var imageViewDecorator = new ImageViewDecorator(imageView);
        imageViewDecorator.scaleProperty().addListener((obs, oldv, newv) -> t3.setText(newv + ""));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
