package net.thumbtack.testdevices.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.AuthorityType;
import net.thumbtack.testdevices.dto.request.UserRequest;
import net.thumbtack.testdevices.dto.response.UserResponse;
import net.thumbtack.testdevices.exceptions.ErrorCode;
import net.thumbtack.testdevices.web.services.UsersService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerMvcTest {
    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;
    private Set<Authority> authoritySet;
    private UserRequest userRequest;
    private String exceptionMessage;
    private UserResponse userResponse;

    @MockBean
    private UsersService usersService;

    @Before
    public void setup() {
        exceptionMessage = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 (email)";
        objectMapper = new ObjectMapper();
        authoritySet = new HashSet<>();
        authoritySet.add(new Authority(AuthorityType.USER));
        userRequest = new UserRequest(
                "Vasiliy",
                "Pupkin",
                "+79923456789",
                "vasiliy.pupkin@mail.com",
                "123456"
        );
        userResponse = new UserResponse(
                1L,
                "Vasiliy",
                "Pupkin",
                "+79923456789",
                "vasiliy.pupkin@mail.com",
                authoritySet
        );
    }

    @Test
    public void registrationUser_thenReturnJsonArray() throws Exception {
        given(usersService.userRegistration(any(UserRequest.class))).willReturn(userResponse);

        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(userResponse.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(userResponse.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(userResponse.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userResponse.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0].authorityType").value(AuthorityType.USER.getAuthorityType()))
        ;
    }

    @Test
    public void registrationUser_thenThrowDuplicateException() throws Exception {
        given(usersService.userRegistration(any(UserRequest.class))).willThrow(new DataIntegrityViolationException(exceptionMessage));

        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value(ErrorCode.DUPLICATE_KEY_EXCEPTION.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").isString())
        ;
    }

    @Test
    public void registrationUser_withNullFirstName() throws Exception {
        userRequest.setFirstName(null);
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("FIRSTNAME_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("firstName can not be blank or null"))
        ;
    }

    @Test
    public void registrationUser_withEmptyFirstName() throws Exception {
        userRequest.setFirstName("");
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("FIRSTNAME_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].errorCode").value("FIRSTNAME_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].field").value("firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].message").isString())
        ;
    }

    @Test
    public void registrationUser_withMinusSighInFirstName() throws Exception {
        userRequest.setFirstName("Asd-qwe");
        userResponse.setFirstName("Asd-qwe");
        given(usersService.userRegistration(any(UserRequest.class))).willReturn(userResponse);
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(userResponse.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(userResponse.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(userResponse.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userResponse.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0].authorityType").value(AuthorityType.USER.getAuthorityType()))
        ;
    }

    @Test
    public void registrationUser_withSymbolsInFirstName() throws Exception {
        userRequest.setFirstName("Name!@#$%^&*()+=-_;:\"',.?/");
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("FIRSTNAME_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("firstName may contain Russian and English letters, whitespaces and minus sign"))
        ;
    }

    @Test
    public void registrationUser_withNullLastName() throws Exception {
        userRequest.setLastName(null);
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("LASTNAME_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("lastName can not be blank or null"))
        ;
    }

    @Test
    public void registrationUser_withEmptyLastName() throws Exception {
        userRequest.setLastName("");
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("LASTNAME_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].errorCode").value("LASTNAME_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].field").value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].message").isString())
        ;
    }

    @Test
    public void registrationUser_withMinusSighInLastName() throws Exception {
        userRequest.setLastName("Asd-qwe");
        userResponse.setLastName("Asd-qwe");
        given(usersService.userRegistration(any(UserRequest.class))).willReturn(userResponse);
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(userResponse.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(userResponse.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(userResponse.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userResponse.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0].authorityType").value(AuthorityType.USER.getAuthorityType()))
        ;
    }

    @Test
    public void registrationUser_withSymbolsInLastName() throws Exception {
        userRequest.setLastName("Name!@#$%^&*()+=-_;:\"',.?/");
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("LASTNAME_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("lastName may contain Russian and English letters, whitespaces and minus sign"))
        ;
    }

    @Test
    public void registrationUser_withNullPhone() throws Exception {
        userRequest.setPhone(null);
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("PHONE_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("phone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("phone can not be blank or null"))
        ;
    }

    @Test
    public void registrationUser_withEmptyPhone() throws Exception {
        userRequest.setPhone("");
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("PHONE_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("phone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].errorCode").value("PHONE_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].field").value("phone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].message").isString())
        ;
    }

    @Test
    public void registrationUser_withValidPhoneExample1() throws Exception {
        userRequest.setPhone("89131234567");
        userResponse.setPhone("89131234567");
        given(usersService.userRegistration(any(UserRequest.class))).willReturn(userResponse);
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(userResponse.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(userResponse.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(userResponse.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userResponse.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0].authorityType").value(AuthorityType.USER.getAuthorityType()))
        ;
    }

    @Test
    public void registrationUser_withValidPhoneExample2() throws Exception {
        userRequest.setPhone("+7-913-123-45-67");
        userResponse.setPhone("+7-913-123-45-67");
        given(usersService.userRegistration(any(UserRequest.class))).willReturn(userResponse);
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(userResponse.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(userResponse.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(userResponse.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userResponse.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0].authorityType").value(AuthorityType.USER.getAuthorityType()))
        ;
    }

    @Test
    public void registrationUser_withBadPhone() throws Exception {
        userRequest.setPhone("7-913-123-4567");
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("PHONE_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("phone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("phone is incorrect. Example: +7-913-123-45-67 or 89131234567"))
        ;
    }

    @Test
    public void registrationUser_withBadEmail() throws Exception {
        userRequest.setEmail("asdasdmail.ru");
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("EMAIL_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("must be a well-formed email address"))
        ;
    }

    @Test
    public void registrationUser_withNullPassword() throws Exception {
        userRequest.setPassword(null);
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("PASSWORD_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("password"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("password can not be blank or null"))
        ;
    }

    @Test
    public void registrationUser_withEmptyPassword() throws Exception {
        userRequest.setPassword("");
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("PASSWORD_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("password"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].errorCode").value("PASSWORD_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].field").value("password"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].message").isString())
        ;
    }

    @Test
    public void registrationUser_withSymbolsInPassword() throws Exception {
        userRequest.setPassword("Name!@#$%^&*()+=-_;:\"',.?/");
        given(usersService.userRegistration(any(UserRequest.class))).willReturn(userResponse);
        mvc.perform(post("/api/registration/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(userResponse.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(userResponse.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(userResponse.getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userResponse.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0].authorityType").value(AuthorityType.USER.getAuthorityType()))
        ;
    }
}
