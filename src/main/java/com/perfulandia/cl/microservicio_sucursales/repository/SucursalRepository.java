package com.perfulandia.cl.microservicio_sucursales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    public List<Sucursal> findByCiudadIdCiudad(Integer idCiudad);

    @Query("SELECT s FROM Sucursal s WHERE s.nombreSucursal = :nombreSucursal")
    List<Sucursal> findByNombreSucursal(@Param("nombreSucursal") String nombreSucursal);

    @Query("SELECT s FROM Sucursal s WHERE s.idSucursal = :idSucursal")
    Optional<Sucursal> findByIdSucursal(@Param("idSucursal") Integer idSucursal);

    @Query("SELECT s FROM Sucursal s")
    List<Sucursal> findAllSucursales();

}

