package com.showcatalog.controllers;

import com.showcatalog.entities.Show;
import com.showcatalog.exceptions.ExceptionMessages;
import com.showcatalog.exceptions.ShowException;
import com.showcatalog.kafka.KafkaConstants;
import com.showcatalog.entities.Performance;
import com.showcatalog.repositories.CategoryRepository;
import com.showcatalog.repositories.ShowRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/shows")
public class ShowController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private KafkaTemplate<String, Show> kafkaTemplate;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Show> getAllShows() throws ShowException {
        log.trace("getAllShows");
        List<Show> shows;
        try {
            shows = showRepository.findAll();
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
        return shows;
    }

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    public Show getShowByName(@RequestParam String name) throws ShowException {
        log.trace("getShowByName");
        log.debug("Name " + name);
        Show show;
        try {
            show = showRepository.getShowByNameLike(name);
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
        return show;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Show getShowById(@PathVariable Long id) throws ShowException {
        log.trace("getShowById");
        log.debug("Id " + id);
        Show show;
        try {
            show = showRepository.findById(id).orElseThrow(()-> new ShowException(ExceptionMessages.NO_SHOW_FOUND));
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
        return show;
    }

    @GetMapping("/{id}/performances")
    @ResponseStatus(HttpStatus.OK)
    public List<Performance> getPerformances(@PathVariable Long id) throws ShowException {
        log.trace("getPerformances");
        log.debug("id " + id);
        List<Performance> performances;
        try {
            Show show = showRepository.findById(id).orElseThrow(()-> new ShowException(ExceptionMessages.NO_SHOW_FOUND));
            performances = show.getPerformances();
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
        return performances;
    }

    @GetMapping("/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Show> apiGetShowsByCategory(@PathVariable Long id) throws ShowException {
        log.trace("apiGetAllShowsByCategory");
        log.debug("CategoryId " + id);
        List<Show> shows;
        try {
            shows = categoryRepository.getById(id).getShows();
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
        return shows;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void apiCreateShow(@RequestBody Show show) throws ShowException {
        log.trace("apiCreateShow");
        log.debug("Show " + show);
        try {
            showRepository.save(show);
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
        kafkaTemplate.send(KafkaConstants.SHOW_TOPIC +
                KafkaConstants.SEPARATOR + KafkaConstants.COMMAND_ADD, show);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void apiUpdateShow(@PathVariable Long id, @RequestBody Show show) throws ShowException {
        log.trace("apiUpdateShow");
        log.debug("id " + id);
        log.debug("show " + show);
        try {
            Show showUpdated = showRepository.findById(id)
                    .orElseThrow(()-> new ShowException(ExceptionMessages.NO_SHOW_FOUND));
            showUpdated.setCapacity(show.getCapacity());
            showUpdated.setImage(show.getImage());
            showUpdated.setCategories(show.getCategories());
            showUpdated.setDescription(show.getDescription());
            showUpdated.setDuration(show.getDuration());
            showUpdated.setName(show.getName());
            showUpdated.setOnSaleDate(show.getOnSaleDate());
            showUpdated.setPerformances(show.getPerformances());
            showUpdated.setPrice(show.getPrice());
            showUpdated.setScore(show.getScore());
            showUpdated.setStatus(show.getStatus());
            showRepository.save(showUpdated);
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
    }

    @PatchMapping("/{id}/image")
    @ResponseStatus(HttpStatus.OK)
    public void apiAddImage(@PathVariable Long id, @RequestBody MultipartFile image) throws ShowException {
        log.trace("apiAddImage");
        log.debug("id " + id);
        log.debug("image" + image);
        Show show;
        try {
            show = showRepository.getById(id);
            show.setImage(image.getBytes());
        } catch (IOException e) {
            throw new ShowException(e.getMessage());
        }
        showRepository.save(show);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void apiCreatePerformance(@PathVariable Long id, @RequestBody Performance performance) throws ShowException {
        log.trace("apiCreatePerformance");
        log.debug("id " + id);
        log.debug("performance " + performance);
        try {
            Show show = showRepository.findById(id).orElseThrow(()-> new ShowException(ExceptionMessages.NO_SHOW_FOUND));
            show.addPerformance(performance);
            showRepository.save(show);
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void apiDeleteShow(@PathVariable Long id) throws ShowException {
        log.trace("apiDeleteShow");
        log.debug("id " + id);
        try {
            showRepository.deleteById(id);
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/performance")
    @ResponseStatus(HttpStatus.OK)
    public void apiDeletePerformance(@PathVariable Long id, @RequestParam Integer index) throws ShowException {
        log.trace("apiDeletePerformance");
        log.debug("index " + index);
        try {
            Show show = showRepository.findById(id).orElseThrow(()-> new ShowException(ExceptionMessages.NO_SHOW_FOUND));
            show.deletePerformance(index);
            showRepository.save(show);
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
    }

    @PostMapping("/{id}/open")
    @ResponseStatus(HttpStatus.OK)
    public void apiOpenShow(@PathVariable Long id) throws ShowException {
        log.trace("apiOpenShow");
        log.debug("id " + id);
        try {
            Show show = showRepository.findById(id).orElseThrow(() -> new ShowException(ExceptionMessages.NO_SHOW_FOUND));
            show.openShow();
            showRepository.save(show);
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
    }

    @PostMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void apiCancelShow(@PathVariable Long id) throws ShowException {
        log.trace("apiCancelShow");
        log.debug("id " + id);
        try {
            Show show = showRepository.findById(id).orElseThrow(() -> new ShowException(ExceptionMessages.NO_SHOW_FOUND));
            show.cancelShow();
            showRepository.save(show);
        } catch (Exception e) {
            throw new ShowException(e.getMessage());
        }
    }

    @ExceptionHandler(value = ShowException.class)
    public ResponseEntity<Object> handleRest(Exception e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

}
