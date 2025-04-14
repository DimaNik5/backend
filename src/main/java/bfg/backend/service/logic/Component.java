package bfg.backend.service.logic;

import bfg.backend.repository.link.Link;
import bfg.backend.repository.module.Module;
import bfg.backend.repository.resource.Resource;

import java.util.List;

public interface Component {
    Integer getRelief();

    Integer getRationality(List<Module> modules, List<Link> links, List<Resource> resources);

    Long getProductionInZone(int idZone, List<Module> modules);

    Long getConsumptionInZone(int idZone, List<Module> modules);
}
