package az.uni.bookappauth.controller;

import az.uni.bookappauth.domain.RoleDto;
import az.uni.bookappauth.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Role", description = "the role API")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "add role", description = "add new role", tags = {"Role"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/roles")
    public ResponseEntity<?> addNewRole(@Valid @RequestBody RoleDto role) {
        return roleService.addNewRole(role);
    }

    @Operation(summary = "update role", description = "update the existing role", tags = {"Role"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/roles/{id}")
    public ResponseEntity<?> updateRole(@Valid @RequestBody RoleDto role, @NotNull @PathVariable(value = "id") Long id) {
        return roleService.updateRole(role, id);
    }

    @Operation(summary = "delete role", description = "delete row by id", tags = {"Role"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> deleteRole(@NotNull @PathVariable(value = "id") Long id) {
        return roleService.deleteRole(id);
    }

    @Operation(summary = "get role", description = "get all rows", tags = {"Role"}, security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return roleService.getRoles();
    }


    @Operation(summary = "test endpoint", description = "get all rows", tags = {"Role"}, security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/tutorials")
    public List<Tutorial> getAllTutorials() {
            List<Tutorial> tutorials = new ArrayList<>();
            tutorials.add(new Tutorial("a"));
            tutorials.add(new Tutorial("b"));

            return tutorials;
    }

}
