package com.votingsystem.repository;

import com.votingsystem.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("select v from Vote v where v.user.id = ?1")
    List<Vote> getAllById(int userId);

    @Query("select v from Vote v where v.user.id = ?1 and v.date = ?2")
    Optional<Vote> findByIdAndDate(int userId, @NotNull LocalDate date);

    @Query("select v from Vote v where v.date = ?1 order by v.restaurant.name")
    List<Vote> findAllByDate(LocalDate date);
}