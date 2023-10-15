package com.votingsystem.web.vote;

import com.votingsystem.model.Vote;
import com.votingsystem.util.validation.ValidationUtil;
import com.votingsystem.model.Restaurant;
import com.votingsystem.repository.RestaurantRepository;
import com.votingsystem.repository.UserRepository;
import com.votingsystem.repository.VoteRepository;
import com.votingsystem.web.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/votes";

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public VoteController(VoteRepository voteRepository, RestaurantRepository restaurantRepository,
                          UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/my-votes")
    public List<Vote> getAllMyVotes() {
        int userId = SecurityUtil.authId();
        log.info("getAllMyVotes for user{}", userId);
        return voteRepository.getAllById(userId);
    }

    @GetMapping("/today-votes")
    public ResponseEntity<Vote> getTodayVote() {
        int userId = SecurityUtil.authId();
        log.info("getTodayVote for user{}", userId);
        return ResponseEntity.of(voteRepository.findByIdAndDate(userId, LocalDate.now()));
    }

    @GetMapping("/all-today-votes")
    public List<Vote> getAllToday() {
        log.info("getAllTodayVotes");
        return voteRepository.findAllByDate(LocalDate.now());
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<Vote> create(Integer restaurantId) {
        int userId = SecurityUtil.authId();
        Vote vote = getTodayVote().getBody();
        ValidationUtil.assureIdConsistent(restaurantRepository.getById(restaurantId), restaurantId);
        ValidationUtil.checkForNull(vote);
        Vote created = new Vote(LocalDate.now(), restaurantRepository.getById(restaurantId), userRepository.getById(userId));
        voteRepository.save(created);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(Integer restaurantId) {
        Vote vote = getTodayVote().getBody();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        ValidationUtil.checkForNotNull(vote);
        ValidationUtil.checkTime();
        ValidationUtil.assureIdConsistent(restaurant, restaurantId);
        vote.setRestaurant(restaurant);
        voteRepository.save(vote);
    }

}
