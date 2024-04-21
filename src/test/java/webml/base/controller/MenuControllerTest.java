package webml.base.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import webml.base.core.constants.AuthorityConstants;
import webml.base.dto.MenuDto;
import webml.base.service.AuthorityService;
import webml.base.service.MenuService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MenuControllerTest {

    @InjectMocks
    private MenuController menuController;

    @Mock
    private MenuService menuService;

    @Mock
    private AuthorityService authorityService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
    }

    @Test
    @DisplayName("메뉴 등록 테스트")
    void reg_menu_test() throws Exception {
        //given
        MenuDto menu = MenuDto.builder()
                .menuNm("menu")
                .menuLink("/menu")
                .visibleLinkYn("Y")
                .viewAuthority(AuthorityConstants.AUTH_001)
                .saveAuthority(AuthorityConstants.AUTH_003)
                .parentMenuIdx(null)
                .build();

        Mockito.lenient().doNothing().when(menuService).saveMenu(Mockito.any(MenuDto.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(menu))
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("message", "").exists());
    }

}