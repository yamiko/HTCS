/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.midlets;

/**
 *
 * @author yamiko
 */
import java.io.*;
import javax.microedition.io.*;
import javax.microedition.io.file.*;
import javax.microedition.lcdui.*;
// This class displays a selected image centered on the screen
class ImageCanvas  {
    /*    extends Canvas {

    private final ImageViewerMidlet midlet;
    private static final int CHUNK_SIZE = 1024;
    private Image currentImage = null;

    ImageCanvas(ImageViewerMidlet midlet) {
        this.midlet = midlet;
    }

    public void displayImage(String imgName) {
        try {
            FileConnection fileConn =
                    (FileConnection) Connector.open(imgName, Connector.READ);
// load the image data in memory
// Read data in CHUNK_SIZE chunks
            InputStream fis = fileConn.openInputStream();
            long overallSize = fileConn.fileSize();
            int length = 0;
            byte[] imageData = new byte[0];
            while (length < overallSize) {
                byte[] data = new byte[CHUNK_SIZE];
                int readAmount = fis.read(data, 0, CHUNK_SIZE);
                byte[] newImageData = new byte[imageData.length + CHUNK_SIZE];
                System.arraycopy(imageData, 0, newImageData, 0, length);
                System.arraycopy(data, 0, newImageData, length, readAmount);
                imageData = newImageData;
                length += readAmount;
            }
            fis.close();
            fileConn.close();
            if (length > 0) {
                currentImage = Image.createImage(imageData, 0, length);
            }
            repaint();
        } catch (IOException e) {
            midlet.showError(e);
        } catch (SecurityException e) {
            midlet.showError(e);
        } catch (IllegalArgumentException e) {
// thrown in case the file format is not understood
            midlet.showError(e);
        }
    }

    protected void paint(Graphics g) {
        int w = getWidth();
        int h = getHeight();
// Set background color to black
        g.setColor(0x00000000);
        g.fillRect(0, 0, w, h);
        if (currentImage != null) {
            g.drawImage(currentImage,
                    w / 2,
                    h / 2,
                    Graphics.HCENTER | Graphics.VCENTER);
        } else {
// If no image is available, display a message
            g.setColor(0x00FFFFFF);
            g.drawString("No image",
                    w / 2,
                    h / 2,
                    Graphics.HCENTER | Graphics.BASELINE);
        }
    }

    protected void keyReleased(int keyCode) {
// Exit with any key
        midlet.displayFileBrowser();
    }*/
}
