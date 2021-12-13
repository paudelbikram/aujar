package com.techpasya.aujar.wildcardtypeimpltest;

import java.util.HashMap;

public class Zoo {
    private HashMap<Long, ? extends Animal> animalContainers;
    public Zoo(){
        animalContainers = new HashMap<>();
    }

    public void setAnimalContainers(HashMap<Long,  ? extends Animal> containers){
        this.setAnimalContainers(containers);
    }



}
