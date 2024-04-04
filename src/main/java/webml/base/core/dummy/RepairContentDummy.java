package webml.base.core.dummy;

import lombok.RequiredArgsConstructor;
import webml.prj.entity.Partner;
import webml.prj.entity.Repair;
import webml.prj.entity.Store;
import webml.prj.repository.PartnerRepository;
import webml.prj.repository.RepairRepository;
import webml.prj.repository.StoreRepository;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

//@Component
@RequiredArgsConstructor
public class RepairContentDummy {

    private final PartnerRepository partnerRepository;

    private final StoreRepository storeRepository;

    private final RepairRepository repairRepository;

    //@PostConstruct
    public void repairContent() throws NoSuchAlgorithmException {

        Partner partner = Partner.builder()
                .partnerIdx(1L)
                .partnerNm("다미아니")
                .build();

        partnerRepository.save(partner);

        Store store = Store.builder()
                .storeIdx(1L)
                .storeNm("갤러리아")
                .storeAddress("서울 송파구")
                .build();

        storeRepository.save(store);

        Store store2 = Store.builder()
                .storeIdx(2L)
                .storeNm("대구 현대백화점")
                .storeAddress("대구 중구")
                .build();

        storeRepository.save(store2);

        for (int i = 0; i < 106; i++) {
            Repair repair = Repair.builder()
                    .repairIdx(Long.parseLong(String.valueOf(i)))
                    .repairContents("인그레빙, 폴리싱")
                    .receiveDt(LocalDate.now())
                    .delYn("N")
                    .partner(partner)
                    .store(store)
                    .productVal("AAAA")
                    .specificVal("JB-1250BBBB")
                    .price(Long.parseLong(String.valueOf(60000)))
                    .build();
            repairRepository.save(repair);
        }
        for (int i = 107; i < 209; i++) {
            Repair repair = Repair.builder()
                    .repairIdx(Long.parseLong(String.valueOf(i)))
                    .repairContents("사이즈 조절")
                    .receiveDt(LocalDate.now())
                    .delYn("N")
                    .partner(partner)
                    .store(store2)
                    .productVal("AAAA")
                    .specificVal("JB-1250BBBB")
                    .price(Long.parseLong(String.valueOf(50000)))
                    .build();
            repairRepository.save(repair);
        }



    }
}
