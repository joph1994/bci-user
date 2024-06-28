package cl.bci.user.domain.repository;

import cl.bci.user.domain.entities.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {
}