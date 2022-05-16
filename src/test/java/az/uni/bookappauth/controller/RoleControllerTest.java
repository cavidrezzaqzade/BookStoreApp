package az.uni.bookappauth.controller;

import az.uni.bookappauth.domain.RoleDto;
import az.uni.bookappauth.filter.JwtFilter;
import az.uni.bookappauth.response.MessageResponse;
import az.uni.bookappauth.response.Reason;
import az.uni.bookappauth.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(/*addFilters = false*/)
//@WebMvcTest(RoleController.class)
@SpringBootTest
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(jwtFilter)
                .apply(springSecurity())
                .build();
    }

    @BeforeAll
    static void setUpAll(){}

    @DisplayName("check get tutorials ok")
    @Test
    @WithMockUser
    void givenNone_WhenGetTutorials_ThenOk() throws Exception {
        //given
        ResponseEntity<?> response = MessageResponse.response(Reason.SUCCESS_GET.getValue(), null, null, HttpStatus.OK);
        given(roleService.getRoles()).willReturn(null);

        // when
        ResultActions result = mockMvc.perform(get("/api/tutorials")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect((jsonPath("$.size()").
                        value(2)));
    }

    @DisplayName("check get tutorials unauthenticated(403)")
    @Test
    @WithAnonymousUser
    void givenNone_WhenGetTutorials_ThenForbidden() throws Exception {
        //given

        // when
        ResultActions result = mockMvc.perform(get("/api/tutorials")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    ////////////////////////////////////////////////////////////////////////////////////

    @DisplayName("check get roles ok")
    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void givenNone_WhenGetRoles_ThenOk() throws Exception {
        //given

        // when
        ResultActions result = mockMvc.perform(get("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk());
    }

    ////////////////////////////////////////////////////////////////////////////////////

}