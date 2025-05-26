package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.service.CiudadService;


@RestController
@RequestMapping("/api/v1/sucursales/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    //Listar todas las ciudades
    @GetMapping
    public ResponseEntity<List<Ciudad>> listarCiudades() {
        List<Ciudad> ciudades = ciudadService.getAllCiudades();
        return ResponseEntity.ok(ciudades);
    }

    //Listar ciudades por region
    @GetMapping("/{idCiudad}")
    public ResponseEntity<List<Ciudad>> ObtenerCiudadPorRegion(@PathVariable Integer idRegion) {
        List<Ciudad> ciudades = ciudadService.getCiudadesByRegionId(idRegion);

        if (ciudades == null || ciudades.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(ciudades);
    }

    //Crear una nueva ciudad
    @PostMapping
    public ResponseEntity<Ciudad> crearCiudad(@RequestBody Ciudad ciudad) {
        Ciudad nuevaCiudad = ciudadService.createCiudad(ciudad);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCiudad);
    }

    //crear una nueva ciudad por region
    @PostMapping("/{idRegion}")
    public ResponseEntity<Ciudad> crearCiudadPorRegion(@PathVariable Integer idRegion, @RequestBody Ciudad ciudad) {
        Ciudad nuevaCiudad = ciudadService.createCiudadByRegion(idRegion, ciudad);
        if (nuevaCiudad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCiudad);
    }

    @DeleteMapping("/{idCiudad}")
    public ResponseEntity<Void> eliminarCiudad(@PathVariable Integer idCiudad) {
        ciudadService.deleteCiudad(idCiudad);
        return ResponseEntity.noContent().build();
    }


}