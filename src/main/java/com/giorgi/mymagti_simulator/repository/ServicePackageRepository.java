package com.giorgi.mymagti_simulator.repository;

import com.giorgi.mymagti_simulator.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePackageRepository extends JpaRepository<Subscriber, Long> {

}
