package webml.prj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webml.prj.entity.Repair;

public interface RepairRepository extends JpaRepository<Repair, Long> {
}
