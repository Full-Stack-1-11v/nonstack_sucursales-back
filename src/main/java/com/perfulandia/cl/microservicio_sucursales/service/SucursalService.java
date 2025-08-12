package com.perfulandia.cl.microservicio_sucursales.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;
import com.perfulandia.cl.microservicio_sucursales.repository.SucursalRepository;


/**
 * Servicio para la gestión de sucursales.
 * <p>
 * Proporciona métodos para crear, actualizar, eliminar y consultar sucursales,
 * así como para asociarlas a ciudades.
 * </p>
 *
 * <ul>
 *   <li>{@link #createSucursal(Sucursal)}: Crea una nueva sucursal asociada a una ciudad existente.</li>
 *   <li>{@link #createSucursalByCiudadId(Sucursal, Integer)}: Crea una sucursal usando el ID de ciudad.</li>
 *   <li>{@link #getAllSucursales()}: Obtiene todas las sucursales.</li>
 *   <li>{@link #getSucursalById(Integer)}: Obtiene una sucursal por su ID.</li>
 *   <li>{@link #eliminarSucursal(Integer)}: Elimina una sucursal por su ID.</li>
 *   <li>{@link #updateSucursal(Integer, Sucursal)}: Actualiza una sucursal existente.</li>
 *   <li>{@link #getSucursalesByCiudadId(Integer)}: Obtiene sucursales por ID de ciudad.</li>
 * </ul>
 *
 * Utiliza {@link com.perfulandia.cl.microservicio_sucursales.repository.SucursalRepository}
 * y {@link com.perfulandia.cl.microservicio_sucursales.service.CiudadService} para el acceso a datos.
 *
 */
@Service    
public class SucursalService {
    
    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private CiudadService ciudadService;

    /**
     * Crea una nueva sucursal asociada a una ciudad existente.
     * @param sucursal Sucursal a crear.
     * @return Sucursal creada.
     * @throws RuntimeException si la ciudad no existe o no es válida.
     */
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

    /**
     * Crea una sucursal usando el ID de ciudad.
     * @param sucursal Sucursal a crear.
     * @param id_ciudad ID de la ciudad.
     * @return Sucursal creada o null si la ciudad no existe.
     */
    public Sucursal createSucursalByCiudadId(Sucursal sucursal, Integer id_ciudad) {
        if (ciudadService.getCiudadById(id_ciudad) == null) {
            return null;
        }
        sucursal.setCiudad(ciudadService.getCiudadById(id_ciudad));
        return sucursalRepository.save(sucursal);
    }

    /**
     * Obtiene todas las sucursales.
     * @return Lista de sucursales.
     */
    public List<Sucursal> getAllSucursales() {
        return sucursalRepository.findAll();
    }

    /**
     * Obtiene una sucursal por su ID.
     * @param id_sucursal ID de la sucursal.
     * @return Optional con la sucursal encontrada o vacío si no existe.
     */
    public Optional<Sucursal> getSucursalById(Integer id_sucursal) {
        return sucursalRepository.findById(id_sucursal);
    }

    /**
     * Elimina una sucursal por su ID.
     * @param id_sucursal ID de la sucursal a eliminar.
     */
    public void eliminarSucursal(Integer id_sucursal) {
        sucursalRepository.deleteById(id_sucursal);
    }

    /**
     * Actualiza una sucursal existente.
     * @param idSucursal ID de la sucursal a actualizar.
     * @param sucursal Datos de la sucursal actualizados.
     * @return Sucursal actualizada o null si no existe.
     */
    public Sucursal updateSucursal(Integer idSucursal, Sucursal sucursal) {
        if (sucursalRepository.existsById(idSucursal)) {
            sucursal.setIdSucursal(idSucursal);
            return sucursalRepository.save(sucursal);
        }
        return null;
    }

    /**
     * Obtiene todas las sucursales asociadas a una ciudad.
     * @param idCiudad ID de la ciudad.
     * @return Lista de sucursales de la ciudad.
     */
    public List<Sucursal> getSucursalesByCiudadId(Integer idCiudad) {
        return sucursalRepository.findByCiudadIdCiudad(idCiudad);
    }
}

