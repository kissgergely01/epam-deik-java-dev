package com.epam.training.ticketservice.core.screening.persistence;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    Optional<Screening> findBy_ScreeningDate_GreaterThan_ScreeningEndDate_LessThanEqual_And_Room_Name(
            LocalDateTime startDate, LocalDateTime endDate, String roomName);

    Optional<Screening> findBy_ScreeningDate_GreaterThanEqual_ScreeningDate_LessThanEqual_And_Room_Name(
            LocalDateTime startDate, LocalDateTime endDate, String roomName);

    Optional<Screening> findBy_ScreeningEndDate_GreaterThanEqual_ScreeningEndDate_LessThanEqual_And_Room_Name(
            LocalDateTime startDate, LocalDateTime endDate, String roomName);

    Optional<Screening> findBy_ScreeningDate_LessThanEqual_ScreeningEndDate_GreaterThanEqual_And_Room_Name(
            LocalDateTime startDate, LocalDateTime endDate, String roomName);



    @Transactional
    void deleteByMovieAndRoom_NameAndDate(Movie movie, String roomName, LocalDateTime screeningDate);

}
