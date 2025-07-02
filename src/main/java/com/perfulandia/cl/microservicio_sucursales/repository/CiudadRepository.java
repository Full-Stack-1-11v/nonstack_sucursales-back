package com.perfulandia.cl.microservicio_sucursales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;


/**
 * Repositorio JPA para la entidad {@link com.perfulandia.cl.microservicio_sucursales.model.Ciudad}.
 * <p>
 * Proporciona métodos para acceder y consultar ciudades en la base de datos, incluyendo búsquedas por nombre,
 * por región y consultas nativas personalizadas.
 * </p>
 *
 * <ul>
 *   <li>{@link #findByNombreCiudad(String)}: Busca una ciudad por su nombre.</li>
 *   <li>{@link #findByRegionIdRegion(Integer)}: Busca todas las ciudades de una región.</li>
 *   <li>{@link #findByNombreTest(String)}: Busca ciudades por nombre usando consulta nativa.</li>
 *   <li>{@link #findByIdCiudad(Integer)}: Busca una ciudad por ID usando consulta nativa.</li>
 *   <li>{@link #findAllCiudades()}: Obtiene todas las ciudades usando consulta nativa.</li>
 * </ul>
 *
 * Extiende {@link org.springframework.data.jpa.repository.JpaRepository} para operaciones CRUD estándar.
 *
 */
@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {

    /**
     * Busca una ciudad por su nombre.
     * @param nombreCiudad Nombre de la ciudad.
     * @return La ciudad encontrada o null si no existe.
     */
    public Ciudad findByNombreCiudad(String nombreCiudad);

    /**
     * Busca todas las ciudades asociadas a una región por su ID.
     * @param idRegion ID de la región.
     * @return Lista de ciudades de la región.
     */
    public List<Ciudad> findByRegionIdRegion(Integer idRegion);

    /**
     * Busca ciudades por nombre usando una consulta nativa.
     * @param nombreCiudad Nombre de la ciudad.
     * @return Lista de ciudades que coinciden con el nombre.
     */
    @Query(value= "SELECT * FROM Ciudad c WHERE c.nombreCiudad = :nombreCiudad", nativeQuery = true)
    List<Ciudad> findByNombreTest(@Param("nombreCiudad") String nombreCiudad);

    /**
     * Busca una ciudad por su ID usando una consulta nativa.
     * @param idCiudad ID de la ciudad.
     * @return Un Optional con la ciudad encontrada o vacío si no existe.
     */
    @Query(value= "SELECT * FROM Ciudad c WHERE c.idCiudad = :idCiudad", nativeQuery = true)
    Optional<Ciudad> findByIdCiudad(@Param("idCiudad") Integer idCiudad);

    /**
     * Obtiene todas las ciudades usando una consulta nativa.
     * @return Lista de todas las ciudades.
     */
    @Query(value= "SELECT * FROM Ciudad c", nativeQuery = true)
    List<Ciudad> findAllCiudades();

}
