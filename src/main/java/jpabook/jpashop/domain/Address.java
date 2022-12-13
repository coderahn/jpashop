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

    //엔티티나 임베디드 타입(@Embedable)은 자바 기본생성자를 public,protected로 해야함
    protected Address () {

    }

    //Setter제거하고 생성자에서 모든 값을 초기화해서 변경 불가능한 클래스를 만들자자
   public Address(String city, String stree, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
