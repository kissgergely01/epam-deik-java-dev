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
            LocalDateTime screeningDate, LocalDateTime screeningEndDate, String name);

    Optional<Screening> findByScreeningDateGreaterThanEqualAndScreeningDateLessThanEqualAndRoom_Name(
            LocalDateTime screeningDate, LocalDateTime screeningEndDate, String name);

    Optional<Screening> findByScreeningEndDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
            LocalDateTime screeningDate, LocalDateTime screeningEndDate, String name);

    Optional<Screening> findByScreeningDateLessThanEqualAndScreeningEndDateGreaterThanEqualAndRoom_Name(
            LocalDateTime screeningDate, LocalDateTime screeningEndDate, String name);

    Optional<Screening> findByScreeningEndDateGreaterThanEqual(LocalDateTime screeningEndDate);


    @Transactional
    void deleteByTitleAndRoom_NameAndScreeningDate(Movie title, String name1, LocalDateTime screeningDate);

}
