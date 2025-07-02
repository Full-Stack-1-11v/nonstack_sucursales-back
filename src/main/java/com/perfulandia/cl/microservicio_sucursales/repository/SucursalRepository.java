package com.perfulandia.cl.microservicio_sucursales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;

/**
 * Repositorio JPA para la entidad {@link com.perfulandia.cl.microservicio_sucursales.model.Sucursal}.
 * <p>
 * Proporciona métodos para acceder y consultar sucursales en la base de datos, incluyendo búsquedas por nombre,
 * por ciudad y consultas personalizadas usando JPQL.
 * </p>
 *
 * <ul>
 *   <li>{@link #findByCiudadIdCiudad(Integer)}: Busca todas las sucursales de una ciudad.</li>
 *   <li>{@link #findByNombreSucursal(String)}: Busca sucursales por nombre usando JPQL.</li>
 *   <li>{@link #findByIdSucursal(Integer)}: Busca una sucursal por ID usando JPQL y retorna Optional.</li>
 *   <li>{@link #findAllSucursales()}: Obtiene todas las sucursales usando JPQL.</li>
 * </ul>
 *
 * Extiende {@link org.springframework.data.jpa.repository.JpaRepository} para operaciones CRUD estándar.
 *
 */
@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    /**
     * Busca todas las sucursales asociadas a una ciudad por su ID.
     * @param idCiudad ID de la ciudad.
     * @return Lista de sucursales de la ciudad.
     */
    public List<Sucursal> findByCiudadIdCiudad(Integer idCiudad);

    /**
     * Busca sucursales por nombre usando JPQL.
     * @param nombreSucursal Nombre de la sucursal.
     * @return Lista de sucursales que coinciden con el nombre.
     */
    @Query("SELECT s FROM Sucursal s WHERE s.nombreSucursal = :nombreSucursal")
    List<Sucursal> findByNombreSucursal(@Param("nombreSucursal") String nombreSucursal);

    /**
     * Busca una sucursal por su ID usando JPQL y retorna un Optional.
     * @param idSucursal ID de la sucursal.
     * @return Optional con la sucursal encontrada o vacío si no existe.
     */
    @Query("SELECT s FROM Sucursal s WHERE s.idSucursal = :idSucursal")
    Optional<Sucursal> findByIdSucursal(@Param("idSucursal") Integer idSucursal);

    /**
     * Obtiene todas las sucursales usando JPQL.
     * @return Lista de todas las sucursales.
     */
    @Query("SELECT s FROM Sucursal s")
    List<Sucursal> findAllSucursales();

}

