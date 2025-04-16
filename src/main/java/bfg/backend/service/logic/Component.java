package bfg.backend.service.logic;

import bfg.backend.repository.link.Link;
import bfg.backend.repository.module.Module;
import bfg.backend.repository.resource.Resource;

import java.util.List;

public interface Component {

    Integer getRelief();

    Integer getRationality(List<Module> modules, List<Link> links, List<Resource> resources);

    void getProduction(int idZone, List<Module> modules, List<Long> production);

    void getConsumption(int idZone, List<Module> modules, List<Long> consumption);

    private int countPeople(List<Module> modules){
        return Math.toIntExact(modules.stream().filter(e -> e.getModule_type() == TypeModule.LIVE_MODULE_Y.ordinal() ||
                e.getModule_type() == TypeModule.LIVE_MODULE_X.ordinal()).count());
    }
}
