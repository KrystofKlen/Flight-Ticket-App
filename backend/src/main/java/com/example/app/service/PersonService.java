package com.example.app.service;

import com.example.app.model.Person;
import com.example.app.repository.PersonRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final EntityManagerFactory entityManagerFactory;
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
        CriteriaBuilder cb = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);

        Root<Person> person = cq.from(Person.class);
        Predicate personEmail = cb.equal(person.get("email"),email);
        cq.where(personEmail);
        Optional<TypedQuery<Person> > optQuery = Optional.of(entityManagerFactory.createEntityManager().createQuery(cq));
        if(optQuery.isEmpty() || optQuery.get().getResultList().isEmpty()){
            return Optional.empty();
        }
        var lst = optQuery.get().getResultList();
        return Optional.of(lst.stream().findFirst().get());
    }

    public List<Person> getAll(){
        return personRepository.findAll();
    }

    public void updatePersonEmail(Long personID,String newEmail){
        personRepository.updatePersonEmail(personID,newEmail);
    }

}
