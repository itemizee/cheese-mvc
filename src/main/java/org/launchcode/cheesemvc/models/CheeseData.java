package org.launchcode.cheesemvc.models;

import java.util.ArrayList;

public class CheeseData {

    static ArrayList<Cheese> cheeses = new ArrayList<>();

    //getAll
    public static ArrayList<Cheese> getAll(){
        return cheeses;
    }

    //add
    public static void add(Cheese c){
        cheeses.add(c);
    }
    //remove
    public static void remove(int id){
        Cheese cRemove = getById(id);
        cheeses.remove(cRemove);
    }

    //getById
    public static Cheese getById(int id){

        Cheese ch = null;
        for(Cheese c : cheeses)
            if(c.getId() == id)
                ch = c;

        return ch;

    }
}
