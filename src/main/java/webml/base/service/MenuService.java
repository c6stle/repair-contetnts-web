package webml.base.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webml.base.util.CustomMap;
import webml.base.entity.Menu;
import webml.base.repository.MenuRepository;
import webml.base.core.exception.MessageException;
import webml.base.dto.MenuDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuDto> getMenuList() {
        return menuRepository.findAll().stream().map(MenuDto::new).collect(Collectors.toList());
    }

    //사이드메뉴 가져오기
    public List<MenuDto> getSideMenuList(Collection<String> authorityCdList) {
        /* //조회권한 미적용 쿼리
        return menuRepository.findByParentIsNull(Sort.by(Sort.Direction.ASC, "menuOrder")).stream()
                .map(MenuDto::new).collect(Collectors.toList());*/
        return menuRepository.findByVisibleAndViewAuthority(authorityCdList).stream()
                .map(MenuDto::new).collect(Collectors.toList());
    }

    //메뉴목록 가져오기
    public List<MenuDto> getMenuMngList(Collection<String> authorityCdList) {
        /* //조회권한 미적용 쿼리
        return menuRepository.findByParentIsNull(Sort.by(Sort.Direction.ASC, "menuOrder")).stream()
                .map(MenuDto::new).collect(Collectors.toList());*/
        return menuRepository.findByViewAuthority(authorityCdList).stream()
                .map(MenuDto::new).collect(Collectors.toList());
    }


    public MenuDto getMenuInfo(Long menuIdx) {
        return menuRepository.findById(menuIdx).map(menu ->
                MenuDto.builder()
                        .menuIdx(menu.getMenuIdx())
                        .menuOrder(menu.getMenuOrder())
                        .menuNm(menu.getMenuNm())
                        .menuLink(menu.getMenuLink())
                        .visibleLinkYn(menu.getVisibleLinkYn())
                        .viewAuthority(menu.getViewAuthority())
                        .saveAuthority(menu.getSaveAuthority())
                        .parentMenuIdx(menu.getParent() == null ? null : menu.getParent().getMenuIdx())
                        .regDt(menu.getRegDt()).build())
                .orElseThrow(() -> new MessageException("메뉴를 찾을 수 없습니다."));
    }

    @Transactional
    public void saveMenu(MenuDto menuDto) {

        Menu parent;
        int count;
        if (menuDto.getParentMenuIdx() == null) {
            parent = null;
            count = menuRepository.countByParentIsNull();
        } else {
            parent = menuRepository.findById(menuDto.getParentMenuIdx()).orElseThrow(() -> new MessageException("상위메뉴를 찾을 수 없습니다."));
            count = menuRepository.countByParentMenuIdx(parent.getMenuIdx());
        }

        Menu menu = Menu.builder()
                .menuNm(menuDto.getMenuNm())
                .menuLink(menuDto.getMenuLink())
                .menuOrder(count + 1)
                .viewAuthority(menuDto.getViewAuthority())
                .saveAuthority(menuDto.getSaveAuthority())
                .parent(parent)
                .build();

        menuRepository.save(menu);
    }


    @Transactional
    public void updateMenu(MenuDto menuDto) {

        Optional<Menu> findMenu = menuRepository.findById(menuDto.getMenuIdx());

        findMenu.ifPresent(menu -> {
            //상위 메뉴가 이전과 달라졌을 경우(최상위 메뉴는 타 메뉴의 하위로 이동 불가능)
//            if (!menuDto.getBfParentMenuIdx().equals(menuDto.getParentMenuIdx())) {
            if ("Y".equals(menuDto.getParentMenuChgYn())) {
                Menu parent;
                int count;
                if (menuDto.getParentMenuIdx() == null) {
                    parent = null;
                    count = menuRepository.countByParentIsNull();
                } else {
                    parent = menuRepository.findById(menuDto.getParentMenuIdx()).orElseThrow(() -> new MessageException("상위메뉴를 찾을 수 없습니다."));
                    count = menuRepository.countByParentMenuIdx(parent.getMenuIdx());
                }
                menu.update(count + 1,
                        menuDto.getMenuNm(),
                        menuDto.getMenuLink(),
                        menuDto.getViewAuthority(),
                        menuDto.getSaveAuthority(),
                        parent);

                List<Menu> bfMenuList = menuRepository.findByParentMenuIdx(menuDto.getBfParentMenuIdx());
                int orderVal = 1;
                for (Menu m:bfMenuList) {
                    m.updateOrder(orderVal);
                    orderVal++;
                }
            } else {
                menu.update(menuDto.getMenuNm(),
                        menuDto.getMenuLink(),
                        menuDto.getViewAuthority(),
                        menuDto.getSaveAuthority());
            }
        });
    }

    @Transactional
    public void deleteMenu(Long menuIdx) {
        menuRepository.deleteById(menuIdx);
    }

    @Transactional
    public void updateOrder(Long[] menuIdxList) {
        int orderVal = 1;
        for (Long menuIdx : menuIdxList) {
            Menu menu = menuRepository.findById(menuIdx).orElseThrow(() -> new MessageException("순서 변경 중 " + menuIdx + " 번 메뉴를 찾을 수 없습니다."));
            menu.updateOrder(orderVal);
            orderVal++;
        }
    }

    @Transactional
    public void updateMenuAll(List<CustomMap> param) {
        for (CustomMap map: param) {
            Long menuIdx = map.getLong("menuIdx");
            Optional<Menu> findMenu = menuRepository.findById(menuIdx);
            findMenu.ifPresent(menu -> {
                menu.update(map.getStr("viewAuthority"), map.getStr("saveAuthority"));
            });
        }
    }
}
