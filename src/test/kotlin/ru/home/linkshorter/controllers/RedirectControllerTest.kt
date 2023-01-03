package ru.home.linkshorter.controllers

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import ru.home.linkshorter.LinkshorterApplication
import ru.home.linkshorter.service.KeyMapperServiceInterface

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootApplication(scanBasePackageClasses = [LinkshorterApplication::class])
@WebAppConfiguration
class RedirectControllerTest {

    @Autowired lateinit var webApplicationContext: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @Mock
    lateinit var service: KeyMapperServiceInterface

    @Autowired
    @InjectMocks
    lateinit var controller: RedirectController

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build()

        Mockito.`when`(service.getLink(PATH)).thenReturn(KeyMapperServiceInterface.Get.Link(HEADER_VALUE))
        Mockito.`when`(service.getLink(BAD_PATH)).thenReturn(KeyMapperServiceInterface.Get.NotFound(BAD_PATH))
    }

    private val PATH = "aAbBcC"
    private val REDIRECT_STATUS: Int = 302
    private val HEADER_NAME = "Location"
    private val HEADER_VALUE = "http://www.test.org"

    @Test fun controllerMustRedirectUsWhenRequestIsSuccess() {
        mockMvc.perform(get("/$PATH"))
            .andExpect(status().`is`(REDIRECT_STATUS))
            .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
    }

    private val BAD_PATH = "okoko"
    private val NOT_FOUND: Int = 404
    @Test fun controllerMustReturn404IfBadKey() {
        mockMvc.perform(get("/$BAD_PATH"))
            .andExpect(status().`is`(NOT_FOUND))
    }
}