package com.pingyuan.manager.users;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class CameraDialogPanel extends JPanel {

    private final WebcamPanel panel;

    public CameraDialogPanel(String cameraName, UserDialogPanel.CameraOpenListener cameraOpenListener) {
        setLayout(new BorderLayout());
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(false);
        panel.setDisplayDebugInfo(false);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        this.add(panel, BorderLayout.CENTER);
        JButton jButton = new JButton("拍照");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = "d:/" + cameraName+".png";
                try {

                    webcam.open();
                    ImageIO.write(webcam.getImage(), "PNG", new File(filePath));
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            cameraOpenListener.open(filePath,cameraName);
                            panel.stop();
                        }
                    });
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        this.add(jButton, BorderLayout.SOUTH);
    }


    public WebcamPanel getPanel() {
        return panel;
    }
}
