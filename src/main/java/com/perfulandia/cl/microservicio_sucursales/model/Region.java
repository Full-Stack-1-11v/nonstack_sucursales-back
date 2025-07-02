package com.perfulandia.cl.microservicio_sucursales.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una región en el sistema.
 * <p>
 * Cada región tiene un identificador único y un nombre.
 * </p>
 *
 * <ul>
 *   <li><b>idRegion</b>: Identificador único de la región (clave primaria).</li>
 *   <li><b>nombreRegion</b>: Nombre de la región.</li>
 * </ul>
 *
 * Esta clase está mapeada a la tabla <b>region</b> en la base de datos.
 *
 */
@Entity
@Table(name = "region")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    /**
     * Identificador único de la región.
     */
    @Id
    @Column(name = "id_region")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegion;

    /**
     * Nombre de la región.
     */
    @Column(name = "nombre_region", nullable = false)
    private String nombreRegion;


}
