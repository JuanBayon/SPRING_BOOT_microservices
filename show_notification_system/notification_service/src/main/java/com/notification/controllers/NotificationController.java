package com.notification.controllers;

import com.notification.pojos.Show;
import com.notification.services.NotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    //Method developed only for testing purposes
    @PostMapping("/")
    void notifyTest(@RequestBody Show show) {
        log.trace("notifyTest");
        notificationService.notifyShowCreation(show);
    }

}
