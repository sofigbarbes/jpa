package uo.ri.cws.application.repository;

import java.util.List;

import uo.ri.cws.domain.Certificate;

public interface CertificateRepository extends Repository<Certificate> {

	List<Certificate> findByVehicleTypeId(String vehicleTypeId);

	List<Certificate> findByWorkOrderId(String workOrderId);

}
