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


@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    // Crear una nueva sucursal
    @PostMapping
    public ResponseEntity<Sucursal> crearSucursal(@RequestBody Sucursal sucursal) {
        Sucursal nuevaSucursal = sucursalService.createSucursal(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSucursal);
    }

    // Listar todas las sucursales
    @GetMapping
    public ResponseEntity<List<Sucursal>> listarSucursales() {
        List<Sucursal> sucursales = sucursalService.getAllSucursales();
        return ResponseEntity.ok(sucursales);
    }

    // Obtener una sucursal por su ID
    @GetMapping("/sucursales/{id_sucursal}")
    public ResponseEntity<Sucursal> obtenerSucursalPorId(@PathVariable Integer id_sucursal) {
        Sucursal sucursal = sucursalService.getSucursalById(id_sucursal).orElse(null);
        if (sucursal != null) {
            return ResponseEntity.ok(sucursal);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Actualizar una sucursal 
    @PutMapping("/{id_sucursal}")
    public ResponseEntity<Sucursal> actualizarSucursal(@PathVariable Integer id_sucursal, @RequestBody Sucursal sucursal) {
        Sucursal sucursalActualizada = sucursalService.updateSucursal(id_sucursal, sucursal);
        if (sucursalActualizada != null) {
            return ResponseEntity.ok(sucursalActualizada);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Eliminar una sucursal
    @DeleteMapping("/{id_sucursal}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Integer id_sucursal) {
        if (sucursalService.getSucursalById(id_sucursal).isPresent()) {
            sucursalService.eliminarSucursal(id_sucursal);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
