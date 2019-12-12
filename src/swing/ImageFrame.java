package swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFrame extends JFrame {

    private BufferedImage image;

    public class ImagePanel extends JPanel{



        public ImagePanel() {

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 500, 500, this); // see javadoc for more info on the parameters
        }

    }

    ImageFrame(File f){
        try {
            image = ImageIO.read(new File("image name and path"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        add(new ImagePanel());



    }

}
