package com.gazorpazorp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gazorpazorp.model.Store;
import com.gazorpazorp.model.dto.StoreIdWithDistance;

public interface StoreRepository extends JpaRepository<Store, Long>{

	@Query("select new com.gazorpazorp.model.dto.StoreIdWithDistance (s.id, SQRT("
			+ "POWER(69.1 * (s.latitude - 43.344), 2) + "
			+ "POWER(69.1 * (-80.33 - s.longitude) * COS(s.latitude / 57.3), 2)) as distance) "
			+ "from Store s where SQRT("
			+ "POWER(69.1 * (s.latitude - 43.344), 2) + "
			+ "POWER(69.1 * (-80.33 - s.longitude) * COS(s.latitude / 57.3), 2)) < 25  order by distance")
	public List<StoreIdWithDistance> getStoresNearPoints(@Param("latitude")double lat, @Param("longitude")double lon);
}
/*
SELECT ID, SQRT(
    POWER(69.1 * (latitude - 43.344), 2) +
    POWER(69.1 * (-80.33 - longitude) * COS(latitude / 57.3), 2)) as distance
from STORE where SQRT(
    POWER(69.1 * (latitude - 43.344), 2) +
    POWER(69.1 * (-80.33 - longitude) * COS(latitude / 57.3), 2)) < 25  order by distance;
*/