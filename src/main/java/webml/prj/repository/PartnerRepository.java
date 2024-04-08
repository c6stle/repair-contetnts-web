package webml.prj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webml.prj.entity.Partner;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    List<Partner> findByDelYn(String delYn);
}
