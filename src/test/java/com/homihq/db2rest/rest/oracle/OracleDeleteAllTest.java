package com.homihq.db2rest.rest.oracle;


import com.homihq.db2rest.OracleBaseIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(291)
@TestPropertySource(properties = {"db2rest.allowSafeDelete=false"})
class OracleDeleteAllTest extends OracleBaseIntegrationTest {

    @Test
    @DisplayName("Delete all records while allowSafeDelete=false")
    void deleteAllWithAllowSafeDeleteFalse() throws Exception {
        mockMvc.perform(delete("/COUNTRY")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rows", Matchers.equalTo(6)))
                .andDo(document("oracle-delete-all"));
    }
}
