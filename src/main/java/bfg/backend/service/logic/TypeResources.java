package bfg.backend.service.logic;

public enum TypeResources {
    H2O(10),
    FUEL(1),
    FOOD(1),
    WT(1),
    O2(1),
    CO2(1),
    GARBAGE(1),
    MATERIAL(1);

    private final long startCount;

    TypeResources(int startCount) {
        this.startCount = startCount;
    }

    public long getStartCount() {
        return startCount;
    }
}
