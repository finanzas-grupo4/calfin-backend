package com.calfin.calfin_app.bondManagement.domain.model.aggregates;

import com.calfin.calfin_app.bondManagement.domain.model.entities.CashFlow;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CashFlows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //relacion con bond
    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private Bond bond;

    private Integer precioTeorico;

    private Double convexidad;

    private Double duracion;

    private Double duracionModificada;

    private Double tcea;

    private Double trea;

    private Double precioMaximo;

    @OneToMany(mappedBy = "cashFlows", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<CashFlow> flows = new ArrayList<>();

    public CashFlows(Bond bond) {
        this.bond = bond;
        this.flows = new ArrayList<>();
    }

}
