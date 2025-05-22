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

    @Query("SELECT c FROM Ciudad c WHERE c.nombreCiudad = :nombreCiudad")
    List<Ciudad> findByNombreTest(@Param("nombreCiudad") String nombreCiudad);

    @Query("SELECT c FROM Ciudad c WHERE c.idCiudad = :idCiudad")
    Optional<Ciudad> findByIdCiudad(@Param("idCiudad") Integer idCiudad);

    @Query("SELECT c FROM Ciudad c")
    List<Ciudad> findAllCiudades();

}
