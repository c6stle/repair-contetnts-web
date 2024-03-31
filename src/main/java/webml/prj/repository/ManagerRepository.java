package webml.prj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webml.prj.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
