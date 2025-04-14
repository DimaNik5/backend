package bfg.backend.service;

import bfg.backend.repository.link.Link;
import bfg.backend.repository.link.LinkRepository;
import bfg.backend.repository.user.User;
import bfg.backend.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public LinkService(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    public void delete(Link link) {
        Optional<User> optionalUser = userRepository.findById(link.getPrimaryKey().getId_user());
        if(optionalUser.isEmpty()){
            throw new RuntimeException("Такого пользователя нет");
        }
        User user = optionalUser.get();
        if(!user.getLive()){
            throw new RuntimeException("Данный пользоваель завершил колнизацию");
        }
        //linkRepository.deleteById(new Link.PrimaryKey(type, idUser, idZone1, idZone2));
        if(linkRepository.findById(link.getPrimaryKey()).isEmpty()){
            throw new RuntimeException("Такой связи нет");
        }
        linkRepository.delete(link);
    }

    public Link create(Link link) {
        Optional<User> optionalUser = userRepository.findById(link.getPrimaryKey().getId_user());
        if(optionalUser.isEmpty()){
            throw new RuntimeException("Такого пользователя нет");
        }
        User user = optionalUser.get();
        if(!user.getLive()){
            throw new RuntimeException("Данный пользоваель завершил колнизацию");
        }
        if(linkRepository.findById(link.getPrimaryKey()).isPresent()){
            throw new RuntimeException("Такая связь уже есть");
        }
        return linkRepository.save(link);
    }
}
