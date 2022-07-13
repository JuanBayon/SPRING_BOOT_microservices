package com.showcatalog.services;

import com.showcatalog.entities.Show;
import com.showcatalog.exceptions.ExceptionMessages;
import com.showcatalog.exceptions.ShowException;
import com.showcatalog.repositories.ShowRepository;
import com.showcatalog.repositories.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ShowService {

    @Autowired
    ShowRepository showRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public void updateShowScore(Long showId, Integer score) throws ShowException {
        log.trace("updateShowScore");
        log.debug("score " + score);
        log.debug("showId " + showId);
        try {
            Show show = showRepository.findById(showId)
                        .orElseThrow(()-> new ShowException(ExceptionMessages.NO_SHOW_FOUND));
            show.updateScore(score);
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
    }

    public void LockSeatsAmount(Long id, Integer amount) throws ShowException {
        log.trace("LockSeatsAmount");
        log.debug("amount " + amount);
        try {
            Show show = showRepository.findById(id).orElseThrow(() -> new ShowException(ExceptionMessages.NO_SHOW_FOUND));
            show.setCapacity(show.getCapacity() - amount);
            showRepository.save(show);
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
    }

}
