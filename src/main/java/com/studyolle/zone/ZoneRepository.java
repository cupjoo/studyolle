package com.studyolle.zone;

import com.studyolle.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

    @Query("select z from Zone z order by z.city, z.localNameOfCity, z.province")
    List<Zone> findAllByNameAsc();

    Optional<Zone> findByCityAndProvince(String city, String province);
}
