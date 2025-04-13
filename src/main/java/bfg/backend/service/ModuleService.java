package bfg.backend.service;

import bfg.backend.dto.responce.optimality.Optimality;
import bfg.backend.repository.link.Link;
import bfg.backend.repository.link.LinkRepository;
import bfg.backend.repository.module.Module;
import bfg.backend.repository.module.ModuleRepository;
import bfg.backend.repository.resource.Resource;
import bfg.backend.repository.resource.ResourceRepository;
import bfg.backend.repository.user.User;
import bfg.backend.repository.user.UserRepository;
import bfg.backend.service.logic.Component;
import bfg.backend.service.logic.TypeModule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final LinkRepository linkRepository;
    private final ResourceRepository resourceRepository;

    public ModuleService(ModuleRepository moduleRepository, UserRepository userRepository, LinkRepository linkRepository, ResourceRepository resourceRepository) {
        this.moduleRepository = moduleRepository;
        this.userRepository = userRepository;
        this.linkRepository = linkRepository;
        this.resourceRepository = resourceRepository;
    }

    public void delete(Long idUser, Long id) {
        if(userRepository.findById(idUser).isEmpty()){
            throw new RuntimeException("Такого пользователя нет");
        }
        Optional<Module> optionalModule = moduleRepository.findById(id);
        if(optionalModule.isEmpty()){
            throw new RuntimeException("Такого модуля нет");
        }
        Module module = optionalModule.get();
        if(module.getId_user().equals(idUser)){
            moduleRepository.delete(module);
        }
        else {
            throw new RuntimeException("Такого модуля нет");
        }
    }

    public Module create(Module module) {
        // TODO проверка на возможность поставить?

        if(userRepository.findById(module.getId_user()).isEmpty()){
            throw new RuntimeException("Такого пользователя нет");
        }
        if(moduleRepository.findById(module.getId()).isPresent()){
            throw new RuntimeException("Такой модуль уже есть");
        }
        return moduleRepository.save(module);

    }

    public List<Optimality> getOptimality(Long idUser){
        Optional<User> optionalUser = userRepository.findById(idUser);
        if(optionalUser.isEmpty()){
            throw new RuntimeException("Нет такого пользователя");
        }
        User user = optionalUser.get();

        List<Module> modules = moduleRepository.findByIdUser(user.getId());
        List<Link> links = linkRepository.findByIdUser(user.getId());
        List<Resource> resources = resourceRepository.findByIdUser(user.getId());

        List<Optimality> optimalityList = new ArrayList<>(modules.size());
        for (int i = 0; i < modules.size(); i++) {
            Component component = TypeModule.values()[modules.get(i).getModule_type()].createModule(modules.get(i));
            Integer relief = component.getRelief();
            Integer rationality = component.getRationality(modules, links, resources);
            optimalityList.add(new Optimality(modules.get(i).getId(), relief, rationality));
        }

        return optimalityList;
    }
}
