package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.CertificateRepository;
import uo.ri.cws.domain.Certificate;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class CertificateJpaRepository extends BaseJpaRepository<Certificate>
		implements CertificateRepository {

	@Override
	public List<Certificate> findByVehicleTypeId(String vehicleTypeId)
	{
		return Jpa.getManager()
				.createNamedQuery("Certificate.findByVehicleTypeId",
						Certificate.class)
				.setParameter(1, vehicleTypeId).getResultList();
	}

	@Override
	public List<Certificate> findByWorkOrderId(String workOrderId)
	{
		return Jpa.getManager()
				.createNamedQuery("Certificate.findByWorkOrderId",
						Certificate.class)
				.setParameter(1, workOrderId).getResultList();
	}

	@Override
	public Optional<Certificate> findByMechanicAndVehicleType(String mecId,
			String vtId)
	{
		return Jpa.getManager()
				.createNamedQuery("Certificate.findByMechanicAndVehicleType",
						Certificate.class)
				.setParameter(1, mecId).setParameter(2, vtId).getResultStream()
				.findFirst();
	}

}
