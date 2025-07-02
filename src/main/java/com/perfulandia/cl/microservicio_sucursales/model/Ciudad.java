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
 * Entidad que representa una ciudad en el sistema.
 * <p>
 * Cada ciudad tiene un identificador único, un nombre y está asociada a una {@link Region}.
 * </p>
 * 
 * <ul>
 *   <li><b>idCiudad</b>: Identificador único de la ciudad (clave primaria).</li>
 *   <li><b>nombreCiudad</b>: Nombre de la ciudad.</li>
 *   <li><b>region</b>: Región a la que pertenece la ciudad.</li>
 * </ul>
 * 
 * Esta clase está mapeada a la tabla <b>ciudad</b> en la base de datos.
 * 
 */
@Entity
@Table(name = "ciudad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ciudad {

    /**
     * Identificador único de la ciudad.
     */
    @Id
    @Column(name = "id_ciudad")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCiudad;

    /**
     * Nombre de la ciudad.
     */
    @Column(name = "nombre_ciudad", nullable = false)
    private String nombreCiudad;

    /**
     * Región a la que pertenece la ciudad.
     */
    @ManyToOne
    @JoinColumn(name = "id_region", nullable = false)
    private Region region;

}