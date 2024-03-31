package webml.prj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webml.prj.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
