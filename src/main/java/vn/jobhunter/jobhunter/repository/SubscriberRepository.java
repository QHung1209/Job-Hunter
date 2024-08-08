package vn.jobhunter.jobhunter.repository;

import org.springframework.stereotype.Repository;

import vn.jobhunter.jobhunter.domain.Subscriber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>, JpaSpecificationExecutor<Subscriber> {
    boolean existsByName(String name);
}
