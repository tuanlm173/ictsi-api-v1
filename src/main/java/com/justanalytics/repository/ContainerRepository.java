package com.justanalytics.repository;

import com.justanalytics.entity.Container;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerRepository extends CrudRepository<Container, Long> {

    @Query(value = "SELECT TOP 3 * FROM dbo.container")
    List<Container> findTopContainer();

    @Query(value = "SELECT * FROM dbo.container WHERE ContainerNbr = :container_number")
    List<Container> findTopContainerCustom(@Param("container_number") String containerNumber);
}
