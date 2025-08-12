package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/v1/sucursales")
@Tag(name = "Sucursales", description = "Operaciones relacionadas con las sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;


    // Crear una nueva sucursal
    @PostMapping("/crear")
    @Operation(summary = "Crear una nueva sucursal", description = "Crea una nueva sucursal y la guarda en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sucursal creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<Sucursal> crearSucursal(
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
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSucursal);
    }

    // Listar todas las sucursales
    @GetMapping
    @Operation(summary = "Listar todas las sucursales", description = "Obtiene una lista de todas las sucursales disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron sucursales")
    })
    public ResponseEntity<List<Sucursal>> listarSucursales() {
        List<Sucursal> sucursales = sucursalService.getAllSucursales();
        return ResponseEntity.ok(sucursales);
    }


    // Obtener una sucursal por su ID
    @GetMapping("/{idSucursal}")
    @Operation(summary = "Obtener una sucursal por su ID", description = "Obtiene una sucursal específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<Sucursal> obtenerSucursalPorId(
        @Parameter(description = "ID de la sucursal a buscar") @PathVariable Integer idSucursal) {
        Sucursal sucursal = sucursalService.getSucursalById(idSucursal).orElse(null);
        if (sucursal != null) {
            return ResponseEntity.ok(sucursal);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    // Actualizar una sucursal
    @PutMapping("/{idSucursal}")
    @Operation(summary = "Actualizar una sucursal", description = "Actualiza una sucursal existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<Sucursal> actualizarSucursal(
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
        if (sucursalActualizada != null) {
            return ResponseEntity.ok(sucursalActualizada);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


        // Eliminar una sucursal
    @DeleteMapping("/{idSucursal}")
    @Operation(summary = "Eliminar una sucursal", description = "Elimina una sucursal existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Sucursal eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public ResponseEntity<Void> eliminarSucursal(
        @Parameter(description = "ID de la sucursal a eliminar") @PathVariable Integer idSucursal) {
        if (sucursalService.getSucursalById(idSucursal).isPresent()) {
            sucursalService.eliminarSucursal(idSucursal);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
