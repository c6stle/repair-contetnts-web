package webml.prj.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webml.prj.dto.RepairDto;
import webml.prj.repository.RepairRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RepairService {

    private final RepairRepository repairRepository;

    public long getRepairListCnt() {
        return repairRepository.count();
    }

    public List<RepairDto> getRepairList() {
        return repairRepository.findAll().stream().map(RepairDto::new).collect(Collectors.toList());
    }

}
