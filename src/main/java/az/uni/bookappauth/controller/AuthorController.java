//package az.uni.bookappauth.controller;
//
//import az.uni.bookappauth.domain.RoleDto;
//import az.uni.bookappauth.domain.app.AuthorDto;
//import az.uni.bookappauth.service.RoleService;
//import az.uni.bookappauth.service.app.AuthorService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("app")
//@RequiredArgsConstructor
//@Tag(name = "Author", description = "the author API")
//public class AuthorController {
//
//    private final AuthorService authorService;
//
//    @Operation(summary = "add author", description = "add new author", tags = {"Author"}, security = @SecurityRequirement(name = "bearerAuth"))
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping("authors")
//    public ResponseEntity<?> addAuthor(@Valid @RequestBody AuthorDto author) {
//        return authorService.addAuthor(author);
//    }
//
//    @Operation(summary = "get authors", description = "get all rows", tags = {"Author"}, security = @SecurityRequirement(name = "bearerAuth"))
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("authors")
//    public ResponseEntity<?> getauthors() {
//        return authorService.getAuthors();
//    }
//
////    @Operation(summary = "update role", description = "update the existing role", tags = {"Author"}, security = @SecurityRequirement(name = "bearerAuth"))
////    @PreAuthorize("hasAuthority('ADMIN')")
////    @PutMapping("roles/{id}")
////    public ResponseEntity<?> updateRole(@Valid @RequestBody RoleDto role, @NotNull @PathVariable(value = "id") Long id) {
////        return authorService.updateRole(role, id);
////    }
////
////    @Operation(summary = "delete role", description = "delete row by id", tags = {"Author"}, security = @SecurityRequirement(name = "bearerAuth"))
////    @PreAuthorize("hasAuthority('ADMIN')")
////    @DeleteMapping("roles/{id}")
////    public ResponseEntity<?> deleteRole(@NotNull @PathVariable(value = "id") Long id) {
////        return authorService.deleteRole(id);
////    }
//}
