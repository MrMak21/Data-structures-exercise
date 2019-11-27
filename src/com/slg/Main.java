package com.slg;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static final String COMMA_DELIMITER = ";";
    public static ArrayList<Person> allPersons;

    public static void main(String[] args) {


        MainMenu menu = new MainMenu();
        menu.showMenu();

        System.out.println("All persons are: " + allPersons.size());


        //Father/son
//        relationFinder("Grandpa Duck","Quackmore Duck");
//        relationFinder("Quackmore Duck","Grandpa Duck");

        //Siblings
//        relationFinder("1 (Duck) Von Drake","Quackmore Duck");
//        relationFinder("Quackmore Duck","1 (Duck) Von Drake");

        //Ksaderfia -- NOT WORKING --> WORKING NOW
//        relationFinder("May","Huey Duck");
//        relationFinder("Huey Duck","May");

        //Husband
//        relationFinder("Quackmore Duck","Hortense (McDuck) Duck");
//        relationFinder("Hortense (McDuck) Duck","Quackmore Duck");

        //Grandpa
//        relationFinder("Quackmore Duck","Louie Duck");
//        relationFinder("Grandma Duck","Gus Goose");

        //Eggonos -- NOT WORKING -- > ALSO WORKING NOW
//        relationFinder("Louie Duck","Quackmore Duck");
//        relationFinder("Gus Goose","Grandma Duck");

        //Anipsi
//        relationFinder("Louie Duck","Daisy Duck");
//        relationFinder("Gus Goose","Quackmore Duck");

        //Uncle
//        relationFinder("Daisy Duck","Louie Duck");
//        relationFinder("Quackmore Duck","Gus Goose");

        //Not related -- NOT WORKING
//        relationFinder("Daisy Duck","Quackmore Duck");

    }

    public static void readFamilyTree() {
        File file = new File("C:\\Users\\pmakris\\Desktop\\ergasia.csv");
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(file));
            String row;


            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(COMMA_DELIMITER);
                if (data.length > 2) {
                    Person p = getPersonByName(data[0]);
                    if (p!= null ) {

                        if (data[1].equals("siblings")) {
                            p.siblings.add(getPersonByName(data[2]));

                        } else if (data[1].equals("father")) {
                            if (p.sex.equals("male")) {
                                p.children.add(getPersonByName(data[2]));
                                getPersonByName(data[2]).setFather(p);
                            }

                        } else if (data[1].equals("mother")) {
                            if (p.sex.equals("female")) {
                                p.children.add(getPersonByName(data[2]));
                                getPersonByName(data[2]).setMother(p);
                            }
                        } else if (data[1].equals("married")) {
                            p.married.add(getPersonByName(data[2]));
                        }
                    }

                }
                else {
                    //If lines = 2
                    Person newPer = new Person(data[0], data[1]);
                    allPersons.add(newPer);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void relationFinder(String name1, String name2) {
        Person p1 = getPersonByName(name1);
        Person p2 = getPersonByName(name2);


        if (p1 != null && p2 != null) {
            //Father
            if (p1.getFather() != null) {
                if (p1.getFather().name.equals(p2.name)) {
                    if (p1.sex.equals("male")) {
                        System.out.println(p1.name + " is son of " + p2.name);
                    } else {
                        System.out.println(p1.name + " is daughter of " + p2.name);
                    }
                }
            }

            //Mother
            if (p1.getMother() != null) {
                if (p1.getMother().name.equals(p2.name)) {
                    if (p1.sex.equals("male")) {
                        System.out.println(p1.name + " is son of " + p2.name);
                    } else {
                        System.out.println(p1.name + " is daughter of " + p2.name);
                    }
                }
            }

            //Children
            for (Person child : p1.children) {
                if (child.name.equals(p2.name)) {
                    if (p1.sex.equals("male")) {
                        System.out.println(p1.name + " is father of " + p2.name);
                    } else {
                        System.out.println(p1.name + " is mother of " + p2.name);
                    }
                }
            }

            //Married
            for (Person person : p1.married) {
                if (person.name.equals(p2.name)) {
                    System.out.println(p1.name + " is married with " + p2.name);
                }
            }

            ///Siblings
            for (Person sibling : p1.siblings) {
                if (sibling.name.equals(p2.name)) {
                    if (p1.sex.equals("male")) {
                        System.out.println(p1.name + " is brother of " + p2.name);
                    } else {
                        System.out.println(p1.name + " is sister of " + p2.name);
                    }
                }
            }

            //Cousins
            if (p1.getFather() != null) {
                for (Person sibling : p1.getFather().siblings) {
                    for (Person child : sibling.children) {
                        if (child.name.equals(p2.name)) {
                            System.out.println(p1.name + " is cousin with " + p2.name + " from " + p1.getFather().name + " and " + sibling.name);
                        }
                    }
                }
            }

            if (p1.getMother() != null) {
                for (Person sibling : p1.getMother().siblings) {
                    for (Person child : sibling.children) {
                        if (child.name.equals(p2.name)) {
                            System.out.println(p1.name + " is cousin with " + p2.name + " from " + p1.getMother().name + " and " + sibling.name);
                        }
                    }
                }
            }


            //Eggonia
            if (p1.getFather() != null) {
                if (p1.getFather().getFather() != null) {
                    if (p1.getFather().getFather().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos toy " + p2.name);
                    }
                    if (p1.getFather().getMother().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos ths " + p2.name);
                    }
                } else if (p1.getFather().getMother() != null) {
                    if (p1.getFather().getFather().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos toy " + p2.name);
                    }
                    if (p1.getFather().getMother().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos ths " + p2.name);
                    }
                }
            }
            if (p1.getMother() != null) {
                if (p1.getMother().getMother() != null) {
                    if (p1.getMother().getMother().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos ths " + p2.name);
                    }
                    if (p1.getMother().getFather().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos tou " + p2.name);
                    }
                } else if (p1.getMother().getFather() != null) {
                    if (p1.getMother().getMother().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos ths " + p2.name);
                    }
                    if (p1.getMother().getFather().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos tou " + p2.name);
                    }
                }
            }

            //Grandpas
            if (p1.children.size() > 0) {
                for (Person child : p1.children) {
                    if (child.children.size() > 0) {
                        for (Person person : child.children) {
                            if (person.name.equals(p2.name)) {
                                if (p1.sex.equals("male")) {
                                    System.out.println(p1.name + " grandpa of " + p2.name);
                                } else {
                                    System.out.println(p1.name + " grandmother of " + p2.name);
                                }
                            }
                        }
                    }
                }
            }

            //Uncle
            if (p1.siblings.size() > 0) {
                for (Person sibling : p1.siblings) {
                    if (sibling.children.size() > 0) {
                        for (Person child : sibling.children) {
                            if (child.name.equals(p2.name)) {
                                System.out.println(p1.name + " Unlce of " + p2.name);
                            }
                        }
                    }
                }
            }

            //Anipsios
            if (p1.getFather() != null) {
                if (p1.getFather().siblings.size() > 0) {
                    for (Person sibling : p1.getFather().siblings) {
                        if (sibling.name.equals(p2.name)) {
                            System.out.println(p1.name + " anipsi of " + p2.name);
                        }
                    }
                }
            }

            if (p1.getMother() != null) {
                if (p1.getMother().siblings.size() > 0) {
                    for (Person sibling : p1.getMother().siblings) {
                        if (sibling.name.equals(p2.name)) {
                            System.out.println(p1.name + " anipsi of " + p2.name);
                        }
                    }
                }
            }
        } else {
            if (p1 == null) {
                System.out.println("Cannot find person  " + name1);
            }
            if (p2 == null) {
                System.out.println("Cannot find person " + name2);
            }
        }

    }

    private static Person getPersonByName(String name) {

        for (Person person : allPersons) {
            if (person.name.equals(name)) {
                return person;
            }
        }

        return null;
    }
}
