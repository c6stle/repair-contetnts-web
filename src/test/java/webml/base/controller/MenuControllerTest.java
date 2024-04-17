package webml.base.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import webml.base.service.AuthorityService;
import webml.base.service.MenuService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MenuControllerTest {

    @InjectMocks
    MenuController menuController;

    @Mock
    MenuService menuService;

    @Mock
    AuthorityService authorityService;

    MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
    }
}