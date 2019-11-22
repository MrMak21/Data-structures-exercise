package com.slg;

import java.util.ArrayList;

public class Person {

    String name;
    String sex;
    Person Father;
    Person Mother;

    ArrayList<Person> children;
    ArrayList<Person> siblings;
    ArrayList<Person> married;

    public Person(String name, String sex) {
        this.name = name;
        this.sex = sex;
        this.Father = null;
        this.Mother = null;
        siblings = new ArrayList<>();
        children = new ArrayList<>();
        married = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public ArrayList<Person> getChildren() {
        return children;
    }

    public ArrayList<Person> getSiblings() {
        return siblings;
    }

    public ArrayList<Person> getMarried() {
        return married;
    }

    public void addChildren(Person p) {
        this.children.add(p);
    }

    public void addSibling(Person p) {
        this.siblings.add(p);
    }

    public void addMarried(Person p) {
        this.married.add(p);
    }

    public Person getFather() {
        return Father;
    }

    public void setFather(Person father) {
        Father = father;
    }

    public Person getMother() {
        return Mother;
    }

    public void setMother(Person mother) {
        Mother = mother;
    }

    @Override
    public String toString() {
        return name + " (" + sex + ")";
    }
}
