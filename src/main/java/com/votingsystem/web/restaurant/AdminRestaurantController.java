package com.votingsystem.web.restaurant;

import com.votingsystem.model.Restaurant;
import com.votingsystem.repository.RestaurantRepository;
import com.votingsystem.to.RestaurantTo;
import com.votingsystem.util.RestaurantUtil;
import com.votingsystem.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "/api/admin/restaurants";
    private final RestaurantRepository repository;

    private final UniqueNameValidator validator;

    public AdminRestaurantController(RestaurantRepository repository, UniqueNameValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get{}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    @Override
    @GetMapping("/{id}/with-today-dishes")
    @Transactional
    public ResponseEntity<Restaurant> getWithTodayDish(@PathVariable int id) {
        return super.getWithTodayDish(id);
    }

    @GetMapping("/{id}/history")
    @Transactional
    public ResponseEntity<Restaurant> getWithDishesWithSomeDate(@PathVariable int id, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("getWithDishesWithSomeDate for rest{} and date{}", id, date);
        ValidationUtil.checkEmpty(repository.getWithDishesWithDate(id, date));
        return ResponseEntity.of(repository.getWithDishesWithDate(id, date));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll();
    }

    @Override
    @GetMapping("/all-with-dishes")
    @Cacheable
    public List<Restaurant> getAllWithTodayDishes() {
        return super.getAllWithTodayDishes();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("create{}", restaurantTo);
        ValidationUtil.checkNew(restaurantTo);
        Restaurant created = repository.save(RestaurantUtil.createNewFromTo(restaurantTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        log.info("update restaurant with id{}", id);
        ValidationUtil.assureIdConsistent(restaurantTo, id);
        Restaurant restaurant = repository.getById(id);
        repository.save(RestaurantUtil.updateFromTo(restaurant, restaurantTo));
    }
}
