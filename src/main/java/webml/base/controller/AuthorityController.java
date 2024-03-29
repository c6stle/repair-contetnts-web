package webml.base.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import webml.base.core.exception.MessageException;
import webml.base.core.util.CustomMap;
import webml.base.dto.MemberDto;
import webml.base.dto.NewMemberDto;
import webml.base.service.AuthorityService;
import webml.base.service.MemberService;
import webml.base.service.MenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 권한관리 (관리자 권한 관리, 메뉴 권한 일괄관리)
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/authority")
public class AuthorityController {

    private final AuthorityService authorityService;

    private final MemberService memberService;

    private final MenuService menuService;

    /**
     * 관리자 목록 조회
     */
    @GetMapping("/member")
    public String getMemberList(Model model) throws Exception {
        try {
            model.addAttribute("members", memberService.getMemberList());
            model.addAttribute("authorities", authorityService.getAuthorities());
        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e);
        }
        return "auth/auth_mng";
    }

    /**
     * 관리자 정보 조회
     */
    @ResponseBody
    @GetMapping("/member/{memberIdx}")
    public Map<String, Object> getMemberInfo(@PathVariable Long memberIdx) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            rtnMap.put("memberInfo", memberService.getMemberInfo(memberIdx));
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    /**
     * 관리자 신규등록
     */
    @ResponseBody
    @PostMapping("/member")
    public Map<String, Object> saveMember(@RequestBody @Valid NewMemberDto newMemberDto) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            memberService.saveMember(newMemberDto);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }


    /**
     * 관리자 정보 수정(관리자명, 권한)
     */
    @ResponseBody
    @PostMapping("/member/{memberIdx}")
    public Map<String, Object> updateMember(@PathVariable Long memberIdx, @RequestBody @Valid MemberDto memberDto, BindingResult bindingResult) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            if (bindingResult.hasErrors()) {
                FieldError error = bindingResult.getFieldErrors().get(0);
                throw new MessageException(error.getDefaultMessage());
            }
            memberDto.setMemberIdx(memberIdx);
            memberService.updateMember(memberDto);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }

    /**
     * 관리자 삭제
     */
    @ResponseBody
    @DeleteMapping("/member/{memberIdx}")
    public Map<String, Object> deleteMember(@PathVariable Long memberIdx) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            memberService.deleteMember(memberIdx);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        rtnMap.put("message", "삭제되었습니다.");
        return rtnMap;
    }

    /**
     * 관리자 신규등록 - 아이디 중복체크
     */
    @ResponseBody
    @PostMapping("/member/checkId")
    public Map<String, Object> checkId(@RequestBody CustomMap param) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            MemberDto member = memberService.checkIdDup(param.getStr("userId"));
            rtnMap.put("checkUser", member);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    /**
     * 메뉴 목록 (메뉴권한 일괄 관리)
     */
    @GetMapping("/menu")
    public String getMenuAll(Model model) throws Exception {
        try {
            model.addAttribute("menuList", menuService.getMenuList());
            model.addAttribute("authorities", authorityService.getAuthorities());
        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e);
        }
        return "auth/menu_auth_mng";
    }

    /**
     * 메뉴 권한 일괄 저장
     */
    @ResponseBody
    @PostMapping("/menu/all")
    public Map<String, Object> updateMenuAll(@RequestBody List<CustomMap> param) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            log.info("paramList : {}", param);

            menuService.updateMenuAll(param);

        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        rtnMap.put("message", "저장되었습니다.");
        return rtnMap;
    }
}
