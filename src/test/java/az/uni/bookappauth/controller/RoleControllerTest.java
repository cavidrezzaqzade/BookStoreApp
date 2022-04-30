//package az.uni.bookappauth.controller;
//
//import az.uni.bookappauth.domain.RoleDto;
//import az.uni.bookappauth.response.MessageResponse;
//import az.uni.bookappauth.response.Reason;
//import az.uni.bookappauth.service.RoleService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import java.util.ArrayList;
//import java.util.List;
//
//@WebMvcTest(RoleControllerTest.class)
//class RoleControllerTest {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private RoleService roleService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private static final String MAIN_URL= "/api/roles";
//    private static final String MAIN_ID= "1";
//
//    private static RoleDto roleDto;
//    private static List<RoleDto> roleDtos = new ArrayList<>();
//
//    private static ResponseEntity<?> responseGetRoles;
//
//    @BeforeAll
//    static void setUpAll(){
//        roleDto = RoleDto.builder()
//                .roleName("Admin")
//                .build();
//
//        roleDtos.add(roleDto);
//
//        responseGetRoles = MessageResponse.response(Reason.SUCCESS_GET.getValue(), roleDtos, null, HttpStatus.OK);
//
//    }
//
//    ////////////////////////////////////////////////////////////////////////
//
//    @WithMockUser(username = "cavid", password = "12345", roles = {"ADMIN"})
//    @Test
//    void addNewRole() {
//    }
//
//    ////////////////////////////////////////////////////////////////////////
//
//    @Test
//    void updateRole() {
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
//        doReturn(responseGetRoles).when(roleService).getRoles();
//
//        //when
//        mockMvc.perform(get(MAIN_URL)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(responseGetRoles)));
//
//        //then
//        verify(roleService, times(1)).getRoles();
//
//    }
//}