package bfg.backend.service.logic;

import bfg.backend.repository.module.Module;
import bfg.backend.service.logic.modules.*;

public enum TypeModule {
    LIVE_MODULE_X(2, 9500, true, LiveModuleX::new),
    LIVE_MODULE_Y(1, 9500, true, LiveModuleY::new),
    LIVE_ADMINISTRATIVE_MODULE(2, 16000, true, LiveAdministrativeModule::new),
    SPORT_MODULE(1, 5000, true, SportModule::new),
    MEDICAL_MODULE(1, 5000, true, MedicalModule::new),
    PLANTATION(3, 42000, true, Plantation::new),
    RESEARCH_MODULE_PLANTATION(1, 5000, true, ResearchModulePlantation::new),
    RESEARCH_MODULE_MINE(1, 5000, true, ResearchModuleMine::new),
    RESEARCH_MODULE_TELESCOPE(1, 5000, true, ResearchModuleTelescope::new),
    RESEARCH_MODULE_TERRITORY(1, 5000, true, ResearchModuleTerritory::new),
    HALLWAY(1, 3500, true, Hallway::new),
    ADMINISTRATIVE_MODULE(2, 16000, false, AdministrativeModule::new),
    SOLAR_POWER_PLANT(1, 900, false, SolarPowerPlant::new),
    REPAIR_MODULE(2, 15000, false, RepairModule::new),
    COSMODROME(4, 900, false, Cosmodrome::new),
    COMMUNICATION_TOWER(1, 1000, false, CommunicationTower::new),
    LANDFILL(2, 15000, false, Landfill::new),
    LANDFILL_BIO(2, 15000, false, LandfillBio::new),
    MANUFACTURING_ENTERPRISE(1, 4200, false, ManufacturingEnterprise::new),
    MANUFACTURING_ENTERPRISE_FUEL(1, 4200, false, ManufacturingEnterpriseFuel::new),
    ASTRONOMICAL_SITE(2, 30000, false, AstronomicalSite::new),
    MINE_BASE(2, 20000, false, MineBase::new),
    WAREHOUSE_FOOD(2, 14000, false, WarehouseFood::new),
    WAREHOUSE_GASES(2, 14000, false, WarehouseGases::new),
    WAREHOUSE_FUEL(2, 14000, false, WarehouseFuel::new),
    WAREHOUSE_MATERIAL(2, 14000, false, WarehouseMaterial::new);

    private final int cost;
    private final int people;
    private final boolean live;

    private interface Constructor{Component create(Module module);};
    private final Constructor constructor;
    // Todo убрать название
    TypeModule(int people, int cost, boolean live, Constructor c){
        this.live = live;
        this.people = people;
        this.cost = cost;
        constructor = c;
    }

    public Component createModule(Module module){
        return constructor.create(module);
    }

    public Component createModule(Long idUser, Integer idZone, Integer x, Integer y){
        return constructor.create(new Module(-1L, idUser,idZone, this.ordinal(), x, y));
    }

    public int getCost() {
        return cost;
    }

    public boolean isLive() {
        return live;
    }

    public int getPeople() {
        return people;
    }
}
