package com.notification.services;

import com.notification.pojos.Category;
import com.notification.pojos.Show;
import com.notification.pojos.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class NotificationService {

    @Autowired
    private UserService userService;    // mock service

    public void notifyShowCreation(Show show) {

        for (Category category : show.getCategories()) {
            for (User user : userService.getUsersByFavouriteCategory(category)) {
                notifyUser(user, show);
            }
        }
    }

    // mock notification
    private void notifyUser(User user, Show show) {
        // send email / push notification / etc.
        log.info("Show \"" + show.getName() + "\" added!. Notifying the user \"" + user.getFullName() + "\"");
    }
}
