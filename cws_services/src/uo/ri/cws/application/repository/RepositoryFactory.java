package uo.ri.cws.application.repository;

public interface RepositoryFactory {

	MechanicRepository forMechanic();

	WorkOrderRepository forWorkOrder();

	PaymentMeanRepository forPaymentMean();

	InvoiceRepository forInvoice();

	ClientRepository forClient();

	SparePartRepository forSparePart();

	InterventionRepository forIntervention();

	VehicleRepository forVehicle();

	VehicleTypeRepository forVehicleType();

	CertificateRepository forCertificate();

	CourseRepository forCourse();

	EnrollmentRepository forEnrollment();

}
