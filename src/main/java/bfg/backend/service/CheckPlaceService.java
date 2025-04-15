package bfg.backend.service;

import bfg.backend.dto.request.modulePlace.ModulePlace;
import bfg.backend.dto.responce.checkPlace.CheckedPlace;
import bfg.backend.repository.link.*;
import bfg.backend.repository.module.Module;
import bfg.backend.repository.module.ModuleRepository;
import bfg.backend.repository.resource.*;
import bfg.backend.repository.user.*;
import bfg.backend.service.logic.Component;
import bfg.backend.service.logic.TypeModule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheckPlaceService {

    private final UserRepository userRepository;
    private final LinkRepository linkRepository;
    private final ModuleRepository moduleRepository;
    private final ResourceRepository resourceRepository;

    public CheckPlaceService(UserRepository userRepository, LinkRepository linkRepository, ModuleRepository moduleRepository, ResourceRepository resourceRepository) {
        this.userRepository = userRepository;
        this.linkRepository = linkRepository;
        this.moduleRepository = moduleRepository;
        this.resourceRepository = resourceRepository;
    }

    public CheckedPlace check(ModulePlace modulePlace){

        Optional<User> optionalUser = userRepository.findById(modulePlace.idUser());
        if(optionalUser.isEmpty()){
            throw new RuntimeException("Такого пользователя нет");
        }
        User user = optionalUser.get();
        if(!user.getLive()){
            throw new RuntimeException("Данный пользоваель завершил колнизацию");
        }

        List<Module> modules = moduleRepository.findByIdUser(user.getId());
        List<Link> links = linkRepository.findByIdUser(user.getId());
        List<Resource> resources = resourceRepository.findByIdUser(user.getId());

        Component component = TypeModule.values()[modulePlace.typeModule()].
                createModule(user.getId(), modulePlace.idZone(), modulePlace.x(), modulePlace.y());

        Integer relief = component.getRelief();
        Integer rationality = component.getRationality(modules, links, resources);

        return new CheckedPlace(relief != null && rationality != null, relief, rationality);
    }
}
