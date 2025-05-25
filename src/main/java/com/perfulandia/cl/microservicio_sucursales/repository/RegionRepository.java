package com.perfulandia.cl.microservicio_sucursales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.cl.microservicio_sucursales.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

    //buscar region por su nombre
    public Region findByNombreRegion(String nombreRegion);

    public Region findByIdRegion(Integer idRegion);

  

    @Query("SELECT r FROM Region r WHERE r.nombreRegion = :nombreRegion")
    List<Region> findByNombreTest(@Param("nombreRegion") String nombreRegion);

    @Query("SELECT r FROM Region r WHERE r.idRegion = :idRegion")
    Optional<Region> findByRegionIdOptional(@Param("idRegion") Integer idRegion);

    @Query("SELECT r FROM Region r")
    List<Region> findAllRegions();

    //buscar region por su nombre utilizando jpql
    @Query("SELECT r FROM Region r WHERE r.nombreRegion = :nombreRegion")
    List<Region> findByNombreJPQL(@Param("nombreRegion") String nombreRegion);

    //buscar regiones por su nombre utilizando sql nativo
    @Query(value = "SELECT * FROM region WHERE nombre_region = :nombreRegion", nativeQuery = true)
    List<Region> findByNombreNative(@Param("nombreRegion") String nombreRegion);

}
