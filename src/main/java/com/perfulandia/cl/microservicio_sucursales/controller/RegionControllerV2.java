package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.cl.microservicio_sucursales.assemblers.RegionModelAssembler;
import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.service.RegionService;

@RestController
@RequestMapping("/api/v2/regiones")
public class RegionControllerV2 {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionModelAssembler assembler;

    @GetMapping("/{id}")
    public EntityModel<Region> obtenerRegionPorId(@PathVariable Integer idRegion) {
        Region region = regionService.getRegionById(idRegion);
        return assembler.toModel(region);
    }

    @GetMapping
    public CollectionModel<EntityModel<Region>> listarRegiones() {
        List<EntityModel<Region>> regiones = regionService.getAllRegions()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(regiones);
    }
}