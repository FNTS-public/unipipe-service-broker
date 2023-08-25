package io.meshcloud.dockerosb.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityConfigTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Test
    fun testAllowEncodedSlashes() {
        val urlWithEncodedSlash = "/test%2Fpath"

        mockMvc.perform(MockMvcRequestBuilders.get(urlWithEncodedSlash))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}