package bfg.backend.service.logic.modules;

import bfg.backend.repository.link.Link;
import bfg.backend.repository.module.Module;
import bfg.backend.repository.resource.Resource;
import bfg.backend.service.logic.Component;
import bfg.backend.service.logic.TypeModule;
import bfg.backend.service.logic.TypeResources;
import bfg.backend.service.logic.zones.Zones;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Landfill extends Module implements Component {
    private final static int h = 2;
    private final static int w = 2;
    private final static double MAX_ANGLE = 10;

    private long mass = 0; // кг поступаемого мусора


    public Landfill(Module module) {
        super(module.getId(), module.getId_user(), module.getId_zone(),
                module.getModule_type(), module.getX(), module.getY());
    }

    public Landfill(Long id, Long id_user, Integer id_zone, Integer module_type, Integer x, Integer y) {
        super(id, id_user, id_zone, module_type, x, y);
    }

    @Override
    public Integer getRelief() {
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
        boolean admin = false;
        int src = 0, other = 50;
        for (Module module : modules){
            if(Objects.equals(module.getModule_type(), getModule_type())){
                other = 0;
            } else if (module.getModule_type() == TypeModule.REPAIR_MODULE.ordinal()) {
                src = 50;
            }

            if(Objects.equals(module.getId_zone(), getId_zone())){
                Component c = TypeModule.values()[module.getModule_type()].createModule(module);
                if(c.cross(getX(), getY(), w, h)){
                    return null;
                }
                if(module.getModule_type() == TypeModule.ADMINISTRATIVE_MODULE.ordinal() ||
                        module.getModule_type() == TypeModule.LIVE_ADMINISTRATIVE_MODULE.ordinal()){
                    admin = true;
                }
            }
        }
        if(admin) return src + other;
        return null;
    }

    @Override
    public void getProduction(int idZone, List<Module> modules, List<Long> production) {
        long count = 0;

        for (Module module : modules) {
            if (module.getModule_type() == TypeModule.REPAIR_MODULE.ordinal()) {
                Component c = TypeModule.values()[module.getModule_type()].createModule(module);
                List<Long> t = new ArrayList<>(production.size());
                for (int i = 0; i < production.size(); i++) {
                    t.add(0L);
                }
                c.getConsumption(idZone, modules, t);
                count += t.get(TypeResources.MATERIAL.ordinal());
            }
        }
        mass = count;

        production.set(TypeResources.MATERIAL.ordinal(), production.get(TypeResources.MATERIAL.ordinal()) + (long) Math.ceil(count * 0.8));
        production.set(TypeResources.GARBAGE.ordinal(), production.get(TypeResources.MATERIAL.ordinal()) + (long) Math.floor(count * 0.2));
    }

    @Override
    public void getConsumption(int idZone, List<Module> modules, List<Long> consumption) {
        consumption.set(TypeResources.WT.ordinal(),consumption.get(TypeResources.WT.ordinal()) +  mass * 1000);
    }

    @Override
    public boolean cross(int x, int y, int w, int h) {
        return (x >= getX() && x <= getX() + Landfill.w && y >= getY() && y <= getY() + Landfill.h) ||
                (getX() >= x && getX() <= x + w && getY() >= y && getY() <= y + h);
    }
}
