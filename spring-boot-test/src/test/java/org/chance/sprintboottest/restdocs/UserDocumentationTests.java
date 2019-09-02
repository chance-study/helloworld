package org.chance.sprintboottest.restdocs;

import org.chance.sprintboottest.service.VehicleDetails;
import org.chance.sprintboottest.web.UserVehicleController;
import org.chance.sprintboottest.web.UserVehicleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.templates.TemplateFormats;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserVehicleController.class)
@AutoConfigureRestDocs("build/generated-snippets")
public class UserDocumentationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserVehicleService userVehicleService;

    @Test
    public void vehicle() throws Exception {

        given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
        this.mvc.perform(get("/sboot/vehicle").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(document("vehicle"));

    }

    /**
     * 如果想自定义主配置类，你可以使用一个内嵌的@TestConfiguration类。
     * 内嵌的@TestConfiguration类是可以跟应用主配置类一块使用的。
     */
    @TestConfiguration
    static class CustomizationConfiguration implements RestDocsMockMvcConfigurationCustomizer {

        @Override
        public void customize(MockMvcRestDocumentationConfigurer configurer) {
            configurer.snippets().withTemplateFormat(TemplateFormats.markdown());
        }

    }

    /**
     * 如果想自定义主配置类，你可以使用一个内嵌的@TestConfiguration类。
     * 内嵌的@TestConfiguration类是可以跟应用主配置类一块使用的。
     */
    @TestConfiguration
    static class ResultHandlerConfiguration {

        @Bean
        public RestDocumentationResultHandler restDocumentation() {
            return MockMvcRestDocumentation.document("{method-name}");
        }

    }

}

