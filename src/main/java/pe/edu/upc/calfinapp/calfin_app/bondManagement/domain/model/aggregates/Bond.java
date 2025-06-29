package pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.calfinapp.calfin_app.iam.domain.model.aggregates.User;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.CreateBondCommand;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.UpdateBondCommand;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.valueObjects.*;
import pe.edu.upc.calfinapp.calfin_app.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
public class Bond extends AuditableAbstractAggregateRoot<Bond> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    private String bondName;

    //costos como comisiones, etc.
    @NotNull
    private Float additionalCosts;

    @NotNull
    private Float nominalValue;

    @NotNull
    private Currency currency;

    @NotNull
    private Integer term; //plazo

    @NotNull
    private TermUnits termUnit;

    //intereses
    @NotNull
    private Float interestRate; //tasa de interes

    @NotNull
    private boolean isEffectiveRate; //es tasa efectiva?

    @NotNull
    private PaymentFrequency paymentFrequency;

    //solo si es efectiva
    @NotNull
    private CompoundingFrequency compoundingFrequency; //frecuencia de capitalización

    //fechas y periodos de gracia
    @NotNull
    private LocalDate issueDate; //fecha de emisión

    @NotNull
    private boolean hasGracePeriod;

    @NotNull
    private GracePeriodType gracePeriodType; //tipo de periodo de gracia

    @NotNull
    private Integer gracePeriodLength;

    @NotNull
    private Float discountRate; //tasa de descuento

    //-----------------

    @NotNull
    private Long userId;

    //----------------
    public Bond() {
    }

    public Bond(CreateBondCommand command){
        this.userId = command.userId();
        this.bondName = command.bondName();
        this.additionalCosts = command.additionalCosts();
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
    }

    public void updateBond(UpdateBondCommand command) {
        this.bondName = command.bondName();
        this.additionalCosts = command.additionalCosts();
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
    }
}
