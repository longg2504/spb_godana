package com.godana.repository.place;

import com.godana.domain.dto.place.PlaceCountDTO;
import com.godana.domain.dto.place.PlaceDTO;
import com.godana.domain.dto.placeAvatar.PlaceAvatarDTO;
import com.godana.domain.dto.report.I6MonthAgoReportDTO;
import com.godana.domain.dto.report.IReportDTO;
import com.godana.domain.dto.report.IYearReportDTO;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.PlaceAvatar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
    @Query("SELECT NEW com.godana.domain.dto.place.PlaceDTO (" +
            "p.id, " +
            "p.title, " +
            "p.content, " +
            "p.longitude, " +
            "p.latitude, " +
            "p.category, " +
            "p.locationRegion, " +
            "p.contact, " +
            "avg(r.rating), " +
            "CASE WHEN COUNT(r.id) IS NULL THEN 0 ELSE COUNT(r.id) END" +
            ") " +
            "FROM Place AS p " +
            "LEFT JOIN Rating AS r " +
            "ON r.place.id = p.id " +
            "WHERE p.deleted = false " +
            "AND (:category IS NULL OR p.category = :category) " +
            "AND p.title LIKE %:search% " +
            "AND p.locationRegion.districtName LIKE %:districtName% " +
            "AND p.locationRegion.wardName LIKE %:wardName% " +
            "AND p.locationRegion.address LIKE %:address% " +
            "AND :rating IS NULL OR r.rating = :rating " +
            "GROUP BY p.id "
    )
    Page<PlaceDTO> findAllByCategoryAndSearch(@Param("category") Category category, @Param("search") String search, @Param("districtName") String districtName, @Param("wardName") String wardName, @Param("address") String address, @Param("rating") Double rating , Pageable pageable);

    Optional<Place> findPlaceByIdAndDeletedFalse(Long id);

    Optional<PlaceDTO> findPlaceById(Long id);
    @Query(nativeQuery = true, value = "SELECT p.* ,  avg(r.rating) , CASE WHEN COUNT(r.id) IS NULL THEN 0 ELSE COUNT(r.id) END "+
            "FROM places as p " +
            "LEFT JOIN ratings as r " +
            "ON r.place_id = p.id " +
            "WHERE ST_Distance_Sphere(ST_PointFromText(CONCAT('POINT(', p.longitude, ' ', p.latitude, ')')),ST_PointFromText(CONCAT('POINT(', :longitude, ' ', :latitude, ')')), 4326) <= 1 AND p.id <> :id AND p.deleted = false " +
            "GROUP BY p.id ")
    List<Place> findNearPlace(float longitude, float latitude, Long id);


    @Query("SELECT NEW com.godana.domain.dto.place.PlaceCountDTO (" +
            "count(p.id)" +
            ") " +
            "FROM Place AS p " +
            "WHERE p.deleted = false "
    )
    PlaceCountDTO countPlace();


    @Query(value = "SELECT * FROM v_get_places_current_day", nativeQuery = true)
    IReportDTO getPlaceReportOfCurrentDay();

    @Query(value = "SELECT * FROM v_get_places_current_month", nativeQuery = true)
    IReportDTO getPlaceReportOfCurrentMonth();

    @Query(value = "SELECT * FROM v_get_places_current_year", nativeQuery = true)
    List<IYearReportDTO> getPlaceReportByCurrentYear();

    @Query(value = "SELECT * FROM v_get_places_last_6_months", nativeQuery = true)
    List<I6MonthAgoReportDTO> getPlaceReport6Months();

}
