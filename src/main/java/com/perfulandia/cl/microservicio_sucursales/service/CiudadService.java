package com.perfulandia.cl.microservicio_sucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.repository.CiudadRepository;
import com.perfulandia.cl.microservicio_sucursales.repository.RegionRepository;





@Service
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private RegionService regionService;

    public List<Ciudad> getAllCiudades() {
        return ciudadRepository.findAll();
    }

    public Ciudad getCiudadById(Integer idCiudad) {
        return ciudadRepository.findById(idCiudad).orElse(null);
    }

    public Ciudad createCiudad(Ciudad ciudad) {
        return ciudadRepository.save(ciudad);
    }

    public Ciudad createCiudadByRegion(Integer idRegion, Ciudad ciudad) {
        //Verifica si la región existe
        if (regionService.getRegionById(idRegion) == null) {
            return null;
        }

        //Asigna la región a la ciudad
        ciudad.setRegion(regionService.getRegionById(idRegion));

        return ciudadRepository.save(ciudad);
    }

    public Ciudad updateCiudad(Integer idCiudad, Ciudad ciudad) {
        if (ciudadRepository.existsById(idCiudad)) {
            ciudad.setIdCiudad(idCiudad);
            return ciudadRepository.save(ciudad);
        }
        return null;
    }

    public void deleteCiudad(Integer idCiudad) {
        if(ciudadRepository.existsById(idCiudad)) {
           ciudadRepository.deleteById(idCiudad);
        }
    }

    public List<Ciudad> getCiudadesByRegionId(Integer idRegion) {
        return ciudadRepository.findByRegionIdRegion(idRegion);
    }

}
