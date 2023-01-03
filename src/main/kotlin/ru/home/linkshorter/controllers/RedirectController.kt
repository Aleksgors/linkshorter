package ru.home.linkshorter.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import ru.home.linkshorter.service.KeyMapperServiceInterface
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/{key}")
class RedirectController {

    @Autowired
    lateinit var service: KeyMapperServiceInterface

    @RequestMapping
    fun redirect(@PathVariable("key") key: String, response: HttpServletResponse) {

        val result = service.getLink(key)

        when(result) {
            is KeyMapperServiceInterface.Get.Link -> {
                response.setHeader(HEADER_NAME, result.link)
                response.status = 302
            }
            is KeyMapperServiceInterface.Get.NotFound -> {
                response.status = 404
            }
        }

    }

    companion object {
        private const val  HEADER_NAME = "Location"
    }
}