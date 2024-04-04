package webml.base.core.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import webml.base.core.exception.MessageException;
import webml.base.dto.MenuAuthorityDto;
import webml.base.dto.MenuDto;

@Slf4j
public class AuthorityCheckFilter {

    public static boolean check(Authentication authentication, HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        log.info("AuthorityCheckFilter.check requestUri : {}", requestUri);

        if (authentication.getName().equals("anonymousUser")) {
            log.info("AuthorityCheckFilter.check authentication is null");
            return false;
        }

        //request method 가 get 이 아닌 요청에 대해 필터 적용
        if (!request.getMethod().equals("GET")) {
            String nowUrl = request.getHeader("nowURL"); //비동기(ajax) 접근방식만 허용됨
            if (nowUrl == null) {
                log.info("AuthorityCheckFilter.check nowUrl is null");
                throw new MessageException("잘못된 접근입니다.");
            }
            log.info("AuthorityCheckFilter.check nowUrl : {}", nowUrl);
            MemberDetails loginAdmin = (MemberDetails) authentication.getPrincipal();
            for (MenuDto menuDto: MenuAuthorityDto.menuAuthorities) {
                //TODO : nowUrl.contains(menuDto.getMenuLink()) 임시조치, 이전 URL 에서 헤더에 담아 이동하는 방식으로 변경필요
                if (menuDto.getMenuLink() != null && nowUrl.contains(menuDto.getMenuLink())) {
                    log.info("AuthorityCheckFilter.check loginAdmin.getAuthoritiesStrArr() : {}", loginAdmin.getAuthoritiesStrArr());
                    log.info("AuthorityCheckFilter.check menuDTO.getSaveAuthority() : {}", menuDto.getSaveAuthority());
                    if (loginAdmin.getAuthoritiesStrArr().contains(menuDto.getSaveAuthority())) {
                        return true;
                    } else {
                        throw new MessageException("등록/수정/삭제 권한이 없습니다.");
                    }
                }
            }
        }
        return true;
    }
}
