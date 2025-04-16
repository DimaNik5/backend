package bfg.backend.service.logic.modules;

import bfg.backend.repository.link.Link;
import bfg.backend.repository.module.Module;
import bfg.backend.repository.resource.Resource;
import bfg.backend.service.logic.Component;
import bfg.backend.service.logic.zones.Zones;

import java.util.List;

public class Hallway extends Module implements Component {
    private final static int h = 1;
    private final static int w = 1;
    private final static double MAX_ANGLE = 10;

    public Hallway(Module module) {
        super(module.getId(), module.getId_user(), module.getId_zone(),
                module.getModule_type(), module.getX(), module.getY());
    }

    public Hallway(Long id, Long id_user, Integer id_zone, Integer module_type, Integer x, Integer y) {
        super(id, id_user, id_zone, module_type, x, y);
    }

    @Override
    public Integer getRelief(){
        int x = getX();
        int y = getY();
        double maxAngle = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                try {
                    maxAngle = Math.max(maxAngle, Zones.getZones().get(getId_zone()).getCells()[y + i][x + j].getAngle());
                }catch (ArrayIndexOutOfBoundsException e){
                    return null;
                }
            }
        }
        int res = (int) ((MAX_ANGLE - maxAngle) * 10);
        if(res <= 0) return null;
        return res;
    }

    @Override
    public Integer getRationality(List<Module> modules, List<Link> links, List<Resource> resources) {
        return 0;
    }

    @Override
    public void getProduction(int idZone, List<Module> modules, List<Long> production) {

    }

    @Override
    public void getConsumption(int idZone, List<Module> modules, List<Long> consumption) {

    }

    @Override
    public boolean cross(int x, int y, int w, int h) {
        return (x >= getX() && x <= getX() + Hallway.w && y >= getY() && y <= getY() + Hallway.h) ||
                (getX() >= x && getX() <= x + w && getY() >= y && getY() <= y + h);
    }
}
