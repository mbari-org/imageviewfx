package org.mbari.imageviewfx;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A decorator that returns the actual scale of the imageview. Useful for when
 * preserveRatio is set to true, but you need to track the actual size of the image
 * @author Brian Schlining
 * @since 2019-10-02T14:53:00
 */
public class ImageViewDecorator {
    private final ImageView imageView;
    private ReadOnlyDoubleWrapper scale = new ReadOnlyDoubleWrapper(Double.NaN);

    public ImageViewDecorator(ImageView imageView) {
        this.imageView = imageView;
        imageView.imageProperty().addListener(i -> calculateScale());
        imageView.fitHeightProperty().addListener(i -> calculateScale());
        imageView.fitWidthProperty().addListener(i -> calculateScale());
    }

    private void calculateScale() {
        if (!imageView.isPreserveRatio()) {
            scale.set(Double.NaN);
        }
        else {
            Image localImage = imageView.getImage();
            Rectangle2D localViewport = imageView.getViewport();

            double w = 0;
            double h = 0;
            if (localViewport != null && localViewport.getWidth() > 0 && localViewport.getHeight() > 0) {
                w = localViewport.getWidth();
                h = localViewport.getHeight();
            } else if (localImage != null) {
                w = localImage.getWidth();
                h = localImage.getHeight();
            }

            double localFitWidth = imageView.getFitWidth();
            double localFitHeight = imageView.getFitHeight();

            if (w > 0 && h > 0 && (localFitWidth > 0 || localFitHeight > 0)) {
                if (localFitWidth <= 0 || (localFitHeight > 0 && localFitWidth * h > localFitHeight * w)) {
                    w = w * localFitHeight / h;
                } else {
                    w = localFitWidth;
                }

                double computedScale = w / localImage.getWidth();
                scale.set(computedScale);
            }
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public double getScale() {
        return scale.get();
    }

    public ReadOnlyDoubleProperty scaleProperty() {
        return scale.getReadOnlyProperty();
    }
}
