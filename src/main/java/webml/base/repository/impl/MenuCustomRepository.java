package webml.base.repository.impl;

import webml.base.entity.Menu;

import java.util.Collection;
import java.util.List;

public interface MenuCustomRepository {

    List<Menu> findByViewAuthority(Collection<String> authorityCdList);

}
