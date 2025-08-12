package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.cl.microservicio_sucursales.assemblers.SucursalModelAssembler;
import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;
import com.perfulandia.cl.microservicio_sucursales.service.SucursalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/sucursales")
@Tag(name = "Sucursales V2", description = "Operaciones HATEOAS relacionadas con las sucursales")
public class SucursalControllerV2 {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler sucursalModelAssembler;

    @GetMapping("/{idSucursal}")
    @Operation(summary = "Obtener una sucursal por su ID", description = "Obtiene una sucursal específica por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<EntityModel<Sucursal>> obtenerSucursalPorId(
        @Parameter(description = "ID de la sucursal a buscar") @PathVariable Integer idSucursal) {
        Sucursal sucursal = sucursalService.getSucursalById(idSucursal).orElse(null);
        if (sucursal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sucursalModelAssembler.toModel(sucursal));
    }

    @GetMapping
    @Operation(summary = "Listar todas las sucursales", description = "Obtiene una lista de todas las sucursales disponibles con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron sucursales")
    })
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> listarSucursales() {
        List<Sucursal> sucursales = sucursalService.getAllSucursales();
        List<EntityModel<Sucursal>> sucursalModels = sucursales.stream()
                .map(sucursalModelAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(sucursalModels));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva sucursal", description = "Crea una nueva sucursal y la guarda en la base de datos, devolviendo el recurso con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<EntityModel<Sucursal>> crearSucursal(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Sucursal a crear",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Sucursal.class),
                examples = @ExampleObject(
                    name = "Ejemplo de sucursal",
                    value = "{ \"nombreSucursal\": \"Sucursal Central\", \"ciudad\": { \"idCiudad\": 1 } }"
                )
            )
        )
        @RequestBody Sucursal sucursal) {
        Sucursal nuevaSucursal = sucursalService.createSucursal(sucursal);
        return ResponseEntity.created(
                sucursalModelAssembler.toModel(nuevaSucursal).getRequiredLink("self").toUri())
                .body(sucursalModelAssembler.toModel(nuevaSucursal));
    }

    @PutMapping("/{idSucursal}")
    @Operation(summary = "Actualizar una sucursal", description = "Actualiza una sucursal existente por su ID y devuelve el recurso con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<EntityModel<Sucursal>> actualizarSucursal(
        @Parameter(description = "ID de la sucursal a actualizar") @PathVariable Integer idSucursal,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Sucursal con los datos actualizados",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Sucursal.class),
                examples = @ExampleObject(
                    name = "Ejemplo de actualización",
                    value = "{ \"nombreSucursal\": \"Sucursal Actualizada\", \"ciudad\": { \"idCiudad\": 2 } }"
                )
            )
        )
        @RequestBody Sucursal sucursal) {
        Sucursal sucursalActualizada = sucursalService.updateSucursal(idSucursal, sucursal);
        if (sucursalActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sucursalModelAssembler.toModel(sucursalActualizada));
    }
}