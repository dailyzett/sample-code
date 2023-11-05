package com.jun.controller

import com.jun.model.Contact
import com.jun.repository.ContactRepository
import org.springframework.security.access.prepost.PostFilter
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ContactController(
    private val contactRepository: ContactRepository,
) {

    @PostMapping("/contact")
//    @PreFilter("filterObject.contactName != 'Test'")
    @PostFilter("filterObject.contactName != 'Test'")
    fun saveContactInquiryDetails(@RequestBody contacts: List<Contact>): List<Contact> {
        var contact = contacts[0]
        contact.contactId = getServiceReqNumber()
        contact.createDt = Date(System.currentTimeMillis())
        contact = contactRepository.save(contact)

        val returnContacts: MutableList<Contact> = mutableListOf()
        returnContacts.add(contact)
        return returnContacts
    }

    private fun getServiceReqNumber(): String {
        val random = Random()
        val randNum = random.nextInt(999999999 - 9999) + 9999
        return "SRN$randNum"
    }
}