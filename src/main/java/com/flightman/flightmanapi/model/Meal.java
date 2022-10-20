package com.flightman.flightmanapi.model;

import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "meals")
public class Meal {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID mealId;

    @Column(name = "mealName")
    private String mealName;

    @Column(name = "mealDesc")
    private String mealDesc;

    public Meal(){}

    public Meal(String mealName, String mealDesc){
        this.mealName = mealName;
        this.mealDesc = mealDesc;
    }

    public UUID getMealId() {
        return mealId;
    }

    public void setMealId(UUID mealId) {
        this.mealId = mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealDesc() {
        return mealDesc;
    }

    public void setMealDesc(String mealDesc) {
        this.mealDesc = mealDesc;
    }

    
}
