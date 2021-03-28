package ru.test.gwt.server.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Panfilov Dmitriy
 * 26.03.2021
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity{

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "middle_name")
    String middleName;

    public Person(Integer id, String firstName, String lastName, String middleName) {
    }
}
