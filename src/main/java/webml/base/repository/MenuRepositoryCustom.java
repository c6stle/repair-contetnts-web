package webml.base.repository;

import webml.base.entity.Menu;

import java.util.Collection;
import java.util.List;

public interface MenuRepositoryCustom {

    List<Menu> findByVisibleAndViewAuthority(Collection<String> authorityCdList);
    List<Menu> findByViewAuthority(Collection<String> authorityCdList);

}
