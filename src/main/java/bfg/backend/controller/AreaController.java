package bfg.backend.controller;


import bfg.backend.dto.responce.areaInfo.Area;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "area")
public class AreaController {

    @GetMapping
    public List<Area> getUser(){
        return Area.getZones();
    }
}
