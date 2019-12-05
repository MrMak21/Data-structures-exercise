package com.slg;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gui {
    JTextField area1;
    JTextField area2;
    JLabel resultLabel,imageLabel;
    JButton createTextFileBtn,createDiagramBtn;
    JFrame frame;


    public Gui() {

    }



    public void showGui() {
        frame = new JFrame();
        JButton b = new JButton("Search");
        area1 = new JTextField();
        area2 = new JTextField();
        resultLabel = new JLabel("",SwingConstants.CENTER);
        createTextFileBtn = new JButton("Create Text File");
        createDiagramBtn = new JButton("Create Diagram Tree");
        JLabel labelText = new JLabel("Please give 2 names",SwingConstants.CENTER);

        try {
            BufferedImage myPicture = ImageIO.read(new File(Main.findPath() + "\\exer_image.png"));
            imageLabel = new JLabel(new ImageIcon(myPicture));
        } catch (IOException e) {
            e.printStackTrace();
        }

        labelText.setBounds(220,350,200,20);
        resultLabel.setBounds(162,480,300,50);
        createTextFileBtn.setBounds(10,550,200,30);
        createDiagramBtn.setBounds(10,590,200,30);
        imageLabel.setBounds(0,0,610,350);


        area1.setBounds(210,380,100,40);
        area2.setBounds(320,380,100,40);

        b.setBounds(265,430,100,40);

        frame.setTitle("Duck Family Tree");
        frame.add(imageLabel);
        frame.add(createDiagramBtn);
        frame.add(createTextFileBtn);
        frame.add(resultLabel);
        frame.add(labelText);
        frame.add(area1);
        frame.add(area2);
        frame.add(b);
        frame.setSize(630,700);
        frame.setLayout(null);
        frame.setVisible(true);

        //Listener fot Enter keyboard btn
        frame.getRootPane().setDefaultButton(b);



        createDiagramBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDiagram();
            }
        });


        createTextFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDialog();
            }
        });



        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeClick();
            }
        });
    }

    private void createDiagram() {
        mCreateDiagram();
        JOptionPane.showConfirmDialog(frame,"Png diagram created succesfully ","Info",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
    }

    private void showDialog() {
        writeFileForPeople();
        JOptionPane.showConfirmDialog(frame,"File Created Succesfully","Info",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
    }

    private void makeClick() {
        String name1 = null ,name2 = null;
        if (area1.getText() != null) {
            name1 = area1.getText();
        }

        if (area2.getText() != null) {
            name2 = area2.getText();
        }

        if (!name1.equals("") && !name2.equals("")) {
            String relation = findRelation(name1,name2);
            resultLabel.setText(relation);
        } else {
            JOptionPane.showMessageDialog(frame,"Please fill in the fields","Error",JOptionPane.OK_OPTION);
        }
    }

    private String findRelation(String value1,String value2) {
        return Main.relationFinder(value1,value2);
    }

    private void writeFileForPeople() {
        Main.writeFileForPeople(Main.allPersons);
    }

    private void mCreateDiagram() {
        Main.generatePngFile();
    }

}
