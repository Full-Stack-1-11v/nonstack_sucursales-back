package com.perfulandia.cl.microservicio_sucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.repository.CiudadRepository;

/**
 * Servicio para la gestión de ciudades.
 * <p>
 * Proporciona métodos para obtener, crear, actualizar y eliminar ciudades,
 * así como para consultar ciudades por región.
 * </p>
 *
 * <ul>
 *   <li>{@link #getAllCiudades()}: Obtiene todas las ciudades.</li>
 *   <li>{@link #getCiudadById(Integer)}: Obtiene una ciudad por su ID.</li>
 *   <li>{@link #createCiudad(Ciudad)}: Crea una nueva ciudad.</li>
 *   <li>{@link #createCiudadByRegion(Integer, Ciudad)}: Crea una ciudad asociada a una región.</li>
 *   <li>{@link #updateCiudad(Integer, Ciudad)}: Actualiza una ciudad existente.</li>
 *   <li>{@link #deleteCiudad(Integer)}: Elimina una ciudad por su ID.</li>
 *   <li>{@link #getCiudadesByRegionId(Integer)}: Obtiene ciudades por ID de región.</li>
 * </ul>
 *
 * Utiliza {@link com.perfulandia.cl.microservicio_sucursales.repository.CiudadRepository}
 * y {@link com.perfulandia.cl.microservicio_sucursales.service.RegionService} para el acceso a datos.
 *
 */
@Service
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private RegionService regionService;

    /**
     * Obtiene todas las ciudades.
     * @return Lista de ciudades.
     */
    public List<Ciudad> getAllCiudades() {
        return ciudadRepository.findAll();
    }

    /**
     * Obtiene una ciudad por su ID.
     * @param idCiudad ID de la ciudad.
     * @return La ciudad encontrada o null si no existe.
     */
    public Ciudad getCiudadById(Integer idCiudad) {
        return ciudadRepository.findById(idCiudad).orElse(null);
    }

    /**
     * Crea una nueva ciudad.
     * @param ciudad Ciudad a crear.
     * @return Ciudad creada.
     */
    public Ciudad createCiudad(Ciudad ciudad) {
        return ciudadRepository.save(ciudad);
    }

    /**
     * Crea una ciudad asociada a una región.
     * @param idRegion ID de la región.
     * @param ciudad Ciudad a crear.
     * @return Ciudad creada o null si la región no existe.
     */
    public Ciudad createCiudadByRegion(Integer idRegion, Ciudad ciudad) {
        Region region = regionService.getRegionById(idRegion);
        if (region == null) {
            return null;
        }
        // Asigna la región a la ciudad
        ciudad.setRegion(region);
        return ciudadRepository.save(ciudad);
    }

    /**
     * Actualiza una ciudad existente.
     * @param idCiudad ID de la ciudad a actualizar.
     * @param ciudad Datos de la ciudad actualizados.
     * @return Ciudad actualizada o null si no existe.
     */
    public Ciudad updateCiudad(Integer idCiudad, Ciudad ciudad) {
        if (ciudadRepository.existsById(idCiudad)) {
            ciudad.setIdCiudad(idCiudad);
            return ciudadRepository.save(ciudad);
        }
        return null;
    }

    /**
     * Elimina una ciudad por su ID.
     * @param idCiudad ID de la ciudad a eliminar.
     */
    public void deleteCiudad(Integer idCiudad) {
        if(ciudadRepository.existsById(idCiudad)) {
           ciudadRepository.deleteById(idCiudad);
        }
    }

    /**
     * Obtiene todas las ciudades asociadas a una región.
     * @param idRegion ID de la región.
     * @return Lista de ciudades de la región.
     */
    public List<Ciudad> getCiudadesByRegionId(Integer idRegion) {
        return ciudadRepository.findByRegionIdRegion(idRegion);
    }

}
