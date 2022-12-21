package com.example.app.integration.repository.service;

import com.example.app.AppApplication;
import com.example.app.model.Person;
import com.example.app.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ContextConfiguration(classes = AppApplication.class)
class PersonRepositoryTest {
    @Autowired
    private PersonRepository underTest;
    @Test
    void itShouldUpdatePersonsEmail() {
        //given
        var oldEmails = new String[]{"example1@email.com", "example2@email.com", "example3@email.com"};
        var newEmails = new String[]{"new1@email.com","new2@email.com","new3@email.com"};
        Person person1 = new Person(1L,oldEmails[0]);
        Person person2 = new Person(2L,oldEmails[1]);
        Person person3 = new Person(3L,oldEmails[2]);
        underTest.save(person1);
        underTest.save(person2);
        underTest.save(person3);
        underTest.updatePersonEmail(1L,newEmails[0]);
        underTest.updatePersonEmail(2L,newEmails[1]);
        underTest.updatePersonEmail(3L,newEmails[2]);

        for(int i = 0; i<3; i++){
            //when
            Long l = (long) (i+1);
            String mail = underTest.findById(l).get().getEmail();
            String ref = newEmails[i];
            boolean same = mail.equals(ref);

            //then
            assertTrue(same);
        }
    }
}