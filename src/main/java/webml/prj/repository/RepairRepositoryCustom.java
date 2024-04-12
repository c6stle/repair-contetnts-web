package webml.prj.repository;

import webml.base.util.PagingInfo;
import webml.prj.dto.RepairSearchDto;
import webml.prj.entity.Repair;

import java.util.List;

public interface RepairRepositoryCustom {

    long countCond(RepairSearchDto searchDto);

    List<Repair> searchCond(PagingInfo pagingInfo, RepairSearchDto searchDto);

    List<Repair> searchDownloadExcel(RepairSearchDto searchDto);
}
