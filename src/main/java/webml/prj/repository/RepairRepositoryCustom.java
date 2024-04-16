package webml.prj.repository;

import com.querydsl.core.Tuple;
import webml.base.util.PagingUtil;
import webml.prj.dto.RepairSearchDto;
import webml.prj.entity.Repair;

import java.util.List;

public interface RepairRepositoryCustom {

    List<Tuple> statistics();

    long countCond(RepairSearchDto searchDto);

    List<Repair> searchCond(RepairSearchDto searchDto, PagingUtil pagingUtil);
}
