package bfg.backend.controller;

import bfg.backend.dto.responce.allUserInfo.AllUserInfo;
import bfg.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "singin")
public class UserSingInController {

    private final UserService userService;

    public UserSingInController(UserService findUserService) {
        this.userService = findUserService;
    }
/*
    @GetMapping
    public List<User> getUser(){
        return userService.createUser();
    }*/

    @PostMapping
    public AllUserInfo find(@RequestBody String email, @RequestBody String password){
        return userService.find(email, password);
    }

    /*@DeleteMapping(path = "{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PutMapping(path = "{id}")
    public void update(
            @PathVariable Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false)String name){
        userService.update(id, email, name);
    }*/
}
