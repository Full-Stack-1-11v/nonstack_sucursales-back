package com.perfulandia.cl.microservicio_sucursales.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;
import com.perfulandia.cl.microservicio_sucursales.repository.SucursalRepository;
import com.perfulandia.cl.microservicio_sucursales.repository.CiudadRepository;

@Service    
public class SucursalService {
    

    @Autowired
    private SucursalRepository sucursalRepository;

   


    
    

    @Autowired
    private CiudadService ciudadService;

   


    // Método para guardar una nueva sucursal demo
    public Sucursal createSucursalTest(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    // Método para guardar una nueva sucursal
    public Sucursal createSucursal(Sucursal sucursal) {
        if (sucursal.getCiudad() != null && sucursal.getCiudad().getIdCiudad() != null) {
            // Busca la ciudad por ID
            var ciudad = ciudadService.getCiudadById(sucursal.getCiudad().getIdCiudad());
            if (ciudad == null) {
                throw new RuntimeException("Ciudad no encontrada con id: " + sucursal.getCiudad().getIdCiudad());
            }
            sucursal.setCiudad(ciudad);
        } else {
            throw new RuntimeException("Debe especificar una ciudad válida para la sucursal.");
        }
        return sucursalRepository.save(sucursal);
    }

    // Método para guardar una nueva sucursal con ID de ciudad
    public Sucursal createSucursalByCiudadId(Sucursal sucursal, Integer id_ciudad) {
        if (ciudadService.getCiudadById(id_ciudad) == null) {
            return null;
        }
        sucursal.setCiudad(ciudadService.getCiudadById(id_ciudad));
        return sucursalRepository.save(sucursal);
    }

    // Método para obtener todas las sucursales
    public List<Sucursal> getAllSucursales() {
        return sucursalRepository.findAll();
    }

    // Método para obtener una sucursal por su ID
    public Optional<Sucursal> getSucursalById(Integer id_sucursal) {
        return sucursalRepository.findById(id_sucursal);
    }

    // Método para eliminar una sucursal por su ID
    public void eliminarSucursal(Integer id_sucursal) {
        sucursalRepository.deleteById(id_sucursal);
    }

    
    public Sucursal updateSucursal(Integer idSucursal, Sucursal sucursal) {
        if (sucursalRepository.existsById(idSucursal)) {
            sucursal.setIdSucursal(idSucursal);
            return sucursalRepository.save(sucursal);
        }
        return null;
    }


    public List<Sucursal> getSucursalesByCiudadId(Integer idCiudad) {
        return sucursalRepository.findByCiudadIdCiudad(idCiudad);
    }
}

