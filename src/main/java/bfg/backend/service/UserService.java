package bfg.backend.service;

import bfg.backend.dto.responce.allUserInfo.AllUserInfo;
import bfg.backend.mapping.MappingToResponse;
import bfg.backend.repository.link.*;
import bfg.backend.repository.module.Module;
import bfg.backend.repository.module.ModuleRepository;
import bfg.backend.repository.resource.*;
import bfg.backend.repository.user.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LinkRepository linkRepository;
    private final ModuleRepository moduleRepository;
    private final ResourceRepository resourceRepository;

    public UserService(UserRepository userRepository, LinkRepository linkRepository, ModuleRepository moduleRepository, ResourceRepository resourceRepository) {
        this.userRepository = userRepository;
        this.linkRepository = linkRepository;
        this.moduleRepository = moduleRepository;
        this.resourceRepository = resourceRepository;
    }

    public AllUserInfo find(String email, String password){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return null;
        }
        User user = optionalUser.get();
        if(!user.getPassword().equals(password)){
            return null;
        }

        List<Module> modules = moduleRepository.findByIdUser(user.getId());
        List<Link> links = linkRepository.findByIdUser(user.getId());
        List<Resource> resources = resourceRepository.findByIdUser(user.getId());

        return MappingToResponse.mapToAllUserInfo(user, modules, links, resources);
    }

    /*public List<User> createUser(){
        return userRepository.findAll();
    }

    public User create(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            throw new IllegalStateException("Пользователь уже есть");
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new IllegalStateException("Пользователь с этим id нет");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, String email, String name) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new IllegalStateException("Пользователь с этим id нет");
        }
        User user = optionalUser.get();
        if(email != null && !email.equals(user.getEmail())){
            Optional<User> optionalUser1 = userRepository.findByEmail(email);
            if(optionalUser1.isPresent()){
                throw new IllegalStateException("Пользователь уже есть");
            }
            user.setEmail(email);
        }

        if(name != null && !name.equals(user.getName())){
            user.setName(name);
        }

       // userRepository.save(user);

    }*/
}
