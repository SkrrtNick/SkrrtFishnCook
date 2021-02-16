package com.skrrtnick.data;

import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;

public enum Locations {
     LUMBRIDGE_BANK("Lumbridge Bank", new Area(new Tile(3207, 3219, 2), new Tile(3210, 3218, 2))),
     FISHING_SPOT("Fishing spot", new Area( new Tile(3239,3155), new Tile(3244,3151))),
     STOVE("Stove", new Area(new Tile(3237,3195),new Tile(3230,3198)));

     private Area area;
     private String name;

    public String getName() {
        return name;
    }

    Locations(String name, Area area) {
         this.name = name;
         this.area = area;
     }
    public Area getArea() {
        return area;
    }
}
