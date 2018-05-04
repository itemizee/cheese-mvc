package org.launchcode.cheesemvc.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
public class Cheese {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @NotNull
    @Size(min=1, message = "Description must not be empty")
    private String description;

    @Min(value=1, message="Must be 1-5")
    @Max(value=5, message="Must be 1-5")
    private Integer rating;

    @ManyToOne
    private Category category;

    @ManyToMany(mappedBy = "cheeses")
    private List<Menu> menus;


    public Cheese(String aName, String aDescription, Integer aRating, Category aCat){
        name = aName;
        description = aDescription;
        rating = aRating;
        category = aCat;
    }

    public Cheese(){
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }


    public void setName(String aName){
        name = aName;
    }

    public void setDescription(String aDescription){
        description = aDescription;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
