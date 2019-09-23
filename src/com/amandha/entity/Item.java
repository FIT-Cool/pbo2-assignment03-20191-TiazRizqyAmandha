package com.amandha.entity;

public class Item {
    private int ID;
    private String Name;
    private String DateExp;
    private Category category;

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }

    public String getName() {return Name;}

    public void setName(String name) {Name = name;}

    public String getDateExp() {return DateExp;}

    public void setDateExp(String dateExp) {DateExp = dateExp;}

    public Category getCategory() {return category;}

    public void setCategory(Category category) {this.category = category;}
}
