package ru.test.gwt.server.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.gwt.server.model.Person;
import ru.test.gwt.server.repository.PersonRepository;
import ru.test.gwt.shared.dto.PersonDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> allPerson (){
        log.info("get all person");
        return personRepository.findAll();
    }

    public List<PersonDto> allPersonDto(){
        log.info("get all person DTO");
        return allPerson().stream()
                                    .map(person -> new PersonDto(person.getId(), person.getFirstName(), person.getLastName(), person.getMiddleName()))
                                    .collect(Collectors.toList());
    }

    public void delete(int id){
        personRepository.deleteById(id);
    }
}
