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

import com.perfulandia.cl.microservicio_sucursales.assemblers.SucursalModelAssembler;
import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;
import com.perfulandia.cl.microservicio_sucursales.service.SucursalService;

@RestController
@RequestMapping("/api/v2/sucursales")
public class SucursalControllerV2 {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler sucursalModelAssembler;

    @GetMapping("/{idSucursal}")
    public ResponseEntity<EntityModel<Sucursal>> obtenerSucursalPorId(@PathVariable Integer idSucursal) {
        Sucursal sucursal = sucursalService.getSucursalById(idSucursal).orElse(null);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sucursalModelAssembler.toModel(sucursal));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> listarSucursales() {
        List<Sucursal> sucursales = sucursalService.getAllSucursales();
        List<EntityModel<Sucursal>> sucursalModels = sucursales.stream()
                .map(sucursalModelAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(sucursalModels));
    }
}