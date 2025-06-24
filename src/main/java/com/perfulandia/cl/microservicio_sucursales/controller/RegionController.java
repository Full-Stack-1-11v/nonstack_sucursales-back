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

import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.service.RegionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/sucursales/regiones")
@Tag(name = "Regiones", description = "Operaciones relacionadas con las regiones de las sucursales")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    @Operation(summary = "Listar todas las regiones", description = "Obtiene una lista de todas las regiones disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de regiones obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron regiones")
    })
    public ResponseEntity<List<Region>> listarRegiones() {
        List<Region> regiones = regionService.getAllRegions();
        return ResponseEntity.ok(regiones);
    }

    @GetMapping("/{idRegion}")
    @Operation(summary = "Obtener región por ID", description = "Obtiene una región específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Región encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<Region> obtenerRegionPorId(
        @Parameter(description = "ID de la región a buscar") @PathVariable Integer idRegion) {
        Region region = regionService.getRegionById(idRegion);
        if (region == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(region);
    }

    @PostMapping
    @Operation(summary = "Crear nueva región", description = "Crea una nueva región y la guarda en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Región creada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<Region> crearRegion(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Región a crear",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Region.class),
                examples = @ExampleObject(
                    name = "Ejemplo de región",
                    value = "{ \"nombreRegion\": \"Metropolitana\" }"
                )
            )
        )
        @RequestBody Region region) {
        Region nuevaRegion = regionService.saveRegion(region);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRegion);
    }

    @PutMapping("/{idRegion}")
    @Operation(summary = "Actualizar región", description = "Actualiza una región existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Región actualizada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
        @ApiResponse(responseCode = "404", description = "Región no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<Region> actualizarRegion(
        @Parameter(description = "ID de la región a actualizar") @PathVariable Integer idRegion,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Región con los datos actualizados",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Region.class),
                examples = @ExampleObject(
                    name = "Ejemplo de actualización",
                    value = "{ \"nombreRegion\": \"Actualizada\" }"
                )
            )
        )
        @RequestBody Region region) {
        Region regionActualizada = regionService.updateRegion(idRegion, region);
        if (regionActualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(regionActualizada);
    }

    @PatchMapping("/{idRegion}")
    @Operation(summary = "Actualizar parcialmente región", description = "Actualiza parcialmente una región existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Región actualizada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
        @ApiResponse(responseCode = "404", description = "Región no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<Region> actualizarRegionParcial(
        @Parameter(description = "ID de la región a actualizar") @PathVariable Integer idRegion,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Región con los datos a actualizar parcialmente",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Region.class),
                examples = @ExampleObject(
                    name = "Ejemplo de actualización parcial",
                    value = "{ \"nombreRegion\": \"Parcialmente Actualizada\" }"
                )
            )
        )
        @RequestBody Region region) {
        Region regionActualizada = regionService.patchRegion(idRegion, region);
        if (regionActualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(regionActualizada);
    }

    @DeleteMapping("/{idRegion}")
    @Operation(summary = "Eliminar región", description = "Elimina una región existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Región eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<Void> eliminarRegion(
        @Parameter(description = "ID de la región a eliminar") @PathVariable Integer idRegion) {
        if (regionService.getRegionById(idRegion) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        regionService.deleteById(idRegion);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/buscar/jpql/{nombreRegion}")
    @Operation(summary = "Buscar región por nombre usando JPQL", description = "Busca regiones por su nombre utilizando JPQL")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Regiones encontradas",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron regiones con ese nombre")
    })
    public ResponseEntity<List<Region>> buscarRegionPorNombre(
        @Parameter(description = "Nombre de la región a buscar") @PathVariable String nombreRegion) {
        List<Region> regiones = regionService.buscarRegionPorNombreJPQL(nombreRegion);
        if (regiones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(regiones);
    }

    @GetMapping("/buscar/native/{nombreRegion}")
    @Operation(summary = "Buscar región por nombre usando Native Query", description = "Busca regiones por su nombre utilizando una consulta nativa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Regiones encontradas",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron regiones con ese nombre")
    })
    public ResponseEntity<List<Region>> buscarRegionPorNombreNative(
        @Parameter(description = "Nombre de la región a buscar") @PathVariable String nombreRegion) {
        List<Region> regiones = regionService.buscarRegionPorNombreNative(nombreRegion);
        if (regiones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(regiones);
    }
}
