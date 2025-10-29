package com.example.ufc.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "peleadores")
public class Fighter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String alias;
    private String categoria; // ej: lightweight, bantamweight...
    private Double pesoKg;
    private Double alturaCm;
    private Double alcanceCm;
    private String nacionalidad;
    private Integer victorias;
    private Integer derrotas;
    private Integer empates;
    private LocalDate fechaNacimiento;

    // getters y setters (o usa Lombok @Data)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Double getPesoKg() { return pesoKg; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }
    public Double getAlturaCm() { return alturaCm; }
    public void setAlturaCm(Double alturaCm) { this.alturaCm = alturaCm; }
    public Double getAlcanceCm() { return alcanceCm; }
    public void setAlcanceCm(Double alcanceCm) { this.alcanceCm = alcanceCm; }
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public Integer getVictorias() { return victorias; }
    public void setVictorias(Integer victorias) { this.victorias = victorias; }
    public Integer getDerrotas() { return derrotas; }
    public void setDerrotas(Integer derrotas) { this.derrotas = derrotas; }
    public Integer getEmpates() { return empates; }
    public void setEmpates(Integer empates) { this.empates = empates; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}
