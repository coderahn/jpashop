package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

//JPA내장타입
@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
