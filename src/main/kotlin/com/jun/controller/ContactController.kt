package com.jun.controller

import com.jun.model.Contact
import com.jun.repository.ContactRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ContactController(
    private val contactRepository: ContactRepository,
) {

    @PostMapping("/contact")
    fun saveContactInquiryDetails(@RequestBody contact: Contact): Contact {
        contact.contactId = getServiceReqNumber()
        contact.createDt = Date(System.currentTimeMillis())
        return contactRepository.save(contact)
    }

    private fun getServiceReqNumber(): String {
        val random = Random()
        val randNum = random.nextInt(999999999 - 9999) + 9999
        return "SRN$randNum"
    }
}