package bfg.backend.service.logic.zones;

import java.util.ArrayList;
import java.util.List;

public class Zones {
    private static final List<Area> areas;
    private static final int LENGTH = 6;

    static {
        areas = new ArrayList<>(LENGTH);
        String[] names = {"Равнина1","Равнина2","Высота1","Высота2","Низина1","Низина2"};
        int[] il = {40, 40, 95, 95, 0, 0};
        String path = System.getProperty("user.dir");
        for (int i = 0; i < LENGTH; i++) {
            areas.add(new Area(il[i], path + "/" + names[i] + ".txt", names[i]));
        }
    }

    public static List<Area> getZones(){return areas;}

    public static int getLength(){return LENGTH;}
}
