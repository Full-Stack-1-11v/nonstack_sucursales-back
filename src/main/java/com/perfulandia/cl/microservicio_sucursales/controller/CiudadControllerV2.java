package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.cl.microservicio_sucursales.assemblers.CiudadModelAssembler;
import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.service.CiudadService;

@RestController
@RequestMapping("/api/v2/sucursales/ciudades")
public class CiudadControllerV2 {

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private CiudadModelAssembler ciudadModelAssembler;

    @GetMapping("/{idCiudad}")
    public ResponseEntity<EntityModel<Ciudad>> obtenerCiudadPorId(@PathVariable Integer idCiudad) {
        Ciudad ciudad = ciudadService.getCiudadById(idCiudad);
        if (ciudad == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ciudadModelAssembler.toModel(ciudad));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Ciudad>>> listarCiudades() {
        List<Ciudad> ciudades = ciudadService.getAllCiudades();
        List<EntityModel<Ciudad>> ciudadModels = ciudades.stream()
                .map(ciudadModelAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(ciudadModels));
    }
    
}