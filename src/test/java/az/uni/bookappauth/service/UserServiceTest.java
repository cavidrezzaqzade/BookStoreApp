package az.uni.bookappauth.service;

import az.uni.bookappauth.domain.RoleDto;
import az.uni.bookappauth.domain.UserDto;
import az.uni.bookappauth.entity.RoleEntity;
import az.uni.bookappauth.entity.UserEntity;
import az.uni.bookappauth.mapper.RoleMapper;
import az.uni.bookappauth.mapper.UserMapper;
import az.uni.bookappauth.repository.UserRepository;
import az.uni.bookappauth.response.MessageResponse;
import az.uni.bookappauth.response.Reason;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Spy
    private UserMapper userMapper;

    private static UserEntity userEntity;
    private static UserEntity userEntity2;
    private static UserDto userDto;
    private static UserDto userDto2;

    private static ResponseEntity<?> responseModelDTO;
    private static ResponseEntity<?> responseModelDTO2;
    private static ResponseEntity<?> responseModelDTOUnprocessableAddRole;
    private static ResponseEntity<?> responseModelDTOUnprocessableFk;
    private static ResponseEntity<?> responseModelDTOUnprocessableDoesNotExist;
    private static ResponseEntity<?> responseModelDTOUnprocessableAlreadyExist;
    private static ResponseEntity<?> responseModelDTOList;
    private static ResponseEntity<?> responseModelDTOEmptyList;

    @BeforeAll
    static void setUpAll(){
        userEntity = UserEntity.builder()
                .id(1L)
                .username("cavid")
                .password("12345")
                .build();

        userEntity2 = UserEntity.builder()
                .id(2L)
                .username("azer")
                .password("12345")
                .build();


        userDto = UserDto.builder()
                .username("cavid")
                .password("12345")
                .build();

        userDto2 = UserDto.builder()
                .username("azer")
                .password("12345")
                .build();




//        responseModelDTOList = MessageResponse.response(Reason.SUCCESS_ADD.getValue(), List.of(userDto, userDto2), null, HttpStatus.OK);
//        responseModelDTO = MessageResponse.response(Reason.SUCCESS_ADD.getValue(), roleDto, null, HttpStatus.OK);
//        responseModelDTO2 = MessageResponse.response(Reason.SUCCESS_ADD.getValue(), roleDto2, null, HttpStatus.OK);
//        responseModelDTOEmptyList = MessageResponse.response(Reason.SUCCESS_GET.getValue(), List.of(), null, HttpStatus.OK);
//
//        Map<String, String> map1 = new HashMap<>();
//        map1.put("roleName", "already exists");
//        responseModelDTOUnprocessableAddRole = MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map1, HttpStatus.UNPROCESSABLE_ENTITY);
//
//        Map<String, String> map2 = new HashMap<>();
//        map2.put("roleId", "foreign key constraint violation");
//        responseModelDTOUnprocessableFk = MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map2, HttpStatus.UNPROCESSABLE_ENTITY);
//
//        Map<String, String> map3 = new HashMap<>();
//        map3.put("roleId", "role does not exist");
//        responseModelDTOUnprocessableDoesNotExist = MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map3, HttpStatus.UNPROCESSABLE_ENTITY);
//
//        Map<String, String> map4 = new HashMap<>();
//        map4.put("roleName", "roleName already exists");
//        responseModelDTOUnprocessableAlreadyExist = MessageResponse.response(Reason.VALIDATION_ERRORS.getValue(), null, map4, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    void getByLogin() {

    }

    //////////////////////////////////////////////////////////////////////////////

    @DisplayName("check get all users ok")
    @Test
    void givenNone_WhenGetUsers_ThenOk() {
        //given
        List<UserEntity> users = List.of(userEntity, userEntity2);
        List<UserDto> usersDto = List.of(userDto, userDto2);
        given(userRepository.findAll()).willReturn(users);
        given(userMapper.usersToUsersDto(users)).willReturn(usersDto);

        //when
        ResponseEntity<?> res = userService.getUsers();

        //then
        assertEquals(res, MessageResponse.response(Reason.SUCCESS_ADD.getValue(), List.of(userDto, userDto2), null, HttpStatus.OK));
        verify(userRepository).findAll();
    }

    @DisplayName("check get all users empty list ok")
    @Test
    void givenNone_WhenGetUsers_ThenEmptyListOk() {
        //given
        given(userRepository.findAll()).willReturn(List.of());
        given(userMapper.usersToUsersDto(List.of())).willReturn(List.of());

        //when
        ResponseEntity<?> res = userService.getUsers();

        //then
        assertEquals(res, MessageResponse.response(Reason.SUCCESS_ADD.getValue(), List.of(), null, HttpStatus.OK));
        verify(userRepository).findAll();
    }

    //////////////////////////////////////////////////////////////////////////////

    @DisplayName("check add new user ok")
    @Test
    void givenUserDto_WhenAddNewUser_ThenOk() {
        //given
        given(userRepository.existsByUsernameIgnoreCase("cavid")).willReturn(false);
        given(userRepository.save(any(UserEntity.class))).willReturn(userEntity);
        given(userMapper.userToUserDto(any(UserEntity.class))).willReturn(userDto);

        //when
        ResponseEntity<?> res = userService.addNewUser(userDto);

        //then
        assertThat(res).isNotNull();
        assertThat(res).isEqualTo(MessageResponse.response(Reason.SUCCESS_ADD.getValue(), userDto, null, HttpStatus.OK));
        verify(userRepository, times(1)).existsByUsernameIgnoreCase("cavid");
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userMapper, times(1)).userToUserDto(any(UserEntity.class));
    }

    @DisplayName("check add new user 422")
    @Test
    void givenUserDto_WhenAddNewUser_Thenunprocessable() {
        //given
        Map<String, String> map = new HashMap<>();
        map.put("username", "data already exists");
        given(userRepository.existsByUsernameIgnoreCase("cavid")).willReturn(true);

        //when
        ResponseEntity<?> res = userService.addNewUser(userDto);

        //then
        assertThat(res).isNotNull();
        assertThat(res).isEqualTo(MessageResponse.response(Reason.SUCCESS_ADD.getValue(), null, map, HttpStatus.UNPROCESSABLE_ENTITY));
        verify(userRepository, times(1)).existsByUsernameIgnoreCase("cavid");
    }

    //////////////////////////////////////////////////////////////////////////////

    @Test
    void updateUser() {

    }
    //////////////////////////////////////////////////////////////////////////////
    @Test
    void deleteUser() {

    }
}