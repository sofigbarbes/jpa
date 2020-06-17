package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Certificate;

public interface CertificateRepository extends Repository<Certificate> {

	List<Certificate> findByVehicleTypeId(String vehicleTypeId);

	/**
	 * Finds the certificate for that workorder:
	 * - Find the vehicle type of the workorder,
	 * - Find certificates for that vehicletype
	 * 
	 * @param workOrderId
	 * @return certificates for that workorder
	 */
	List<Certificate> findByWorkOrderId(String workOrderId);

	Optional<Certificate> findByMechanicAndVehicleType(String idMechanic,
			String idVehicleType);

}
