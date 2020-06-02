package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class DeleteMechanic implements Command<Void> {

	private String mechanicId;
	MechanicRepository repo = Factory.repository.forMechanic();

	public DeleteMechanic(String idMecanico) {
		this.mechanicId = idMecanico;
	}

	public Void execute() throws BusinessException {
		validate();
		Optional<Mechanic> m = repo.findById(mechanicId);
		repo.remove(m.get());
		return null;
	}

	private void validate() throws BusinessException {
		Optional<Mechanic> mec = repo.findById(mechanicId);
		if (!mec.isPresent()) {
			throw new BusinessException("The mechanic with id " + mechanicId + " is not in the database");
		}
	}

}
