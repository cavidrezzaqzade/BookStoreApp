package az.uni.bookappauth.controller;

import az.uni.bookappauth.domain.RoleDto;
import az.uni.bookappauth.domain.UserDto;
import az.uni.bookappauth.filter.JwtFilter;
import az.uni.bookappauth.response.MessageResponse;
import az.uni.bookappauth.response.Reason;
import az.uni.bookappauth.response.ResponseModel;
import az.uni.bookappauth.response.ResponseModelDTO;
import az.uni.bookappauth.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unchecked")
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

    private static RoleDto roleDto;
    private static RoleDto roleDto2;
    private static List<RoleDto> roles;

    private enum TestRole {
        ADMIN("ADMIN"), USER("USER");

        private final String roleName;

        TestRole(String role){
            this.roleName = role;
        }

        @Override
        public String toString() {
            return roleName;
        }
    }

    @BeforeAll
    static void setUpAll(){
        roleDto = RoleDto.builder().id(1L).roleName("ADMIN").build();
        roleDto2 = RoleDto.builder().id(2L).roleName("USER").build();

        roles = List.of(roleDto, roleDto2);
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(jwtFilter)
                .apply(springSecurity())
                .build();
    }

    ////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("check get roles ok")
    @WithMockUser(authorities = {"ADMIN"})
    void givenNone_WhenGetRoles_ThenOk() throws Exception {
        //given
        String message = Reason.SUCCESS_GET.getValue();
        int rolesSize = roles.size();

        ResponseEntity response = MessageResponse.response(Reason.SUCCESS_GET.getValue(), roles, null, HttpStatus.OK);
        given(roleService.getRoles()).willReturn(response);

        //when
        ResultActions result = mockMvc.perform(get("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.size()") //message, data, errors
                        .value(3)))
                .andExpect((jsonPath("$.message")
                        .value(message)))
                .andExpect(jsonPath("$.errors", nullValue()))
                .andExpect(jsonPath("$.data.size()").value(rolesSize));
    }

    @Test
    @DisplayName("check get roles 403")
    @WithMockUser(authorities = {"USER"})
    void givenTestRoles_WhenGetRoles_ThenUnAuth() throws Exception {
        //given
        String message = "access denied for token";

        //when
        ResultActions result = mockMvc.perform(get("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect((jsonPath("$.size()") //message, data, errors
                        .value(3)))
                .andExpect((jsonPath("$.message")
                        .value(message)))
                .andExpect(jsonPath("$.errors", notNullValue()))
                .andExpect(jsonPath("$.data",nullValue()));

    }

    ////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("check add role ok")
    @WithMockUser(authorities = {"ADMIN"})
    void givenRoleDto_WhenAddRole_ThenOk() throws Exception {
        //given
        String message = Reason.SUCCESS_GET.getValue();

        ResponseEntity response = MessageResponse.response(Reason.SUCCESS_GET.getValue(), roleDto, null, HttpStatus.OK);
        given(roleService.addNewRole(any())).willReturn(response);

        //when
        ResultActions result = mockMvc.perform(post("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleDto)));

        //then
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.size()") //message, data, errors
                        .value(3)))
                .andExpect((jsonPath("$.message")
                        .value(message)))
                .andExpect(jsonPath("$.errors", nullValue()));
    }

    ////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("check get tutorials ok")
    @WithMockUser
    void givenNone_WhenGetTutorials_ThenOk() throws Exception {
        //given
        String message = Reason.SUCCESS_GET.getValue();

        // when
        ResultActions result = mockMvc.perform(get("/api/tutorials")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect((jsonPath("$.size()") //message, data, errors
                        .value(3)))
                .andExpect((jsonPath("$.message")
                        .value(message)))
                .andExpect(jsonPath("$.errors", nullValue()))
                .andExpect(jsonPath("$.data.size()").isNotEmpty())
        ;
    }

    @Test
    @DisplayName("check get tutorials unauthenticated(403)")
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
}