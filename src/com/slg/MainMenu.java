package com.slg;

import java.util.ArrayList;
import java.util.Scanner;

import static com.slg.Main.endGraph;

public class MainMenu {

    String value1,value2;

    public MainMenu() {
        Main.allPersons = new ArrayList<>();
        Main.readFamilyTree();
        endGraph();
    }

    public void showMenu() {
        System.out.println("-----------------------------");
        System.out.println("Please give 2 names to find the relation between them");

        Scanner scan = new Scanner(System.in);
        value1 = scan.nextLine();
        System.out.println("Good now give me another one!");
        value2 = scan.nextLine();

        findRelation(value1,value2);
    }


    private void findRelation(String value1,String value2) {
        Main.relationFinder(value1,value2);
    }


}
