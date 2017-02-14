package org.dekinci.oscillograph;

import org.dekinci.oscillograph.compart.TransferableImage;
import org.dekinci.oscillograph.imagepart.EditableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Edits, parses image and sends it to Arduino
 */

public class OscilloGraph {
    public static void main(String args[]) throws IOException {
        final int heightOscill = 11; //according to the arduino Mega PWM outputs

        EditableImage eImage = new EditableImage(ImageIO.read(new File("Images/image.jpg")));
        eImage.convertToBnW();
        eImage.resize(heightOscill);

        ImageIO.write(eImage.getEditedImage(), "JPEG", new File("Images/prepared.jpg"));

        TransferableImage tImage = new TransferableImage(eImage.getEditedImage());
        tImage.transferImage();
    }
}
