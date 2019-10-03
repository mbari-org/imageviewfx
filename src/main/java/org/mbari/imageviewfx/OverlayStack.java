package org.mbari.imageviewfx;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * @author Brian Schlining
 * @since 2019-10-02T15:03:00
 */
public class OverlayStack {

  private final ImageView imageView;

  private final StackPane stackPane = new StackPane();
  /** All child nodes should be absolutly positioned on this pane */
  private final Pane pane = new Pane();
  private final ImageViewDecorator imageViewDecorator;

  public OverlayStack(ImageView imageView) {
    this.imageView = imageView;
    imageViewDecorator = new ImageViewDecorator(imageView);
    stackPane.getChildren().addAll(imageView, pane);

    imageView.fitWidthProperty().bind(stackPane.widthProperty());
    imageView.fitHeightProperty().bind(stackPane.heightProperty());

  }

  private void resizeChildren() {
    var scale = imageViewDecorator.getScale();
    var bounds = imageView.getBoundsInParent();
    pane.getChildren().forEach(node -> {
      node.setScaleX(scale);
      node.setScaleY(scale);
      node.setTranslateX(bounds.getMinX());
      node.setTranslateX(bounds.getMinY());
    });
  }

  /**
   * @return the imageView
   */
  public ImageView getImageView() {
    return imageView;
  }

  /**
   * @return the stackPane
   */
  public StackPane getStackPane() {
    return stackPane;
  }

  /**
   * @return the pane
   */
  public Pane getPane() {
    return pane;
  }

}
