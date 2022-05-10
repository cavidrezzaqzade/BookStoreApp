package az.uni.bookappauth.service;

import az.uni.bookappauth.domain.Role;
import az.uni.bookappauth.domain.User;
import az.uni.bookappauth.entity.RoleEntity;
import az.uni.bookappauth.entity.UserEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Converter {
    public Optional<User> entityToDtoLogin(UserEntity entity) {
        Optional<User> user = Optional.of(new User());

        user.get().setLogin(entity.getUsername());
        user.get().setPassword(entity.getPassword());
        user.get().setFirstName(entity.getName());
        user.get().setLastName(entity.getSurname());

        Set<Role> roles = new HashSet<>();
        for (RoleEntity roleEntity : entity.getRoles()) {
            Role r = new Role();
            r.setRoleName(roleEntity.getRoleName());
            roles.add(r);
        }
        user.get().setRoles(roles);

        return user;
    }
}
