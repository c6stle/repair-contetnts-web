package webml.prj.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webml.base.core.exception.MessageException;
import webml.base.util.PagingInfo;
import webml.prj.dto.RepairDto;
import webml.prj.dto.RepairSearchDto;
import webml.prj.entity.Partner;
import webml.prj.entity.Repair;
import webml.prj.entity.Store;
import webml.prj.repository.PartnerRepository;
import webml.prj.repository.RepairRepository;
import webml.prj.repository.StoreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RepairService {

    private final PartnerRepository partnerRepository;

    private final StoreRepository storeRepository;

    private final RepairRepository repairRepository;

    public long getRepairListCnt(RepairSearchDto searchDto) {
        return repairRepository.countCond(searchDto);
    }

    public List<RepairDto> getRepairList(PagingInfo pagingInfo, RepairSearchDto searchDto) {
        return repairRepository.searchCond(pagingInfo, searchDto).stream().map(RepairDto::new).collect(Collectors.toList());
    }

    public RepairDto getRepairInfo(Long repairIdx) {
        return repairRepository.findById(repairIdx).map(RepairDto::new).orElseThrow(() -> new MessageException("선택한 내역을 찾을 수 없습니다."));
    }

    @Transactional
    public void regRepair(RepairDto repairRegDto) {
        Partner partner = partnerRepository.findById(repairRegDto.getPartnerIdx()).orElseThrow(() -> new MessageException("선택한 거래처를 찾을 수 없습니다."));
        Store store = storeRepository.findById(repairRegDto.getStoreIdx()).orElseThrow(() -> new MessageException("선택한 매장을 찾을 수 없습니다."));

        Repair repair = Repair.builder()
                .partner(partner)
                .receiveDt(repairRegDto.getReceiveDt())
                .productVal(repairRegDto.getProductVal())
                .specificVal(repairRegDto.getSpecificVal())
                .repairContents(repairRegDto.getRepairContents())
                .price(repairRegDto.getPrice())
                .store(store)
                .build();
        repairRepository.save(repair);
    }

    @Transactional
    public void updateRepair(RepairDto repairUpdateDto) {
        Repair repair = repairRepository.findById(repairUpdateDto.getRepairIdx()).orElseThrow(() -> new MessageException("선택한 내역을 찾을 수 없습니다."));
        Partner partner = partnerRepository.findById(repairUpdateDto.getPartnerIdx()).orElseThrow(() -> new MessageException("선택한 거래처를 찾을 수 없습니다."));
        Store store = storeRepository.findById(repairUpdateDto.getStoreIdx()).orElseThrow(() -> new MessageException("선택한 매장을 찾을 수 없습니다."));

        repair.update(partner, repairUpdateDto.getReceiveDt(), repairUpdateDto.getProductVal(), repairUpdateDto.getSpecificVal(),
                repairUpdateDto.getRepairContents(), repairUpdateDto.getPrice(), store);
    }

    @Transactional
    public void deleteRepair(Long repairIdx) {
        repairRepository.deleteById(repairIdx);
    }

    public List<RepairDto> downloadRepairList(RepairSearchDto searchDto) {
        return repairRepository.searchDownloadExcel(searchDto).stream().map(RepairDto::new).collect(Collectors.toList());
    }
}
