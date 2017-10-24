package com.example.salman.inclass07;

import java.util.Comparator;

/**
 * Created by KashishSyeda on 10/15/2017.
 */

public class Music {
    int id;
    String name;
    String icon;
    String price;
    String bigIcon;

    public String getBigIcon() {
        return bigIcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBigIcon(String bigIcon) {
        this.bigIcon = bigIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public static Comparator<Music> MusicComparatorAsc
            = new Comparator<Music>() {

        @Override
        public int compare(Music o1, Music o2) {
            Float price = Float.parseFloat(o1.getPrice().substring(1,o1.getPrice().length()));
            Float price2 = Float.parseFloat(o2.getPrice().substring(1,o2.getPrice().length()));
            Float sub = price2 - price;
            if(sub>0f) return 1;
            else if(sub<0f) return -1;
            else return 0;
        }
    };

    public static Comparator<Music> MusicComparatorDesc
            = new Comparator<Music>() {

        @Override
        public int compare(Music o1, Music o2) {
            Float price = Float.parseFloat(o1.getPrice().substring(1,o1.getPrice().length()));
            Float price2 = Float.parseFloat(o2.getPrice().substring(1,o2.getPrice().length()));
            Float sub = price - price2;
            if(sub>0f) return 1;
            else if(sub<0f) return -1;
            else return 0;
        }
    };


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Music that = (Music) o;
        if (!name.equals(that.name)) return false;
        if (!price.equals(that.price)) return false;
        return true;
    }
}
