package com.perfulandia.cl.microservicio_sucursales.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.repository.RegionRepository;

import jakarta.transaction.Transactional;

/**
 * Servicio para la gestión de regiones.
 * <p>
 * Proporciona métodos para obtener, crear, actualizar (total y parcial) y eliminar regiones,
 * así como para consultar regiones por nombre usando JPQL o SQL nativo.
 * </p>
 *
 * <ul>
 *   <li>{@link #getAllRegions()}: Obtiene todas las regiones.</li>
 *   <li>{@link #saveRegion(Region)}: Guarda una nueva región o retorna la existente si ya está registrada.</li>
 *   <li>{@link #deleteById(Integer)}: Elimina una región por su ID.</li>
 *   <li>{@link #updateRegion(Integer, Region)}: Actualiza completamente una región existente.</li>
 *   <li>{@link #patchRegion(Integer, Region)}: Actualiza parcialmente una región existente.</li>
 *   <li>{@link #buscarRegionPorNombreJPQL(String)}: Busca regiones por nombre usando JPQL.</li>
 *   <li>{@link #buscarRegionPorNombreNative(String)}: Busca regiones por nombre usando SQL nativo.</li>
 *   <li>{@link #getRegionById(Integer)}: Obtiene una región por su ID.</li>
 * </ul>
 *
 * Utiliza {@link com.perfulandia.cl.microservicio_sucursales.repository.RegionRepository}
 * para el acceso a datos.
 *
 */
@Service
@Transactional
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    /**
     * Obtiene una lista de todas las regiones.
     * @return Lista de regiones.
     */
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    /**
     * Guarda una región en el sistema. Si ya existe una región con el mismo nombre y el ID no es nulo,
     * retorna la existente.
     * @param region Región a guardar.
     * @return Región guardada o existente.
     */
    public Region saveRegion(Region region) {
        Region regionExistente = regionRepository.findByNombreRegion(region.getNombreRegion());
        if (region.getIdRegion() != null && regionExistente != null) {
            return regionExistente;
        } 
        return regionRepository.save(region);
    }

    /**
     * Elimina una región por su ID.
     * @param idRegion ID de la región a eliminar.
     */
    public void deleteById(Integer idRegion) {
        regionRepository.deleteById(idRegion);
    }

    /**
     * Actualiza la información completa de una región existente.
     * @param idRegion ID de la región a actualizar.
     * @param region Datos actualizados de la región.
     * @return Región actualizada o null si no existe.
     */
    public Region updateRegion(Integer idRegion, Region region) {
        if (regionRepository.existsById(idRegion)) {
            region.setIdRegion(idRegion);
            return regionRepository.save(region);
        }
        return null;
    }

    /**
     * Actualiza parcialmente la información de una región existente.
     * @param idRegion ID de la región a actualizar.
     * @param region Datos parciales de la región.
     * @return Región actualizada o null si no existe.
     */
    public Region patchRegion(Integer idRegion, Region region) {
        Optional<Region> optionalRegion = regionRepository.findById(idRegion);

        if (optionalRegion.isEmpty()) {
            return null;
        }

        Region existingRegion = optionalRegion.get();

        if (region.getNombreRegion() != null) {
            existingRegion.setNombreRegion(region.getNombreRegion());
        }

        return regionRepository.save(existingRegion);
    }
    
    /**
     * Busca regiones por nombre usando JPQL.
     * @param nombre_region Nombre de la región.
     * @return Lista de regiones que coinciden con el nombre.
     */
    public List<Region> buscarRegionPorNombreJPQL(String nombre_region) {
        return regionRepository.findByNombreJPQL(nombre_region);
    }

    /**
     * Busca regiones por nombre usando SQL nativo.
     * @param nombre_region Nombre de la región.
     * @return Lista de regiones que coinciden con el nombre.
     */
    public List<Region> buscarRegionPorNombreNative(String nombre_region) {
        return regionRepository.findByNombreNative(nombre_region);
    }

    /**
     * Obtiene una región por su ID.
     * @param id_region ID de la región.
     * @return Región encontrada o null si no existe.
     */
    public Region getRegionById(Integer id_region) {
        return regionRepository.findById(id_region).orElse(null);

    }


}
