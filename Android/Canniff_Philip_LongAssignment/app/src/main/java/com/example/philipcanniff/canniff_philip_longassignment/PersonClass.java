package com.example.philipcanniff.canniff_philip_longassignment;

import java.io.Serializable;

/**
 * Created by philipcanniff on 9/18/15.
 */
public class PersonClass implements Serializable {

    String name;
    String last;
    int  years;
    String skill1;
    String skill2;

    public PersonClass(String name, String last, int years, String skill1, String skill2) {
        this.name = name;
        this.last = last;
        this.years = years;
        this.skill1 = skill1;
        this.skill2 = skill2;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getSkill1() {
        return skill1;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    @Override
    public String toString() {

        String info = name + "/" + last;

        return info;
    }
}
