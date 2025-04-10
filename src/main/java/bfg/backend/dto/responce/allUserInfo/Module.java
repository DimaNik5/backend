package bfg.backend.dto.responce.allUserInfo;

public class Module {
    private Integer idZone;
    private Integer moduleType;
    private Integer haveLink;
    private Integer x;
    private Integer y;

    public Module(Integer id_zone, Integer module_type, Integer have_link, Integer x, Integer y) {
        this.idZone = id_zone;
        this.moduleType = module_type;
        this.haveLink = have_link;
        this.x = x;
        this.y = y;
    }

    public Module() {}

    public Module(bfg.backend.repository.module.Module module){
        idZone = module.getId_zone();
        moduleType = module.getModule_type();
        haveLink = module.getHave_link();
        x = module.getX();
        y = module.getY();
    }

    public Integer getIdZone() {
        return idZone;
    }

    public void setIdZone(Integer idZone) {
        this.idZone = idZone;
    }

    public Integer getModuleType() {
        return moduleType;
    }

    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    public Integer getHaveLink() {
        return haveLink;
    }

    public void setHaveLink(Integer haveLink) {
        this.haveLink = haveLink;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
