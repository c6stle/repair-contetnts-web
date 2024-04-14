package webml.base.repository;

import webml.base.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
    Authority findByAuthorityCd(String authorityCd);
}
