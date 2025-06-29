package pe.edu.upc.calfinapp.calfin_app.bondManagement.application.internal.CommandService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.Bond;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.CreateBondCommand;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.UpdateBondCommand;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.DeleteBondCommand;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.services.BondCommandService;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.infrastructure.persistence.jpa.repositories.BondRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BondCommandServiceImpl implements BondCommandService {
    private final BondRepository bondRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(BondCommandServiceImpl.class);

    public BondCommandServiceImpl(BondRepository bondRepository) {
        this.bondRepository = bondRepository;
    }

    @Override
    public Bond handle(CreateBondCommand command) {
        var bond = new Bond(command);
        Bond savedBond = bondRepository.save(bond);  // <- esta sí tiene el ID
        return savedBond;
    }

    @Override
    public void handle(DeleteBondCommand command) {
        LOGGER.debug("Iniciando eliminación de bond con ID: {}", command.bondId());

        if (!bondRepository.existsById(command.bondId())) {
            LOGGER.error("No se encontró el bond con ID: {}", command.bondId());
            throw new IllegalArgumentException("Bond does not exist");
        }

        LOGGER.debug("Eliminando bond con ID: {}", command.bondId());
        bondRepository.deleteById(command.bondId());

        LOGGER.debug("Bond eliminado correctamente");
    }

    @Override
    public Optional<Bond> handle(UpdateBondCommand command){
        LOGGER.debug("Iniciando actualización de bond con ID: {}", command.bondId());
        if (!bondRepository.existsById(command.bondId())) {
            LOGGER.error("No se encontró el bond con ID: {}", command.bondId());
            throw new IllegalArgumentException("Bond does not exist");
        }
        return bondRepository.findById(command.bondId())
                .map(bond -> {
                    LOGGER.debug("Bond encontrado: ID={}, EntrepreneurId={}",
                            bond.getId(),
                            bond.getUserId());

                    bond.updateBond(command);

                    LOGGER.debug("Campos de Bond actualizados");

                    // Guardar la publicación actualizada
                    Bond savedBond = bondRepository.save(bond);
                    LOGGER.debug("Bond guardada exitosamente: ID={}", savedBond.getId());

                    return savedBond;
                });
    }
}
