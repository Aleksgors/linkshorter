package ru.home.linkshorter.service

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class DefaultKeyMapperService : KeyMapperServiceInterface {

    private val map: MutableMap<String, String> = ConcurrentHashMap()

    override fun add(key: String, link: String): KeyMapperServiceInterface.Add {
        return if (this.map.containsKey(key)) {
            KeyMapperServiceInterface.Add.AlreadyExist(key)
        } else {
            this.map[key] = link
            KeyMapperServiceInterface.Add.Success(key, link)
        }
    }

    override fun getLink(key: String): KeyMapperServiceInterface.Get = if (map.containsKey(key)) KeyMapperServiceInterface.Get.Link(map.getValue(key)) else KeyMapperServiceInterface.Get.NotFound(key)
}