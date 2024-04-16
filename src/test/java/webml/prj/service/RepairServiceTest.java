package webml.prj.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import webml.base.util.PagingUtil;
import webml.prj.dto.RepairSearchDto;
import webml.prj.entity.Manager;
import webml.prj.entity.Partner;
import webml.prj.entity.Repair;
import webml.prj.entity.Store;
import webml.prj.repository.ManagerRepository;
import webml.prj.repository.PartnerRepository;
import webml.prj.repository.RepairRepository;
import webml.prj.repository.StoreRepository;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
class RepairServiceTest {

    @Autowired
    RepairRepository repairRepository;
    @Autowired
    PartnerRepository partnerRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    ManagerRepository managerRepository;

    @Test
    @DisplayName("수리내역 등록 테스트")
    void repair_reg_get_test() {
        //given
        Repair repair = reg_repair_one();
        //when
        Repair byId = repairRepository.findById(repair.getRepairIdx()).orElseThrow();
        //then
        Assertions.assertThat(byId).isEqualTo(repair);
    }

    @Test
    @DisplayName("수리내역 페이징 테스트")
    void repair_paging_test() {
        //given
        RepairSearchDto searchDto = new RepairSearchDto(1, null, null, null);
        int pageNum = searchDto.getPageNum() == null ? 1 : searchDto.getPageNum();
        long totalCnt = repairRepository.countCond(searchDto);
        PagingUtil pagingUtil = new PagingUtil(totalCnt, pageNum, 20); //paging 20개

        //when
        List<Repair> repairs = repairRepository.searchCond(searchDto, pagingUtil);

        //then
        Assertions.assertThat(repairs.size()).isEqualTo(20);
    }

    @Test
    @DisplayName("수리내역 마지막 페이지 테스트")
    void repair_last_page_test() {
        //given
        RepairSearchDto searchDto = new RepairSearchDto(1, null, null, null);
        long totalCnt = repairRepository.countCond(searchDto);
        int pageNum = (int) (totalCnt / 20) + 1;
        PagingUtil pagingUtil = new PagingUtil(totalCnt, pageNum, 20); //paging 20개

        //when
        List<Repair> repairs = repairRepository.searchCond(searchDto, pagingUtil);

        //then
        Assertions.assertThat(repairs.size()).isEqualTo((int) totalCnt % 20);
    }

    @Test
    @DisplayName("수리내역 검색조건 테스트")
    void repair_search_condition_test() {
        //given
        Repair repair = reg_repair_one();
        LocalDate localDate = LocalDate.now();

        //date 검색조건 테스트
        RepairSearchDto searchDto = new RepairSearchDto(1, String.valueOf(localDate), String.valueOf(localDate), null);
        //partnerIdx 검색조건 테스트
        RepairSearchDto searchDto2 = new RepairSearchDto(1, null, null, repair.getPartner().getPartnerIdx());

        //when
        List<Repair> repairs = repairRepository.searchCond(searchDto, null);
        List<Repair> repairs2 = repairRepository.searchCond(searchDto2, null);

        //then
        Assertions.assertThat(repairs.get(0).getReceiveDt()).isEqualTo(localDate);
        Assertions.assertThat(repairs2.get(0).getPartner()).isEqualTo(repair.getPartner());
    }

    @Test
    @DisplayName("수리내역 삭제 테스트")
    void repair_delete_test() {
        //given
        Repair repair = reg_repair_one();

        //when
        repairRepository.deleteById(repair.getRepairIdx());

        //then
        Assertions.assertThatRuntimeException().isThrownBy(() -> {
            repairRepository.findById(repair.getRepairIdx()).orElseThrow(() -> new RuntimeException("error"));
        });
    }

    private Repair reg_repair_one() {
        Partner partner = Partner.builder()
                .partnerNm("다미아니")
                .build();
        partnerRepository.save(partner);

        Manager manager = Manager.builder()
                .managerMail("abc@abc.com")
                .partner(partner)
                .build();
        managerRepository.save(manager);

        Store store = Store.builder()
                .storeNm("갤러리아")
                .storeAddress("서울 송파구")
                .build();
        storeRepository.save(store);

        Repair repair = Repair.builder()
                .partner(partner)
                .store(store)
                .receiveDt(LocalDate.now())
                .productVal("DDDD")
                .specificVal("JB-0001")
                .repairContents("인그레빙")
                .price(40000L)
                .build();
        repairRepository.save(repair);
        return repair;
    }


}