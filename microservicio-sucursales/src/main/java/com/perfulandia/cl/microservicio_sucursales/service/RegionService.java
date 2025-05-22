package com.perfulandia.cl.microservicio_sucursales.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    //Obtiene una lista de todas las regiones
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    //Guarda una región en el sistema
    public Region saveRegion(Region region) {
        Region regionExistente = regionRepository.findByNombreRegion(region.getNombreRegion());
        if (region.getIdRegion() != null && regionExistente != null) {
            return regionExistente;
        } 
        return regionRepository.save(region);
    }

    //elimina una región por su id
    public void deleteById(Integer idRegion) {
        regionRepository.deleteById(idRegion);
    }

    //actualiza la información de una región completa
    public Region updateRegion(Integer idRegion, Region region) {
        if (regionRepository.existsById(idRegion)) {
            region.setIdRegion(idRegion);
            return regionRepository.save(region);
        }
        return null;
    }

    // Actualizar parcialmente la información de una región
    // Se utiliza Optional para evitar NullPointerException
    public Region patchRegion(Integer idRegion, Region region) {
        Optional<Region> optionalRegion = regionRepository.findById(idRegion);

        if (optionalRegion.isEmpty()) {
            return null;
        }

        Region existingRegion = optionalRegion.get();

        if (region.getNombreRegion() != null) {
            existingRegion.setNombreRegion(region.getNombreRegion());
        }
            existingRegion.setNombreRegion(region.getNombreRegion());

        return regionRepository.save(existingRegion);
        

    }
    
    // Método para buscar regiones por nombre usando JPQL
    public List<Region> buscarRegionPorNombreJPQL(String nombre_region) {
        return regionRepository.findByNombreJPQL(nombre_region);
    }

    // Método para buscar regiones por nombre usando SQL nativo
    public List<Region> buscarRegionPorNombreNative(String nombre_region) {
        return regionRepository.findByNombreNative(nombre_region);
    }

    // Método para buscar una región por su ID
    public Region getRegionById(Integer id_region) {
        return regionRepository.findById(id_region).orElse(null);

    }


}
