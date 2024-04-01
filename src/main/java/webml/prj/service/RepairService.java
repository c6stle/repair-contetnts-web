package webml.prj.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webml.base.util.PagingInfo;
import webml.prj.dto.RepairDto;
import webml.prj.dto.RepairSearchDto;
import webml.prj.repository.RepairRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RepairService {

    private final RepairRepository repairRepository;

    public long getRepairListCnt(RepairSearchDto searchDto) {
        return repairRepository.countCond(searchDto);
    }

    public List<RepairDto> getRepairList(PagingInfo pagingInfo, RepairSearchDto searchDto) {
        return repairRepository.searchCond(pagingInfo, searchDto).stream().map(RepairDto::new).collect(Collectors.toList());
    }

}
