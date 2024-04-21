package webml.prj.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import webml.prj.dto.RepairSearchDto;
import webml.prj.repository.PartnerRepository;
import webml.prj.repository.RepairRepository;
import webml.prj.repository.StoreRepository;

@ExtendWith(MockitoExtension.class)
public class RepairServiceTest {

    @InjectMocks
    RepairService repairService;

    @Mock
    RepairRepository repairRepository;
    @Mock
    PartnerRepository partnerRepository;
    @Mock
    StoreRepository storeRepository;

    @Test
    @DisplayName("수리내역 카운트 조회 테스트")
    void get_repair_list_cnt_test() {
        //Mockito.doReturn(new RepairSearchDto(1, null, null, null)).when(repairRepository).countCond(Mockito.any(RepairSearchDto.class));
        long repairListCnt = repairService.getRepairListCnt(new RepairSearchDto(1, null, null, null));
        Assertions.assertThat(repairListCnt).isEqualTo(0);

        Mockito.verify(repairRepository, Mockito.times(1)).countCond(Mockito.any(RepairSearchDto.class));
    }


}
