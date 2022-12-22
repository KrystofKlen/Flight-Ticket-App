package com.example.app.service;

import com.example.app.model.Person;
import com.example.app.repository.PersonRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    public Optional<Person> getPerson(Long id){
        return personRepository.findById(id);
    }
    public void addPerson(Person person){
        personRepository.save(person);
    }
    public void deletePerson(Long id){
        personRepository.deleteById(id);
    }
    public Optional<Person> findByEmail(String email){
        return personRepository.findByEmail(email);
    }

    public List<Person> getAll(){
        return personRepository.findAll();
    }

    public void updatePersonEmail(Long personID,String newEmail){
        personRepository.updatePersonEmail(personID,newEmail);
    }

}
