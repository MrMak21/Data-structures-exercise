package com.slg;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static final String COMMA_DELIMITER = ";";
    static ArrayList<Person> allPersons;

    public static void main(String[] args) {
        // write your code here
        File file = new File("C:\\Users\\pmakris\\Desktop\\test.csv");
        allPersons = new ArrayList<>();


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

        System.out.println("All persons are: " + allPersons.size());
//        printSiblings("Kostas Makris");
//        printMarried("Kostas Makris");
//        printChildren("Kostas Makris");
//        printChildren("Athina Pantsou");

//        relationFinder("Venetsiana Makri","Paris Makris");
//        relationFinder("Kostas Makris","Paris Makris");
//        relationFinder("Athina Pantsou","Venetsiana Makri");
//        relationFinder("Paris Makris","Kostas Makris");
//        relationFinder("Kostas Makris","Paris Makris");
//        relationFinder("Venetsiana Makri","Kostas Makris");
//        relationFinder("Panos Makris","Kostas Makris");
//        relationFinder("Elli Makri","Panos Makris");
//        relationFinder("Panos Makris","Gria Makri");
//        relationFinder("Panagiotis Makris","Gria Makri");
//        relationFinder("Geros Makris","Panos Makris");
//        relationFinder("Tasos Makris","Panos Makris");
        relationFinder("Panos Makris","Tasos Makris");
    }

    private static void relationFinder(String name1, String name2) {
        Person p1 = getPersonByName(name1);
        Person p2 = getPersonByName(name2);

        //Father
        if (p1.getFather() != null){
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

        //Eggonia
        if (p1.getFather() != null) {
            if (p1.getFather().getFather() != null) {
                if (p1.getFather().getFather().name.equals(p2.name)) {
                    System.out.println(p1.name + " eggonos toy " + p2.name );
                }
                if (p1.getFather().getMother().name.equals(p2.name)) {
                    System.out.println(p1.name + " eggonos ths " + p2.name );
                }
            } else if (p1.getFather().getMother() != null) {
                if (p1.getFather().getFather().name.equals(p2.name)) {
                    System.out.println(p1.name + " eggonos toy " + p2.name );
                }
                if (p1.getFather().getMother().name.equals(p2.name)) {
                    System.out.println(p1.name + " eggonos ths " + p2.name );
                }
            }
        } else if (p1.getMother() != null) {
            if (p1.getMother().getMother() != null) {
                if (p1.getMother().getMother().name.equals(p2.name)) {
                    System.out.println(p1.name + " eggonos ths " + p2.name);
                }
                if (p1.getMother().getFather().name.equals(p2.name)) {
                    System.out.println(p1.name + " eggonos tou " + p2.name);
                }
            }else if (p1.getMother().getFather() != null) {
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





    }

    private static void printMarried(String name) {
        Person p = getPersonByName(name);
        if (p.married.size() > 0) {
            for (Person married : p.married) {
                System.out.println(p.name + " is married with " + married.name);
            }
        }
    }

    private static void printChildren(String name) {
        Person p = getPersonByName(name);
        if (p.children.size() > 0) {
            System.out.println(p.name + " chlidrens are: " + Arrays.toString( p.children.toArray() ));
//            for (Person child : p.children) {
//                System.out.println(p.name + " chlidrens are: " + child.name);
//            }
        }

    }

    private static void printSiblings(String name) {
        Person p = getPersonByName(name);
        if (p.siblings.size() > 0) {
            for (Person sibling : p.siblings) {
                System.out.println(p.name + " brother is: " + sibling.name);
            }
        } else {
            System.out.println(p.name + " has no siblings");
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
