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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/regiones")
@Tag(name = "Regiones V2", description = "Operaciones HATEOAS relacionadas con las regiones")
public class RegionControllerV2 {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionModelAssembler assembler;

    @GetMapping("/{idRegion}")
    @Operation(summary = "Obtener región por ID", description = "Obtiene una región específica por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Región encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public EntityModel<Region> obtenerRegionPorId(
        @Parameter(description = "ID de la región a buscar") @PathVariable Integer idRegion) {
        Region region = regionService.getRegionById(idRegion);
        return assembler.toModel(region);
    }

    @GetMapping
    @Operation(
        summary = "Listar todas las regiones",
        description = "Obtiene una lista de todas las regiones disponibles con enlaces HATEOAS"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de regiones obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron regiones")
    })
    public CollectionModel<EntityModel<Region>> listarRegiones() {
        List<EntityModel<Region>> regiones = regionService.getAllRegions()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(regiones);
    }
}