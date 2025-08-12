package com.perfulandia.cl.microservicio_sucursales.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una sucursal en el sistema.
 * <p>
 * Cada sucursal tiene un identificador único, un nombre y está asociada a una {@link Ciudad}.
 * </p>
 *
 * <ul>
 *   <li><b>idSucursal</b>: Identificador único de la sucursal (clave primaria).</li>
 *   <li><b>nombreSucursal</b>: Nombre de la sucursal.</li>
 *   <li><b>ciudad</b>: Ciudad a la que pertenece la sucursal.</li>
 * </ul>
 *
 * Esta clase está mapeada a la tabla <b>sucursal</b> en la base de datos.
 *
 */
@Entity
@Table(name = "sucursal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sucursal {

    /**
     * Identificador único de la sucursal.
     */
    @Id
    @Column(name = "id_sucursal")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSucursal;

    /**
     * Nombre de la sucursal.
     */
    @Column(name = "nombre_sucursal", nullable = false)
    private String nombreSucursal;

    /**
     * Ciudad a la que pertenece la sucursal.
     */
    @ManyToOne
    @JoinColumn(name = "id_ciudad", nullable = false)
    private Ciudad ciudad;
}
