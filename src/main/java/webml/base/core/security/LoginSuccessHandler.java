package webml.base.core.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import webml.base.core.constants.SessionConstants;
import webml.base.dto.MenuAuthorityDto;
import webml.base.service.MenuService;

import java.io.IOException;

@Slf4j
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MenuService service;

    public LoginSuccessHandler(MenuService menuService) {
        this.service = menuService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        MemberDetails loginMember = (MemberDetails) authentication.getPrincipal();
        log.info("login member userId : {}", loginMember.getUsername());
        log.info("login member userNm : {}", loginMember.getUserNm());
        log.info("login member authority : {}", authentication.getAuthorities());
        session.setAttribute(SessionConstants.LOGIN_ADMIN, loginMember.getUserNm());

        //MenuAuthority 객체에 메뉴 url 과 권한 세팅
        MenuAuthorityDto.menuAuthorities = service.getMenuList();

        setDefaultTargetUrl("/");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
