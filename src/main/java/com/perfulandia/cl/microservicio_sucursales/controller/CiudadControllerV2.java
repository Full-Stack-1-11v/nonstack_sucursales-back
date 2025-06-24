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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/sucursales/ciudades")
@Tag(name = "Ciudades V2", description = "Operaciones HATEOAS relacionadas con las ciudades")
public class CiudadControllerV2 {

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private CiudadModelAssembler ciudadModelAssembler;

    @GetMapping("/{idCiudad}")
    @Operation(summary = "Obtener ciudad por ID", description = "Obtiene una ciudad específica por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ciudad encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
    })
    public ResponseEntity<EntityModel<Ciudad>> obtenerCiudadPorId(
        @Parameter(description = "ID de la ciudad a buscar") @PathVariable Integer idCiudad) {
        Ciudad ciudad = ciudadService.getCiudadById(idCiudad);
        if (ciudad == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ciudadModelAssembler.toModel(ciudad));
    }

    @GetMapping
    @Operation(summary = "Listar todas las ciudades", description = "Obtiene una lista de todas las ciudades disponibles con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ciudades obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron ciudades")
    })
    public ResponseEntity<CollectionModel<EntityModel<Ciudad>>> listarCiudades() {
        List<Ciudad> ciudades = ciudadService.getAllCiudades();
        List<EntityModel<Ciudad>> ciudadModels = ciudades.stream()
                .map(ciudadModelAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(ciudadModels));
    }
}