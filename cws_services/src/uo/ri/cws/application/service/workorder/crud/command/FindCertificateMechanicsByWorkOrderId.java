package uo.ri.cws.application.service.workorder.crud.command;

import java.util.List;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.util.command.Command;

public class FindCertificateMechanicsByWorkOrderId
		implements Command<List<MechanicDto>> {

	// private String mechanicId;

	public FindCertificateMechanicsByWorkOrderId(String id) {
		// this.mechanicId = id;
	}

	@Override
	public List<MechanicDto> execute() throws BusinessException
	{
		throw new BusinessException("NOT IMPLEMENTED");
		//return null;

	}

}
