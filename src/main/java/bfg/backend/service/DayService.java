package bfg.backend.service;

import bfg.backend.dto.responce.day.ChangeDay;
import bfg.backend.repository.resource.Resource;
import bfg.backend.repository.resource.ResourceRepository;
import bfg.backend.repository.user.User;
import bfg.backend.repository.user.UserRepository;
import bfg.backend.service.logic.TypeModule;
import bfg.backend.service.logic.TypeResources;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static bfg.backend.service.logic.Constants.DAYS_DELIVERY;

@Service
public class DayService {

    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;

    public DayService(UserRepository userRepository, ResourceRepository resourceRepository) {
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
    }

    public ChangeDay addDay(Long idUser){
        Optional<User> optionalUser = userRepository.findById(idUser);
        if(optionalUser.isEmpty()){
            throw new RuntimeException("Такого пользователя нет");
        }
        User user = optionalUser.get();
        if(!user.getLive()){
            throw new RuntimeException("Данный пользоваель завершил колнизацию");
        }

        user.setCurrent_day(user.getCurrent_day() + 1);
        boolean delivery = user.getDays_before_delivery() == 1;
        if(delivery) user.setDays_before_delivery(DAYS_DELIVERY);
        else user.setDays_before_delivery(user.getDays_before_delivery() - 1);

        List<Resource> resources = resourceRepository.findByIdUser(idUser);
        resources.sort(Resource::compareTo);
        List<Long> diffResources = new ArrayList<>(resources.size());
        Long diff;
        for (Resource resource : resources) {
            diff = resource.getProduction() - resource.getConsumption();
            if (delivery && diff < 0) {
                diff -= diff * DAYS_DELIVERY + (resource.getCount() + diff * 5);
            }
            diffResources.add(diff);
            resource.setCount(resource.getCount() + diff);
            resource.setSum_production(resource.getSum_production() + resource.getProduction());
            resource.setSum_consumption(resource.getSum_consumption() + resource.getConsumption());
        }

        Boolean live = resources.stream().allMatch(e -> e.getCount() > 0);
        resourceRepository.saveAll(resources);
        user.setLive(live);
        userRepository.save(user);
        return new ChangeDay(live, diffResources);
    }
}
