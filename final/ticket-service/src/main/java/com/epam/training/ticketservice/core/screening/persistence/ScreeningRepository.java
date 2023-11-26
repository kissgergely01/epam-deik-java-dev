package com.epam.training.ticketservice.core.screening.persistence;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    Optional<Screening> findByScreeningDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
            LocalDateTime startDate, LocalDateTime endDate, String roomName);

    Optional<Screening> findByScreeningDateGreaterThanEqualAndScreeningDateLessThanEqualAndRoom_Name(
            LocalDateTime startDate, LocalDateTime endDate, String roomName);

    Optional<Screening> findByScreeningEndDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
            LocalDateTime startDate, LocalDateTime endDate, String roomName);

    Optional<Screening> findByScreeningDateLessThanEqualAndScreeningEndDateGreaterThanEqualAndRoom_Name(
            LocalDateTime startDate, LocalDateTime endDate, String roomName);

    Optional<Screening> findByScreeningEndDateGreaterThanEqual(LocalDateTime endDate);

    @Transactional
    void deleteByMovieAndRoom_NameAndDate(Movie movie, String roomName, LocalDateTime screeningDate);

}
