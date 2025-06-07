package com.calfin.calfin_app.bondManagement.domain.model.aggregates;

import com.calfin.calfin_app.IAM.domain.model.aggregates.Users;
import com.calfin.calfin_app.bondManagement.domain.model.commands.CreateBondCommand;
import com.calfin.calfin_app.bondManagement.domain.model.valueObjects.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Bond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bondName;

    private Float nominalValue;

    private Currency currency;

    private Integer term; //plazo

    private TermUnits termUnit;

    //intereses

    private Float interestRate; //tasa de interes

    private boolean isEffectiveRate; //es tasa efectiva?

    private PaymentFrequency paymentFrequency;

    //solo si es efectiva
    private CompoundingFrequency compoundingFrequency; //frecuencia de capitalización

    //fechas y periodos de gracia
    private Date issueDate; //fecha de emisión

    private boolean hasGracePeriod;

    private GracePeriodType gracePeriodType; //tipo de periodo de gracia

    private Integer gracePeriodLength;

    private Float discountRate; //tasa de descuento

    //-----------------

    @ManyToOne
    private Users user;

    //----------------

    public Bond(CreateBondCommand command){
        this.bondName = command.bondName();
        this.nominalValue = command.nominalValue();
        this.currency = command.currency();
        this.term = command.term();
        this.termUnit = command.termUnit();
        this.interestRate = command.interestRate();
        this.isEffectiveRate = command.isEffectiveRate();
        this.paymentFrequency = command.paymentFrequency();
        this.compoundingFrequency = command.compoundingFrequency();
        this.issueDate = command.issueDate();
        this.hasGracePeriod = command.hasGracePeriod();
        this.gracePeriodType = command.gracePeriodType();
        this.gracePeriodLength = command.gracePeriodLength();
        this.discountRate = command.discountRate();

        this.user = command.user();
    }

}
