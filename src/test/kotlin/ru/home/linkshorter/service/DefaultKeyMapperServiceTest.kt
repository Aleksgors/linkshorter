package ru.home.linkshorter.service

import org.junit.Test
import org.junit.Assert.*

class DefaultKeyMapperServiceTest {

    private val service: KeyMapperServiceInterface = DefaultKeyMapperService()

    private val KEY: String = "aAbBcCdD"
    private val LINK: String = "http://www.test.org"
    private val LINK_NEW: String = "http://www.vk.ru"

    @Test
    fun `Client can add new key with link` () {
        assertEquals(KeyMapperServiceInterface.Add.Success(KEY, LINK), service.add(KEY, LINK))
        assertEquals(KeyMapperServiceInterface.Get.Link(LINK), service.getLink(KEY))
    }

    @Test
    fun `Client cannot add key if the key exist` () {
        service.add(KEY, LINK)
        assertEquals(KeyMapperServiceInterface.Add.AlreadyExist(KEY), service.add(KEY, LINK_NEW))
        assertEquals(KeyMapperServiceInterface.Get.Link(LINK    ), service.getLink(KEY))
    }

    @Test
    fun `Client cannot take link on not existed key` () {
        assertEquals(KeyMapperServiceInterface.Get.NotFound(KEY), service.getLink(KEY))
    }
}