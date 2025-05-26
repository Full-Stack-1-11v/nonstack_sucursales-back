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

    @GetMapping("/{id_region}")
    public ResponseEntity<Region> obtenerRegionPorId(@PathVariable Integer id_region) {
        Region region = regionService.getRegionById(id_region);
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

    @PutMapping("/{id_region}")
    public ResponseEntity<Region> actualizarRegion(@PathVariable Integer id_region, @RequestBody Region region) {
        Region regionActualizada = regionService.updateRegion(id_region, region);
        if (regionActualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(regionActualizada);
    }


    @PatchMapping("/{id_region}")
    public ResponseEntity<Region> actualizarRegionParcial(@PathVariable Integer id_region, @RequestBody Region region) {
        Region regionActualizada = regionService.patchRegion(id_region, region);
        if (regionActualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(regionActualizada);
    }

    @DeleteMapping("/{id_region}")
    public ResponseEntity<Void> eliminarRegion(@PathVariable Integer id_region) {
        if (regionService.getRegionById(id_region) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        regionService.deleteById(id_region);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/buscar/jpql/{nombre_region}")
    public ResponseEntity<List<Region>> buscarRegionPorNombre(@PathVariable String nombre_region) {
        List<Region> regiones = regionService.buscarRegionPorNombreJPQL(nombre_region);
        if (regiones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(regiones);
    }

    @GetMapping("/buscar/native/{nombre_region}")
    public ResponseEntity<List<Region>> buscarRegionPorNombreNative(@PathVariable String nombre_region) {
        List<Region> regiones = regionService.buscarRegionPorNombreNative(nombre_region);
        if (regiones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(regiones);
    }

}
