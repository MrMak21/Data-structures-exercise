package com.slg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {
    JTextField area1;
    JTextField area2;
    JLabel resultLabel;
    JButton createTextFileBtn,createDiagramBtn;
    JFrame frame;


    public Gui() {

    }



    public void showGui() {
        frame = new JFrame();
        JButton b = new JButton("click");
        area1 = new JTextField();
        area2 = new JTextField();
        resultLabel = new JLabel("",SwingConstants.CENTER);
        createTextFileBtn = new JButton("Create Text File");
        createDiagramBtn = new JButton("Create Diagram Tree");
        JLabel labelText = new JLabel("Please give me 2 names",SwingConstants.CENTER);

        labelText.setBounds(100,150,200,20);
        resultLabel.setBounds(50,300,300,50);
        createTextFileBtn.setBounds(10,380,200,30);
        createDiagramBtn.setBounds(10,420,200,30);


        area1.setBounds(100,180,100,40);
        area2.setBounds(210,180,100,40);

        b.setBounds(150,250,100,40);

        frame.add(createDiagramBtn);
        frame.add(createTextFileBtn);
        frame.add(resultLabel);
        frame.add(labelText);
        frame.add(area1);
        frame.add(area2);
        frame.add(b);
        frame.setSize(400,500);
        frame.setLayout(null);
        frame.setVisible(true);



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

        if (name1 != null && name2 != null) {
            String relation = findRelation(name1,name2);
            resultLabel.setText(relation);
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
