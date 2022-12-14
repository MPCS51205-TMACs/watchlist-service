package com.example.watchlistservice.service

import com.example.watchlistservice.model.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*


@Service
class ItemService {

    @Value("\${item.url}")
    lateinit var url: String


    var restTemplate: RestTemplate = RestTemplate()

    fun getCategories(ids: Collection<UUID>): Map<UUID, String> {
        // attempt to get categories from item-service
        try{
            val response: ResponseEntity<Array<Category>> = restTemplate.getForEntity(
                "${url}/category?id=${ids.joinToString(",")}",
                Array<Category>::class.java
            )
            if (response.statusCode.is2xxSuccessful){
                return response.body!!.map { it.id to it.categoryDescription }.toMap()
            }

        } catch (e: Exception){

        }
        // if failed, return an empty map
        return mapOf()

    }


}