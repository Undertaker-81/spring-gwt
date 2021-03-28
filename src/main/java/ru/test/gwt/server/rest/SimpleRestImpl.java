package ru.test.gwt.server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.test.gwt.server.model.Person;
import ru.test.gwt.server.service.HelloService;
import ru.test.gwt.server.service.PersonService;
import ru.test.gwt.shared.dto.PersonDto;
import ru.test.gwt.shared.dto.StringDto;
import ru.test.gwt.shared.rest.SimpleRest;

import java.util.List;

@RequestMapping("rest/person")
@RestController
public class SimpleRestImpl implements SimpleRest {

    @Autowired
    private HelloService helloService;

    @Autowired
    private PersonService personService;

    @PostMapping("hello")
    @Override
    public StringDto sayHello(@RequestBody StringDto targetName) {
        return helloService.sayHello(targetName);
    }

    @Override
    @GetMapping
    public List<PersonDto> allPerson() {
        return personService.allPersonDto();
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        personService.delete(Integer.parseInt(id));
    }

    @Override
    @PostMapping
    public void create(@RequestBody PersonDto personDto) {
            personService.create(personDto);
    }

    @Override
    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody PersonDto personDto) {
        personService.update(personDto, Integer.parseInt(id));
    }


}
