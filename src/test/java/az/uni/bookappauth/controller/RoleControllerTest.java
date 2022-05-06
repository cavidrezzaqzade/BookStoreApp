//package az.uni.bookappauth.controller;
//
//import az.uni.bookappauth.domain.RoleDto;
//import az.uni.bookappauth.filter.JwtFilter;
//import az.uni.bookappauth.response.MessageResponse;
//import az.uni.bookappauth.response.Reason;
//import az.uni.bookappauth.response.ResponseModelDTO;
//import az.uni.bookappauth.service.JwtProvider;
//import az.uni.bookappauth.service.RoleService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.stubbing.Answer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.hamcrest.Matchers.is;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@WebMvcTest(RoleController.class)
//class RoleControllerTest {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private RoleService roleService;
//
//    @MockBean
//    private JwtFilter jwtFilter;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private static final String MAIN_URL= "/caci/fifi/";
//    private static final String MAIN_ID= "1";
//
//    private static RoleDto roleDto;
//    private static List<RoleDto> roleDtos = new ArrayList<>();
//
//    private static ResponseEntity responseGetRoles;
//
///*    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .addFilter(jwtFilter)
//                .apply(springSecurity())
//                .build();
//    }*/
//
////    @BeforeAll
////    static void setUpAll(){
////
////        roleDto = RoleDto.builder()
////                .roleName("Admin")
////                .build();
////
////        roleDtos.add(roleDto);
////
////        responseGetRoles = MessageResponse.response(Reason.SUCCESS_GET.getValue(), roleDtos, null, HttpStatus.OK);
////    }
//
//    ////////////////////////////////////////////////////////////////////////
//
//    @WithMockUser/*(username = "cavid", password = "$2a$10$lX6kBplFbkNe7OAjZHGgYOA6JICljqfpWnSU8cHQepXvt7vfnkITW", roles = {"ADMIN"})*/
//    @Test
//    void addNewRole() throws Exception {
//        //given
////        doReturn(responseGetRoles).when(roleService).getRoles();
//        given(roleService.addNewRole(roleDto)).willReturn((null));
//
//        //when
//        ResultActions response = mockMvc.perform(post("/api/roles")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));
//
//        //then
//        response
//                .andExpect(status().isOk())
//                .andDo(print());
////                .andExpect(jsonPath("$.getStatus()",
////                                is(null)));
////                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(responseGetRoles.toString())));
//
//
//    }
//
//    ////////////////////////////////////////////////////////////////////////
//
//    @Test
//    void updateRole() {
//
//    }
//
//    ////////////////////////////////////////////////////////////////////////
//
//    @Test
//    void deleteRole() {
//    }
//
//    ////////////////////////////////////////////////////////////////////////
//
//    @DisplayName("get all roles ok")
//    @Test
//    void givenNone_WhenGetRoles_ThenOK() throws Exception {
//        //given
////        doReturn(responseGetRoles).when(roleService).getRoles();
//        given(roleService.getRoles()).willReturn((null));
//
//        //when
//        ResultActions response = mockMvc.perform(get("/api/oles")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));
//
//        //then
//        response
//                .andExpect(status().isOk())
//                .andDo(print());
////                .andExpect(jsonPath("$.getStatus()",
////                                is(null)));
////                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(responseGetRoles.toString())));
//
//    }
//}