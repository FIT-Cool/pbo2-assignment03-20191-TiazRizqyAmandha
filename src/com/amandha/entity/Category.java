package com.amandha.entity;

public class Category {
    private int ID;
    private String Name;

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }

    public String getName() { return Name; }

    public void setName(String name) { Name = name; }

    @Override
    public String toString() { return Name; }
}
