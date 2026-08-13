package com.giorgi.mymagti_simulator.repository;

import com.giorgi.mymagti_simulator.entity.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicePackageRepository extends JpaRepository<ServicePackage, Long> {

}
