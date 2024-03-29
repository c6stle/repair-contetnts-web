package webml.base.core.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import webml.base.core.constants.AuthorityConstants;
import webml.base.entity.Authority;
import webml.base.entity.Member;
import webml.base.entity.Menu;
import webml.base.repository.AuthorityRepository;
import webml.base.repository.MemberRepository;
import webml.base.repository.MenuRepository;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

//@Component
@RequiredArgsConstructor
public class InitAuthority {

    private final AuthorityRepository authorityRepository;

    private final MenuRepository menuRepository;

    private final MemberRepository memberRepository;

    //@PostConstruct
    public void initAuthority() throws NoSuchAlgorithmException {

        //기본권한등록
        Authority 시스템관리자 = Authority.builder().authorityCd(AuthorityConstants.AUTH_003).authorityNm("시스템관리자").build();
        Authority 일반관리자 = Authority.builder().authorityCd(AuthorityConstants.AUTH_002).authorityNm("일반관리자").build();
        Authority 일반사용자 = Authority.builder().authorityCd(AuthorityConstants.AUTH_001).authorityNm("일반사용자").build();

        authorityRepository.save(시스템관리자);
        authorityRepository.save(일반관리자);
        authorityRepository.save(일반사용자);

        //초기메뉴등록
        Menu menu = Menu.builder().menuNm("메뉴관리")
                .menuOrder(1)
                .menuLink("/menu")
                .viewAuthority(일반관리자.getAuthorityCd())
                .saveAuthority(시스템관리자.getAuthorityCd())
                .build();
        menuRepository.save(menu);

        Menu authority = Menu.builder().menuNm("권한관리")
                .menuOrder(2)
                //.menuLink("/authority/member")
                .viewAuthority(일반관리자.getAuthorityCd())
                .saveAuthority(시스템관리자.getAuthorityCd())
                .build();
        menuRepository.save(authority);

        Menu authorityAdmin = Menu.builder().menuNm("관리자/관리자권한관리")
                .menuOrder(1)
                .menuLink("/authority/member")
                .viewAuthority(일반관리자.getAuthorityCd())
                .saveAuthority(시스템관리자.getAuthorityCd())
                .parent(authority)
                .build();
        menuRepository.save(authorityAdmin);

        Menu authorityMenu = Menu.builder().menuNm("메뉴권한관리")
                .menuOrder(2)
                .menuLink("/authority/menu")
                .viewAuthority(일반관리자.getAuthorityCd())
                .saveAuthority(시스템관리자.getAuthorityCd())
                .parent(authority)
                .build();
        menuRepository.save(authorityMenu);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member admin = Member.builder().userId("admin")
                .userNm("최고관리자")
                .password(passwordEncoder.encode("admin"))
                .lastLoginDt(LocalDateTime.now())
                .authority(시스템관리자).build();
        memberRepository.save(admin);
    }

}
