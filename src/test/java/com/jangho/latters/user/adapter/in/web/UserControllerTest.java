package com.jangho.latters.user.adapter.in.web;

import com.jangho.latters.user.application.port.in.UserUseCase;
import com.jangho.latters.user.domain.Email;
import com.jangho.latters.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserUseCase userUseCase;

    @Test
    public void findById() throws Exception {
        // given
        Long id = 1L;

        when(userUseCase.findById(id)).thenReturn(User.builder()
                .id(id)
                .name("John Doe")
                .email(Email.from("test@example.com"))
                .build());

        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "user-get",
                                pathParameters(parameterWithName("id").description("target user id")),
                                responseFields( // response 필드 정보 입력
                                        fieldWithPath("id").description("user id"),
                                        fieldWithPath("name").description("user name"),
                                        fieldWithPath("email").description("user email")
                                )
                        )
                );
    }

}