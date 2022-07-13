package com.notification.services;


import com.notification.pojos.Category;
import com.notification.pojos.User;
import lombok.extern.log4j.Log4j2;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Log4j2
@Component
public class UserService {

    private final Random random = new Random();

    private final MockNeat mock = MockNeat.threadLocal();

    private final MockUnit<User> userGenerator = mock.filler(User::new)
            .setter(User::setFullName, mock.names().full())
            .setter(User::setId, mock.longs())
            .setter(User::setEmail, mock.emails());

    // mock
    public List<User> getUsersByFavouriteCategory(Category category) {
        log.trace("Retrieving users with favourite category '" + category.getName() + "'");

        List<User> userList = new LinkedList<User>();

        for (int i = 0; i < random.nextInt(3) + 1; i++) {
            userList.add(userGenerator.get());
        }

        return userList;
    }

}
