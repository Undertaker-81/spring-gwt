package ru.test.gwt.server.repository;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.test.gwt.server.model.Person;
import ru.test.gwt.shared.dto.PersonDto;

/**
 * @author Panfilov Dmitriy
 * 26.03.2021
 */
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
