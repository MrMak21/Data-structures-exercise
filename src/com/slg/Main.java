package com.slg;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static final String COMMA_DELIMITER = ";";
    public static ArrayList<Person> allPersons;
    public static boolean isFound = false;
    public static String graph = "";
    public static StringBuilder sb1;

    public static void main(String[] args) {

        sb1 = new StringBuilder("digraph G { ");


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

//    public static void createDotGraph(String dotFormat,String fileName)
//    {
//        Graphviz gv = new Graphviz();
//
//        //GraphViz gv=new GraphViz();
//        gv.addln(gv.start_graph());
//        gv.add(dotFormat);
//        gv.addln(gv.end_graph());
//        // String type = "gif";
//        String type = "pdf";
//        // gv.increaseDpi();
//        gv.decreaseDpi();
//        gv.decreaseDpi();
//        File out = new File(fileName+"."+ type);
//        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
//    }

//    public static void ex2() {
//        final Node
//                init = node("init"),
//                execute = node("execute"),
//                compare = node("compare").with(Shape.RECTANGLE, Style.FILLED, Color.hsv(.7, .3, 1.0)),
//                make_string = node("make_string"),
//                printf = node("printf");
//        final Graph g = graph("ex2").directed().with(
//                node("main").with(Shape.RECTANGLE).link(
//                        to(node("parse").link(execute)).with("weight", 8),
//                        to(init).with(Style.DOTTED),
//                        node("cleanup"),
//                        to(printf).with(Style.BOLD, Label.of("100 times"), Color.RED)),
//                execute.link(graph().with(make_string, printf), to(compare).with(Color.RED)),
//                init.link(make_string.with(Label.of("make a\nstring"))));
//        Graphviz.fromGraph(g).render(SVG).toString();
//    }

    public static void addToGraph(String name1, String name2, String relation) {
        if (relation.equals(GraphVizUtils.MARRIED)) {
            sb1.append(" \n " + "\"" + name1 + "\"" + " -> " + "\"" + name2 + "\" " + "[label=" + relation + " " + GraphVizUtils.MARRIED_COLOR + "];" );
        } else if (relation.equals(GraphVizUtils.SIBLINGS)) {
            sb1.append(" \n " + "\"" + name1 + "\"" + " -> " + "\"" + name2 + "\" " + "[label=" + relation + " " + GraphVizUtils.SIBLINGS_COLOR + " " + GraphVizUtils.SIBLINGS_STYLE + "];" );
        } else if (relation.equals(GraphVizUtils.FATHER)) {
            sb1.append(" \n " + "\"" + name1 + "\"" + " -> " + "\"" + name2 + "\" " + "[label=" + relation + " " + GraphVizUtils.FATHER_COLOR + "];" );
        } else if (relation.equals(GraphVizUtils.MOTHER)) {
            sb1.append(" \n " + "\"" + name1 + "\"" + " -> " + "\"" + name2 + "\" " + "[label=" + relation + " " + GraphVizUtils.MOTHER_COLOR + "];" );
        } else {
            sb1.append(" \n " + "\"" + name1 + "\"" + " -> " + "\"" + name2 + "\" " + "[label=" + relation + "];");
        }
    }

    public static void endGraph() {
        sb1.append(" \n }");
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
                    if (p != null) {

                        if (data[1].equals("siblings")) {
                            p.siblings.add(getPersonByName(data[2]));
                            addToGraph(data[0],data[2],data[1]);

                        } else if (data[1].equals("father")) {
                            if (p.sex.equals("male")) {
                                p.children.add(getPersonByName(data[2]));
                                getPersonByName(data[2]).setFather(p);
                                addToGraph(data[0],data[2],data[1]);
                            }

                        } else if (data[1].equals("mother")) {
                            if (p.sex.equals("female")) {
                                p.children.add(getPersonByName(data[2]));
                                getPersonByName(data[2]).setMother(p);
                                addToGraph(data[0],data[2],data[1]);
                            }
                        } else if (data[1].equals("married")) {
                            p.married.add(getPersonByName(data[2]));
                            addToGraph(data[0],data[2],data[1]);
                        }
                    }

                } else {
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
                        isFound = true;
                    } else {
                        System.out.println(p1.name + " is daughter of " + p2.name);
                        isFound = true;
                    }
                }
            }

            //Mother
            if (p1.getMother() != null) {
                if (p1.getMother().name.equals(p2.name)) {
                    if (p1.sex.equals("male")) {
                        System.out.println(p1.name + " is son of " + p2.name);
                        isFound = true;
                    } else {
                        System.out.println(p1.name + " is daughter of " + p2.name);
                        isFound = true;
                    }
                }
            }

            //Children
            for (Person child : p1.children) {
                if (child.name.equals(p2.name)) {
                    if (p1.sex.equals("male")) {
                        System.out.println(p1.name + " is father of " + p2.name);
                        isFound = true;
                    } else {
                        System.out.println(p1.name + " is mother of " + p2.name);
                        isFound = true;
                    }
                }
            }

            //Married
            for (Person person : p1.married) {
                if (person.name.equals(p2.name)) {
                    System.out.println(p1.name + " is married with " + p2.name);
                    isFound = true;
                }
            }

            ///Siblings
            for (Person sibling : p1.siblings) {
                if (sibling.name.equals(p2.name)) {
                    if (p1.sex.equals("male")) {
                        System.out.println(p1.name + " is brother of " + p2.name);
                        isFound = true;
                    } else {
                        System.out.println(p1.name + " is sister of " + p2.name);
                        isFound = true;
                    }
                }
            }

            //Cousins
            if (p1.getFather() != null) {
                for (Person sibling : p1.getFather().siblings) {
                    for (Person child : sibling.children) {
                        if (child.name.equals(p2.name)) {
                            System.out.println(p1.name + " is cousin with " + p2.name + " from " + p1.getFather().name + " and " + sibling.name);
                            isFound = true;
                        }
                    }
                }
            }

            if (p1.getMother() != null) {
                for (Person sibling : p1.getMother().siblings) {
                    for (Person child : sibling.children) {
                        if (child.name.equals(p2.name)) {
                            System.out.println(p1.name + " is cousin with " + p2.name + " from " + p1.getMother().name + " and " + sibling.name);
                            isFound = true;
                        }
                    }
                }
            }


            //Eggonia
            if (p1.getFather() != null) {
                if (p1.getFather().getFather() != null) {
                    if (p1.getFather().getFather().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos toy " + p2.name);
                        isFound = true;
                    }
                    if (p1.getFather().getMother().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos ths " + p2.name);
                        isFound = true;
                    }
                } else if (p1.getFather().getMother() != null) {
                    if (p1.getFather().getFather().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos toy " + p2.name);
                        isFound = true;
                    }
                    if (p1.getFather().getMother().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos ths " + p2.name);
                        isFound = true;
                    }
                }
            }
            if (p1.getMother() != null) {
                if (p1.getMother().getMother() != null) {
                    if (p1.getMother().getMother().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos ths " + p2.name);
                        isFound = true;
                    }
                    if (p1.getMother().getFather().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos tou " + p2.name);
                        isFound = true;
                    }
                } else if (p1.getMother().getFather() != null) {
                    if (p1.getMother().getMother().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos ths " + p2.name);
                        isFound = true;
                    }
                    if (p1.getMother().getFather().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos tou " + p2.name);
                        isFound = true;
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
                                    isFound = true;
                                } else {
                                    System.out.println(p1.name + " grandmother of " + p2.name);
                                    isFound = true;
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
                                isFound = true;
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
                            isFound = true;
                        }
                    }
                }
            }

            if (p1.getMother() != null) {
                if (p1.getMother().siblings.size() > 0) {
                    for (Person sibling : p1.getMother().siblings) {
                        if (sibling.name.equals(p2.name)) {
                            System.out.println(p1.name + " anipsi of " + p2.name);
                            isFound = true;
                        }
                    }
                }
            }

            //Not related
            if (!isFound) {
                System.out.println(p1.name + " and " + p2.name + " are not related!");
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
