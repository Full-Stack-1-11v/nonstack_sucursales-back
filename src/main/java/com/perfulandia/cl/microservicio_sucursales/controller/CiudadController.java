package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.service.CiudadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/v1/sucursales/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    //Listar todas las ciudades
    @GetMapping
    @Operation(summary = "Listar todas las ciudades", description = "Obtiene una lista de todas las ciudades disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ciudades obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron ciudades")
    })
    @Schema(description = "Controlador para gestionar las ciudades de las sucursales")
    public ResponseEntity<List<Ciudad>> listarCiudades() {
        List<Ciudad> ciudades = ciudadService.getAllCiudades();
        return ResponseEntity.ok(ciudades);
    }

    //Listar ciudades por region
    @GetMapping("/region/{idRegion}")
    @Operation(summary = "Listar ciudades por región", description = "Obtiene una lista de todas las ciudades disponibles en una región específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ciudades obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron ciudades para la región especificada")
    })
    @Schema(description = "Controlador para gestionar las ciudades de las sucursales por región")
    public ResponseEntity<List<Ciudad>> listarCiudadesPorRegion(@Parameter(description = "ID de la región") @PathVariable Integer idRegion) {
        List<Ciudad> ciudades = ciudadService.getCiudadesByRegionId(idRegion);
        if (ciudades.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(ciudades);
    }

    //Crear una nueva ciudad
    @PostMapping
    @Operation(summary = "Crear nueva ciudad", description = "Crea una nueva ciudad y la guarda en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ciudad creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @Schema(description = "Controlador para crear nuevas ciudades en las sucursales")
    public ResponseEntity<Ciudad> crearCiudad(@Parameter(description = "Ciudad a crear") @RequestBody Ciudad ciudad) {
        Ciudad nuevaCiudad = ciudadService.createCiudad(ciudad);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCiudad);
        
    }

    //Actualizar una ciudad
    @PutMapping("/{idCiudad}")
    @Operation(summary = "Actualizar ciudad", description = "Actualiza una ciudad existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Actualización exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "Ciudad no encontrada", content= @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @Schema(description = "Controlador para actualizar ciudades de las sucursales")
    public ResponseEntity<Ciudad> actualizarCiudad(@Parameter(description = "ID de la ciudad a actualizar") @PathVariable Integer idCiudad, @RequestBody Ciudad ciudad) {
        Ciudad ciudadActualizada = ciudadService.updateCiudad(idCiudad, ciudad);
        if (ciudadActualizada != null) {
            return ResponseEntity.ok(ciudadActualizada);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    //actualizar una ciudad parcialmente
    @PatchMapping("/{idCiudad}")
    @Operation(summary = "Actualizar parcialmente ciudad", description = "Actualiza parcialmente una ciudad existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Actualización exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "Ciudad no encontrada", content= @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @Schema(description = "Controlador para actualizar parcialmente ciudades de las sucursales")
    public ResponseEntity<Ciudad> actualizarCiudadParcial(@Parameter(description = "ID de la ciudad a actualizar") @PathVariable Integer idCiudad, @RequestBody Ciudad ciudad) {
        Ciudad ciudadActualizada = ciudadService.updateCiudad(idCiudad, ciudad);
        if (ciudadActualizada != null) {
            return ResponseEntity.ok(ciudadActualizada);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //crear una nueva ciudad por region
    @PostMapping("/{idRegion}")
    @Operation(summary = "Crear ciudad por región", description = "Crea una nueva ciudad en una región específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ciudad creada exitosamente en la región", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    @Schema(description = "Controlador para crear ciudades en una región específica de las sucursales")
    public ResponseEntity<Ciudad> crearCiudadPorRegion(@Parameter(description = "ID de la región donde se creará la ciudad") @PathVariable Integer idRegion, @RequestBody Ciudad ciudad) {
        Ciudad nuevaCiudad = ciudadService.createCiudadByRegion(idRegion, ciudad);
        if (nuevaCiudad == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCiudad);
    }

    @DeleteMapping("/{idCiudad}")
    @Operation(summary = "Eliminar ciudad", description = "Elimina una ciudad existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ciudad eliminada exitosamente", content = @Content(mediaType= "application/json", schema = @Schema(implementation = Ciudad.class))),
        @ApiResponse(responseCode = "404", description = "Ciudad no encontrada")
    })
    @Schema(description = "Controlador para eliminar ciudades de las sucursales")
    public ResponseEntity<Void> eliminarCiudad(@Parameter(description = "ID de la ciudad a eliminar") @PathVariable Integer idCiudad) {
        ciudadService.deleteCiudad(idCiudad);
        return ResponseEntity.noContent().build();
    }


}