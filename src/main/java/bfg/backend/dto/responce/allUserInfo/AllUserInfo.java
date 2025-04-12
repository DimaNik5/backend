package bfg.backend.dto.responce.allUserInfo;

import java.util.List;

public record AllUserInfo(String name,
                          Long id,
                          Integer curDay,
                          Integer dayBeforeDelivery,
                          List<Resource> resources,
                          List<Link> links,
                          List<Module> modules) {}
