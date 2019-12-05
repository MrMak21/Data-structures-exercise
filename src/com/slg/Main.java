package com.slg;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {

    public static final String COMMA_DELIMITER = ";";
    public static ArrayList<Person> allPersons;
    public static boolean isFound = false;
    public static String graph = "";
    public static StringBuilder sb1;

    public static void main(String[] args) {

        Gui gui = new Gui();
        gui.showGui();
        findPath();

        sb1 = new StringBuilder("digraph G { ");
        MainMenu menu = new MainMenu();
        menu.showMenu();

        System.out.println("All persons are: " + allPersons.size());
        writeFileForPeople(allPersons);
    }

    public static String findPath() {

        String currentDir = System.getProperty("user.dir");
//        System.out.println("Current dir using System: " + currentDir);

        return currentDir;
    }


    public static void writeFileForPeople(ArrayList<Person> persons) {
        persons.sort(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.name.compareTo(o2.name);
            }
        });

        writeToFile(persons);
    }

    private static void writeToFile(ArrayList<Person> persons) {
        PrintWriter writer = null;
//        PrintWriter writer2 = null;
        try {
            writer = new PrintWriter( findPath() + "\\personsSortedList.txt", "UTF-8");
//            writer2 = new PrintWriter(findPath() + "\\graph.dot", "UTF-8");
//            writer2.println(sb1.toString());
            for (Person person : persons) {
                writer.println(person.name + " " + person.sex);
            }
//            generatePngFile();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            writer.close();
//            writer2.close();
        }
    }

    public static void generatePngFile() {
        PrintWriter writer2 = null;
        String imageName = "\\generatedGraph.png";
        String cmd = findPath() + "\\graphViz\\release\\bin\\dot -Tpng -o " + findPath() + imageName + " " +  findPath() + "\\graph.dot";
        String openImage = findPath() + imageName;
        try {
            writer2 = new PrintWriter(findPath() + "\\graph.dot", "UTF-8");
            writer2.println(sb1.toString());
            Process p = Runtime.getRuntime().exec(cmd);
            //open png file
//            Process a = Runtime.getRuntime().exec(openImage);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer2.close();
        }
    }

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
        File file = new File(findPath() + "\\ergasia.csv");
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


    public static String relationFinder(String name1, String name2) {
        Person p1 = getPersonByName(name1);
        Person p2 = getPersonByName(name2);
        String result = null;


        if (p1 != null && p2 != null) {
            //Father
            if (p1.getFather() != null) {
                if (p1.getFather().name.equals(p2.name)) {
                    if (p1.sex.equals("male")) {
                        System.out.println(p1.name + " is son of " + p2.name);
                        result = p1.name + " is son of " + p2.name;
                        isFound = true;
                    } else {
                        System.out.println(p1.name + " is daughter of " + p2.name);
                        result = p1.name + " is daughter of " + p2.name;
                        isFound = true;
                    }
                }
            }

            //Mother
            if (p1.getMother() != null) {
                if (p1.getMother().name.equals(p2.name)) {
                    if (p1.sex.equals("male")) {
                        System.out.println(p1.name + " is son of " + p2.name);
                        result = p1.name + " is son of " + p2.name;
                        isFound = true;
                    } else {
                        System.out.println(p1.name + " is daughter of " + p2.name);
                        result = p1.name + " is daughter of " + p2.name;
                        isFound = true;
                    }
                }
            }

            //Children
            for (Person child : p1.children) {
                if (child.name.equals(p2.name)) {
                    if (p1.sex.equals("male")) {
                        System.out.println(p1.name + " is father of " + p2.name);
                        result = p1.name + " is father of " + p2.name;
                        isFound = true;
                    } else {
                        System.out.println(p1.name + " is mother of " + p2.name);
                        result = p1.name + " is mother of " + p2.name;
                        isFound = true;
                    }
                }
            }

            //Married
            for (Person person : p1.married) {
                if (person.name.equals(p2.name)) {
                    System.out.println(p1.name + " is married with " + p2.name);
                    result = p1.name + " is married with " + p2.name;
                    isFound = true;
                }
            }

            ///Siblings
            for (Person sibling : p1.siblings) {
                if (sibling.name.equals(p2.name)) {
                    if (p1.sex.equals("male")) {
                        System.out.println(p1.name + " is brother of " + p2.name);
                        result = p1.name + " is brother of " + p2.name;
                        isFound = true;
                    } else {
                        System.out.println(p1.name + " is sister of " + p2.name);
                        result = p1.name + " is sister of " + p2.name;
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
                            result = p1.name + " is cousin with " + p2.name + " from " + p1.getFather().name + " and " + sibling.name;
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
                            result = p1.name + " is cousin with " + p2.name + " from " + p1.getMother().name + " and " + sibling.name;
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
                        result = p1.name + " eggonos toy " + p2.name;
                        isFound = true;
                    }
                    if (p1.getFather().getMother().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos ths " + p2.name);
                        result = p1.name + " eggonos ths " + p2.name;
                        isFound = true;
                    }
                } else if (p1.getFather().getMother() != null) {
                    if (p1.getFather().getFather().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos toy " + p2.name);
                        result = p1.name + " eggonos toy " + p2.name;
                        isFound = true;
                    }
                    if (p1.getFather().getMother().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos ths " + p2.name);
                        result = p1.name + " eggonos ths " + p2.name;
                        isFound = true;
                    }
                }
            }
            if (p1.getMother() != null) {
                if (p1.getMother().getMother() != null) {
                    if (p1.getMother().getMother().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos ths " + p2.name);
                        result = p1.name + " eggonos ths " + p2.name;
                        isFound = true;
                    }
                    if (p1.getMother().getFather().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos tou " + p2.name);
                        result = p1.name + " eggonos tou " + p2.name;
                        isFound = true;
                    }
                } else if (p1.getMother().getFather() != null) {
                    if (p1.getMother().getMother().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos ths " + p2.name);
                        result = p1.name + " eggonos ths " + p2.name;
                        isFound = true;
                    }
                    if (p1.getMother().getFather().name.equals(p2.name)) {
                        System.out.println(p1.name + " eggonos tou " + p2.name);
                        result = p1.name + " eggonos tou " + p2.name;
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
                                    result = p1.name + " grandpa of " + p2.name;
                                    isFound = true;
                                } else {
                                    System.out.println(p1.name + " grandmother of " + p2.name);
                                    result = p1.name + " grandmother of " + p2.name;
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
                                result = p1.name + " Unlce of " + p2.name;
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
                            result = p1.name + " anipsi of " + p2.name;
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
                            result = p1.name + " anipsi of " + p2.name;
                            isFound = true;
                        }
                    }
                }
            }

            //Not related
            if (!isFound) {
                System.out.println(p1.name + " and " + p2.name + " are not related!");
                result = p1.name + " and " + p2.name + " are not related!";
            }
        } else {
            if (p1 == null) {
                System.out.println("Cannot find person  " + name1);
                result = "Cannot find person " + name1;
            }
            if (p2 == null) {
                System.out.println("Cannot find person " + name2);
                if (result == null){
                    result = "Cannot find person " + name2;
                } else {
                    result += " and " + name2;
                }
            }
        }

        return result;

    }

    private static Person getPersonByName(String name) {

        for (Person person : allPersons) {
            if (person.name.equals(name)) {
                return person;
            }
        }

        return null;
    }


    @Test
    public void all_with_all() {

        //Some initialization for functions
        sb1 = new StringBuilder("digraph G { ");
        allPersons = new ArrayList<>();
        readFamilyTree();

        for (Person person1 : allPersons) {
            for (Person person2 : allPersons) {
                if (!person2.name.equals(person1.name)) {
                    relationFinder(person2.name,person1.name);
                }
            }
        }
    }
}
