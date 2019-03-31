package com.utsav.authservice.web;

import com.utsav.authservice.model.dtos.AppDto;
import com.utsav.authservice.services.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/apps")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public ResponseEntity<List<AppDto>> getAllApps() {
        return new ResponseEntity<>(appService.getAllApps(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AppDto> registerApp(@RequestBody AppDto appDto) {
        return new ResponseEntity<>(appService.registerApp(appDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AppDto> getAppDetails(@PathVariable("id") long id) {
        return new ResponseEntity<>(appService.getAppDetails(id), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<AppDto> updateApp(@RequestBody AppDto appDto) {
        return new ResponseEntity<>(appService.updateApp(appDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteApp(@PathVariable("id") long id, @RequestParam(required = false, defaultValue = "false") boolean permanent) {
        appService.deleteApp(id, permanent);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
