package webml.base.core.dummy;

import lombok.RequiredArgsConstructor;
import webml.base.core.constants.AuthorityConstants;
import webml.base.entity.Authority;
import webml.base.entity.Menu;
import webml.base.repository.AuthorityRepository;
import webml.base.repository.MenuRepository;
import webml.prj.repository.ManagerRepository;
import webml.prj.repository.PartnerRepository;
import webml.prj.repository.RepairRepository;
import webml.prj.repository.StoreRepository;

import java.security.NoSuchAlgorithmException;

//@Component
@RequiredArgsConstructor
public class RepairContentDummy {

    private final PartnerRepository partnerRepository;

    private final StoreRepository storeRepository;

    private final RepairRepository repairRepository;

    private final ManagerRepository managerRepository;

    private final AuthorityRepository authorityRepository;

    private final MenuRepository menuRepository;

    //@PostConstruct
    public void repairContent() throws NoSuchAlgorithmException {

        Authority 일반사용자 = authorityRepository.findByAuthorityCd(AuthorityConstants.AUTH_001);
        Authority 일반관리자 = authorityRepository.findByAuthorityCd(AuthorityConstants.AUTH_002);
        Authority 시스템관리자 = authorityRepository.findByAuthorityCd(AuthorityConstants.AUTH_003);

        //초기메뉴등록
        Menu repair = Menu.builder().menuNm("수리내역")
                .menuOrder(3)
                .menuLink("/repair")
                .viewAuthority(일반사용자.getAuthorityCd())
                .saveAuthority(일반관리자.getAuthorityCd())
                .visibleLinkYn("Y")
                .build();
        menuRepository.save(repair);

        Menu partner = Menu.builder().menuNm("거래처")
                .menuOrder(4)
                .menuLink("/partner")
                .viewAuthority(일반사용자.getAuthorityCd())
                .saveAuthority(일반관리자.getAuthorityCd())
                .visibleLinkYn("Y")
                .build();
        menuRepository.save(partner);

        Menu store = Menu.builder().menuNm("매장")
                .menuOrder(5)
                .menuLink("/store")
                .viewAuthority(일반사용자.getAuthorityCd())
                .saveAuthority(일반관리자.getAuthorityCd())
                .visibleLinkYn("Y")
                .build();
        menuRepository.save(store);

        //메인화면
        Menu main = Menu.builder().menuNm("메인(Home)")
                .menuOrder(6)
                .menuLink("/main")
                .viewAuthority(일반사용자.getAuthorityCd())
                .saveAuthority(일반관리자.getAuthorityCd())
                .visibleLinkYn("N")
                .build();
        menuRepository.save(store);

        //Partner partner = Partner.builder()
        //        .partnerIdx(1L)
        //        .partnerNm("다미아니")
        //        .build();
        //partnerRepository.save(partner);
        //
        //Manager manager = Manager.builder()
        //        .managerIdx(1L)
        //        .managerMail("abc@abc.com")
        //        .partner(partner)
        //        .build();
        //managerRepository.save(manager);
        //
        //Store store = Store.builder()
        //        .storeIdx(1L)
        //        .storeNm("갤러리아")
        //        .storeAddress("서울 송파구")
        //        .build();
        //
        //storeRepository.save(store);
        //
        //Store store2 = Store.builder()
        //        .storeIdx(2L)
        //        .storeNm("대구 현대백화점")
        //        .storeAddress("대구 중구")
        //        .build();
        //
        //storeRepository.save(store2);
        //
        //for (int i = 0; i < 106; i++) {
        //    Repair repair = Repair.builder()
        //            .repairIdx(Long.parseLong(String.valueOf(i)))
        //            .repairContents("인그레빙, 폴리싱")
        //            .receiveDt(LocalDate.now())
        //            .partner(partner)
        //            .store(store)
        //            .productVal("AAAA")
        //            .specificVal("JB-1250BBBB")
        //            .price(Long.parseLong(String.valueOf(60000)))
        //            .build();
        //    repairRepository.save(repair);
        //}
        //for (int i = 107; i < 209; i++) {
        //    Repair repair = Repair.builder()
        //            .repairIdx(Long.parseLong(String.valueOf(i)))
        //            .repairContents("사이즈 조절")
        //            .receiveDt(LocalDate.now())
        //            .partner(partner)
        //            .store(store2)
        //            .productVal("AAAA")
        //            .specificVal("JB-1250BBBB")
        //            .price(Long.parseLong(String.valueOf(50000)))
        //            .build();
        //    repairRepository.save(repair);
        //}
    }
}
