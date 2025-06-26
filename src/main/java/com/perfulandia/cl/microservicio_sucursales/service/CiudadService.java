package com.perfulandia.cl.microservicio_sucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.repository.CiudadRepository;





@Service
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;


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
        Region region = regionService.getRegionById(idRegion);
        if (region == null) {
            return null;
        }
        //Asigna la región a la ciudad
        ciudad.setRegion(region);
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
