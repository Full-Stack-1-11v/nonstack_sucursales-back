package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/region/{idRegion}")
    @Operation(summary = "Listar ciudades por región", description = "Obtiene una lista de ciudades por región con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ciudades obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron ciudades para la región especificada")
    })
    public ResponseEntity<CollectionModel<EntityModel<Ciudad>>> listarCiudadesPorRegion(
            @Parameter(description = "ID de la región") @PathVariable Integer idRegion) {
        List<Ciudad> ciudades = ciudadService.getCiudadesByRegionId(idRegion);
        if (ciudades.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<EntityModel<Ciudad>> ciudadModels = ciudades.stream()
                .map(ciudadModelAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(ciudadModels));
    }

    @PostMapping
    @Operation(summary = "Crear nueva ciudad", description = "Crea una nueva ciudad y la guarda en la base de datos con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ciudad creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<EntityModel<Ciudad>> crearCiudad(
            @Parameter(description = "Ciudad a crear") @RequestBody Ciudad ciudad) {
        Ciudad nuevaCiudad = ciudadService.createCiudad(ciudad);
        return ResponseEntity.status(HttpStatus.CREATED).body(ciudadModelAssembler.toModel(nuevaCiudad));
    }

    @PutMapping("/{idCiudad}")
    @Operation(summary = "Actualizar ciudad", description = "Actualiza una ciudad existente por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Actualización exitosa",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "Ciudad no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<EntityModel<Ciudad>> actualizarCiudad(
            @Parameter(description = "ID de la ciudad a actualizar") @PathVariable Integer idCiudad,
            @RequestBody Ciudad ciudad) {
        Ciudad ciudadActualizada = ciudadService.updateCiudad(idCiudad, ciudad);
        if (ciudadActualizada != null) {
            return ResponseEntity.ok(ciudadModelAssembler.toModel(ciudadActualizada));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/{idCiudad}")
    @Operation(summary = "Actualizar parcialmente ciudad", description = "Actualiza parcialmente una ciudad existente por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Actualización exitosa",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "Ciudad no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<EntityModel<Ciudad>> actualizarCiudadParcial(
            @Parameter(description = "ID de la ciudad a actualizar") @PathVariable Integer idCiudad,
            @RequestBody Ciudad ciudad) {
        Ciudad ciudadActualizada = ciudadService.updateCiudad(idCiudad, ciudad);
        if (ciudadActualizada != null) {
            return ResponseEntity.ok(ciudadModelAssembler.toModel(ciudadActualizada));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/{idRegion}")
    @Operation(summary = "Crear ciudad por región", description = "Crea una nueva ciudad en una región específica con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ciudad creada exitosamente en la región",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<EntityModel<Ciudad>> crearCiudadPorRegion(
            @Parameter(description = "ID de la región donde se creará la ciudad") @PathVariable Integer idRegion,
            @RequestBody Ciudad ciudad) {
        Ciudad nuevaCiudad = ciudadService.createCiudadByRegion(idRegion, ciudad);
        if (nuevaCiudad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ciudadModelAssembler.toModel(nuevaCiudad));
    }

    @DeleteMapping("/{idCiudad}")
    @Operation(summary = "Eliminar ciudad", description = "Elimina una ciudad existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ciudad eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
    })
    public ResponseEntity<Void> eliminarCiudad(
            @Parameter(description = "ID de la ciudad a eliminar") @PathVariable Integer idCiudad) {
        ciudadService.deleteCiudad(idCiudad);
        return ResponseEntity.noContent().build();
    }
}