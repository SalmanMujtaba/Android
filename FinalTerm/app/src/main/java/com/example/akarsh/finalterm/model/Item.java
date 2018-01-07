package com.example.akarsh.finalterm.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Akarsh on 12/12/2016.
 */

public class Item implements Serializable,Comparable<Item> {

    public Item() {
    }


    @Override
    public int compareTo(Item another) {
        return 0;
    }
}
