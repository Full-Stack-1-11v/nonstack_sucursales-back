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

@RestController
@RequestMapping("/api/v1/sucursales/regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> listarRegiones() {
        List<Region> regiones = regionService.getAllRegions();
        return ResponseEntity.ok(regiones);
    }

    @GetMapping("/{idRegion}")
    public ResponseEntity<Region> obtenerRegionPorId(@PathVariable Integer idRegion) {
        Region region = regionService.getRegionById(idRegion);
        if (region != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } 
        return ResponseEntity.notFound().build();
        
    }

    @PostMapping
    public ResponseEntity<Region> crearRegion(@RequestBody Region region) {
        Region nuevaRegion = regionService.saveRegion(region);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRegion);
    }

    @PutMapping("/{idRegion}")
    public ResponseEntity<Region> actualizarRegion(@PathVariable Integer idRegion, @RequestBody Region region) {
        Region regionActualizada = regionService.updateRegion(idRegion, region);
        if (regionActualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(regionActualizada);
    }


    @PatchMapping("/{idRegion}")
    public ResponseEntity<Region> actualizarRegionParcial(@PathVariable Integer idRegion, @RequestBody Region region) {
        Region regionActualizada = regionService.patchRegion(idRegion, region);
        if (regionActualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(regionActualizada);
    }

    @DeleteMapping("/{idRegion}")
    public ResponseEntity<Void> eliminarRegion(@PathVariable Integer idRegion) {
        if (regionService.getRegionById(idRegion) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        regionService.deleteById(idRegion);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/buscar/jpql/{nombreRegion}")
    public ResponseEntity<List<Region>> buscarRegionPorNombre(@PathVariable String nombreRegion) {
        List<Region> regiones = regionService.buscarRegionPorNombreJPQL(nombreRegion);
        if (regiones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(regiones);
    }

    @GetMapping("/buscar/native/{nombreRegion}")
    public ResponseEntity<List<Region>> buscarRegionPorNombreNative(@PathVariable String nombreRegion) {
        List<Region> regiones = regionService.buscarRegionPorNombreNative(nombreRegion);
        if (regiones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(regiones);
    }

}
