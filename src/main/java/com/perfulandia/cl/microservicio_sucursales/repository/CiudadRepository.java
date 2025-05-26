package com.perfulandia.cl.microservicio_sucursales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;


@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {

    public Ciudad findByNombreCiudad(String nombreCiudad);

    public List<Ciudad> findByRegionIdRegion(Integer idRegion);

    @Query(value= "SELECT * FROM Ciudad c WHERE c.nombreCiudad = :nombreCiudad", nativeQuery = true)
    List<Ciudad> findByNombreTest(@Param("nombreCiudad") String nombreCiudad);

    @Query(value= "SELECT * FROM Ciudad c WHERE c.idCiudad = :idCiudad", nativeQuery = true)
    Optional<Ciudad> findByIdCiudad(@Param("idCiudad") Integer idCiudad);

    @Query(value= "SELECT * FROM Ciudad c", nativeQuery = true)
    List<Ciudad> findAllCiudades();

}
