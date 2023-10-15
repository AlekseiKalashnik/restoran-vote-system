package com.votingsystem.web.vote;

import com.votingsystem.model.Vote;
import com.votingsystem.web.MatcherFactory;
import com.votingsystem.web.restaurant.RestaurantTestData;
import com.votingsystem.web.user.UserTestData;

import java.time.LocalDate;
import java.util.List;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant.address", "restaurant.dishes", "user");

    public static final int VOTE1_ID = 1;

    public static Vote vote1 = new Vote(VOTE1_ID, LocalDate.of(2020, 10, 10), RestaurantTestData.restaurant1, UserTestData.user);
    public static Vote vote2 = new Vote(VOTE1_ID + 1, LocalDate.of(2021, 11, 11), RestaurantTestData.restaurant2, UserTestData.user);
    public static Vote vote3 = new Vote(VOTE1_ID + 2, LocalDate.of(2022, 1, 1), RestaurantTestData.restaurant3, UserTestData.user);

    public static Vote vote4 = new Vote(VOTE1_ID + 3, LocalDate.now(), RestaurantTestData.restaurant1, UserTestData.user);

    public static List<Vote> votesToday = List.of(vote4);

    public static List<Vote> myVotes = List.of(vote1, vote2, vote3, vote4);

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), RestaurantTestData.restaurant2, UserTestData.admin);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID + 3, LocalDate.now(), RestaurantTestData.restaurant2, UserTestData.user);
    }
}
