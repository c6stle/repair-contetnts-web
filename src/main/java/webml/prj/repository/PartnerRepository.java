package webml.prj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webml.prj.entity.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
}
