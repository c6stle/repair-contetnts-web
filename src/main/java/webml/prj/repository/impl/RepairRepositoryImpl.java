package webml.prj.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import webml.base.util.PagingInfo;
import webml.prj.dto.RepairSearchDto;
import webml.prj.entity.QPartner;
import webml.prj.entity.QRepair;
import webml.prj.entity.QStore;
import webml.prj.entity.Repair;
import webml.prj.repository.RepairRepositoryCustom;

import java.util.List;

public class RepairRepositoryImpl implements RepairRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public RepairRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public long countCond(RepairSearchDto searchDto) {
        return jpaQueryFactory
                .select(QRepair.repair.count())
                .from(QRepair.repair)
                .where(partnerEq(searchDto.getPartnerIdx()),
                        receiveDtBetween(searchDto.getBeginDt(), searchDto.getEndDt()))
                .fetchFirst();
    }

    @Override
    public List<Repair> searchCond(PagingInfo pagingInfo, RepairSearchDto searchDto) {
        return jpaQueryFactory
                .selectFrom(QRepair.repair)
                .leftJoin(QRepair.repair.partner, QPartner.partner).fetchJoin()
                .leftJoin(QRepair.repair.store, QStore.store).fetchJoin()
                .where(partnerEq(searchDto.getPartnerIdx()),
                        receiveDtBetween(searchDto.getBeginDt(), searchDto.getEndDt()))
                .offset(pagingInfo.getOffset())
                .limit(pagingInfo.getPageSize())
                .orderBy(QRepair.repair.receiveDt.desc())
                .fetch();
    }

    @Override
    public List<Repair> searchDownloadExcel(RepairSearchDto searchDto) {
        return jpaQueryFactory
                .selectFrom(QRepair.repair)
                .leftJoin(QRepair.repair.partner, QPartner.partner).fetchJoin()
                .leftJoin(QRepair.repair.store, QStore.store).fetchJoin()
                .where(partnerEq(searchDto.getPartnerIdx()),
                        receiveDtBetween(searchDto.getBeginDt(), searchDto.getEndDt()))
                .orderBy(QRepair.repair.store.storeNm.asc(), QRepair.repair.receiveDt.asc())
                .fetch();
    }

    private Predicate receiveDtBetween(String beginDt, String endDt) {
        if ((beginDt == null || endDt == null) || ("".equals(beginDt) || "".equals(endDt))) {
            return null;
        } else {
            //StringExpression format = Expressions.stringTemplate("FUNCTION('TO_CHAR', {0}, 'yyyy-MM-dd HH:mm:ss')", QRepair.repair.receiveDt);
            //beginDt = beginDt + " 00:00:00";
            //endDt = endDt + " 23:59:59";
            StringExpression format = Expressions.stringTemplate("FUNCTION('TO_CHAR', {0}, 'yyyy-MM-dd')", QRepair.repair.receiveDt);
            return format.between(beginDt, endDt);
        }
    }

    private Predicate partnerEq(Long partnerIdx) {
        return partnerIdx != null ? QPartner.partner.partnerIdx.eq(partnerIdx) : null;
    }
}
