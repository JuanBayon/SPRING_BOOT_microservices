package com.showcatalog.kafka;

import com.showcatalog.pojos.Order;
import com.showcatalog.pojos.Review;
import com.showcatalog.services.ShowService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class KafkaClassListener {

    @Autowired
    private ShowService showService;

    @Autowired
    private KafkaTemplate<String, Exception> kafkaTemplate;

    @KafkaListener(topics = KafkaConstants.SCORE_TOPIC + KafkaConstants.SEPARATOR + KafkaConstants.COMMAND_UPDATE,
                    groupId = "group-1", id="reviewListener", containerFactory="kafkaListenerContainerFactoryReview")
    void updateScore(Review review) {
        log.trace("updateScore");
        log.debug("review " + review);
        try {
            showService.updateShowScore(review.getShowId(), review.getScore());
        } catch (Exception e) {
            kafkaTemplate.send(KafkaConstants.SHOW_TOPIC +
                    KafkaConstants.SEPARATOR + KafkaConstants.COMMAND_EXCEPTION, e);
        }
    }

    @KafkaListener(topics = KafkaConstants.SEATS_TOPIC + KafkaConstants.SEPARATOR + KafkaConstants.COMMAND_UPDATE,
            groupId = "group-1", id="orderListener", containerFactory="kafkaListenerContainerFactoryOrder")
    void lockSeats(Order order) {
        log.trace("lockSeats");
        log.debug("order " + order);
        try {
            showService.LockSeatsAmount(order.getReservation().getShowId(), order.getReservation().getNumberOfSeats());
        } catch (Exception e) {
            kafkaTemplate.send(KafkaConstants.SHOW_TOPIC +
                    KafkaConstants.SEPARATOR + KafkaConstants.COMMAND_EXCEPTION, e);
        }
    }
}
