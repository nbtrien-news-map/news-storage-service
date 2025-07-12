package com.newsmap.repositories;

import com.newsmap.entities.NewsTrackedAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface NewsTrackedAreaRepository extends JpaRepository<NewsTrackedAreaEntity, Long> {

    @Query("SELECT nta FROM NewsTrackedAreaEntity nta JOIN nta.newsSources ns WHERE ns.newsSourceId = :newsSourceId")
    Set<NewsTrackedAreaEntity> findAllByNewsSourceId(@Param("newsSourceId") Integer newsSourceId);
}
