package az.uni.bookappauth.service;

import az.uni.bookappauth.domain.Role;
import az.uni.bookappauth.domain.RoleDto;
import az.uni.bookappauth.entity.RoleEntity;
import az.uni.bookappauth.entity.UserEntity;
import az.uni.bookappauth.mapper.RoleMapper;
import az.uni.bookappauth.repository.RoleRepository;
import az.uni.bookappauth.response.MessageResponse;
import az.uni.bookappauth.response.Reason;
import az.uni.bookappauth.response.ResponseModelDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Spy
    private RoleMapper roleMapper;

    private static RoleEntity roleEntity;
    private static RoleEntity roleEntity2;
    private static RoleDto roleDto;
    private static RoleDto roleDto2;
    private static ResponseEntity<?> responseModelDTO;
    private static ResponseEntity<?> responseModelDTOUnprocessable;
    private static ResponseEntity<?> responseModelDTOList;

    @BeforeAll
    static void setUpAll(){
        roleEntity = RoleEntity.builder()
                .id(1L)
                .roleName("Admin")
                .build();
        roleEntity2 = RoleEntity.builder()
                .id(1L)
                .roleName("Admin")
                .build();

        UserEntity user1 = UserEntity.builder()
                .username("cavid")
                .password("12345")
                .name("caci")
                .build();

        roleEntity.setUsers(Set.of(user1));

        roleDto = RoleDto.builder()
                .roleName("Admin")
                .build();
        roleDto2 = RoleDto.builder()
                .roleName("User")
                .build();

        responseModelDTOList = MessageResponse.response(Reason.SUCCESS_ADD.getValue(), List.of(roleDto, roleDto2), null, HttpStatus.OK);
        responseModelDTO = MessageResponse.response(Reason.SUCCESS_ADD.getValue(), roleDto, null, HttpStatus.OK);

        Map<String, String> map = new HashMap<>();
        map.put("roleName", "already exists");
        responseModelDTOUnprocessable = MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @DisplayName("check add new role success")
    @Test
    void givenRoleDto_WhenAddNewRole_ThenOK() {
        //given
        given(roleRepository.existsByRoleNameIgnoreCase("Admin")).willReturn(false);
        given(roleRepository.save(any(RoleEntity.class))).willReturn(roleEntity);
//        given(roleMapper.roleDtoToRole(roleDto)).willReturn(roleEntity); //should we use mapper in the service or ?
        given(roleMapper.roleToRoleDto(any(RoleEntity.class))).willReturn(roleDto);

        //when
        ResponseEntity<?> responseOK = roleService.addNewRole(roleDto);

        //then
        assertThat(responseOK).isNotNull();
        assertThat(responseOK).isEqualTo(responseModelDTO);
        verify(roleRepository, times(1)).existsByRoleNameIgnoreCase("Admin");
        verify(roleRepository, times(1)).save(any(RoleEntity.class));
        verify(roleMapper, times(1)).roleToRoleDto(any(RoleEntity.class));
    }

    @DisplayName("check add new role exception")
    @Test
    void givenRoleDto_WhenAddNewRole_ThenUnprocessable() {
        //given
        given(roleRepository.existsByRoleNameIgnoreCase("Admin")).willReturn(true);

        //when
        ResponseEntity<?> responseUnprocessable = roleService.addNewRole(roleDto);

        //then
        assertThat(responseUnprocessable).isNotNull();
        assertThat(responseUnprocessable).isEqualTo(responseModelDTOUnprocessable);
        verify(roleRepository, times(1)).existsByRoleNameIgnoreCase("Admin");
    }

    @DisplayName("check get all roles")
    @Test
    void givenNone_WhenGetRoles_ThenOK() {
        //given
        List<RoleEntity> roles = List.of(roleEntity, roleEntity2);
        List<RoleDto> roleDtos = List.of(roleDto, roleDto2);

        //when
        given(roleRepository.findAll()).willReturn(roles);
        given(roleMapper.rolesToRoleDtos(roles)).willReturn(roleDtos);

        //then
        ResponseEntity<?> a = roleService.getRoles();

        assertEquals(a, responseModelDTOList);
    }

    @DisplayName("check delete role by id")
    @Test
    void givenRoleId_WhenDeleteRole_ThenOK() {
        //given
        Long roleID = 1L;
        given(roleRepository.findById(roleID)).willReturn(Optional.of(roleEntity));
        given(roleMapper.roleToRoleDto(any(RoleEntity.class))).willReturn(roleDto);

        //when
        ResponseEntity<?> responseOK = roleService.deleteRole(roleID);
        roleRepository.findById(roleID).get().getUsers().forEach(System.out::println);

        //then
        assertThat(responseOK).isNotNull();
        assertThat(responseOK).isEqualTo(responseModelDTO);
        verify(roleRepository, times(2)).findById(roleID);
        verify(roleMapper, times(1)).roleToRoleDto(any(RoleEntity.class));
    }

    @Test
    void updateRole() {
    }
}