package org.launchcode.cheesemvc.models;

public class Cheese {
    private String name;
    private String description;
    private static int nextId = 1;
    private int id;

    public Cheese(String aName, String aDescription){
        name = aName;
        description = aDescription;
        id = nextId++;
    }

    public Cheese(){
        this("","");
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String aName){
        name = aName;
    }

    public void setDescription(String aDescription){
        description = aDescription;
    }
}
