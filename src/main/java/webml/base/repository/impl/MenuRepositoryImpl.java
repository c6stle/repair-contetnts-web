package webml.base.repository.impl;

import webml.base.entity.Menu;
import webml.base.entity.QMenu;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import webml.base.repository.MenuRepositoryCustom;

import java.util.Collection;
import java.util.List;

import static webml.base.entity.QMenu.menu;

public class MenuRepositoryImpl implements MenuRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MenuRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Menu> findByVisibleAndViewAuthority(Collection<String> authorityCdList) {
        //self join
        QMenu menuChild = new QMenu("menuChild");

        return jpaQueryFactory
                .selectFrom(menu)
                .leftJoin(menu.childMenu, menuChild).fetchJoin()
                .where(menu.parent.isNull(),
                        menu.viewAuthority.in(authorityCdList),
                        menu.visibleLinkYn.eq("Y"),
                        menuChild.viewAuthority.in(authorityCdList).or(menuChild.isNull()))
                .orderBy(menu.menuOrder.asc())
                .fetch();
    }

    @Override
    public List<Menu> findByViewAuthority(Collection<String> authorityCdList) {
        //self join
        QMenu menuChild = new QMenu("menuChild");

        return jpaQueryFactory
                .selectFrom(menu)
                .leftJoin(menu.childMenu, menuChild).fetchJoin()
                .where(menu.parent.isNull(),
                        menu.viewAuthority.in(authorityCdList),
                        menuChild.viewAuthority.in(authorityCdList).or(menuChild.isNull()))
                .orderBy(menu.menuOrder.asc())
                .fetch();
    }
}
