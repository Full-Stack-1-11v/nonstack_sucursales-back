package com.perfulandia.cl.microservicio_sucursales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.cl.microservicio_sucursales.model.Region;

/**
 * Repositorio JPA para la entidad {@link com.perfulandia.cl.microservicio_sucursales.model.Region}.
 * <p>
 * Proporciona métodos para acceder y consultar regiones en la base de datos, incluyendo búsquedas por nombre,
 * por ID y consultas personalizadas tanto JPQL como nativas.
 * </p>
 *
 * <ul>
 *   <li>{@link #findByNombreRegion(String)}: Busca una región por su nombre.</li>
 *   <li>{@link #findByIdRegion(Integer)}: Busca una región por su ID.</li>
 *   <li>{@link #findByNombreTest(String)}: Busca regiones por nombre usando JPQL.</li>
 *   <li>{@link #findByRegionIdOptional(Integer)}: Busca una región por su ID usando JPQL y retorna Optional.</li>
 *   <li>{@link #findAllRegions()}: Obtiene todas las regiones usando JPQL.</li>
 *   <li>{@link #findByNombreJPQL(String)}: Busca regiones por nombre usando JPQL.</li>
 *   <li>{@link #findByNombreNative(String)}: Busca regiones por nombre usando consulta nativa.</li>
 * </ul>
 *
 * Extiende {@link org.springframework.data.jpa.repository.JpaRepository} para operaciones CRUD estándar.
 *
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

    /**
     * Busca una región por su nombre.
     * @param nombreRegion Nombre de la región.
     * @return La región encontrada o null si no existe.
     */
    public Region findByNombreRegion(String nombreRegion);

    /**
     * Busca una región por su ID.
     * @param idRegion ID de la región.
     * @return La región encontrada o null si no existe.
     */
    public Region findByIdRegion(Integer idRegion);

    /**
     * Busca regiones por nombre usando JPQL.
     * @param nombreRegion Nombre de la región.
     * @return Lista de regiones que coinciden con el nombre.
     */
    @Query("SELECT r FROM Region r WHERE r.nombreRegion = :nombreRegion")
    List<Region> findByNombreTest(@Param("nombreRegion") String nombreRegion);

    /**
     * Busca una región por su ID usando JPQL y retorna un Optional.
     * @param idRegion ID de la región.
     * @return Optional con la región encontrada o vacío si no existe.
     */
    @Query("SELECT r FROM Region r WHERE r.idRegion = :idRegion")
    Optional<Region> findByRegionIdOptional(@Param("idRegion") Integer idRegion);

    /**
     * Obtiene todas las regiones usando JPQL.
     * @return Lista de todas las regiones.
     */
    @Query("SELECT r FROM Region r")
    List<Region> findAllRegions();

    /**
     * Busca regiones por nombre usando JPQL.
     * @param nombreRegion Nombre de la región.
     * @return Lista de regiones que coinciden con el nombre.
     */
    @Query("SELECT r FROM Region r WHERE r.nombreRegion = :nombreRegion")
    List<Region> findByNombreJPQL(@Param("nombreRegion") String nombreRegion);

    /**
     * Busca regiones por nombre usando consulta SQL nativa.
     * @param nombreRegion Nombre de la región.
     * @return Lista de regiones que coinciden con el nombre.
     */
    @Query(value = "SELECT * FROM region WHERE nombre_region = :nombreRegion", nativeQuery = true)
    List<Region> findByNombreNative(@Param("nombreRegion") String nombreRegion);

}
