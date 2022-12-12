package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") //fk 매핑 / 여기 값을 셋팅하면 member_id fk값이 변함
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    //fk를 order,delivery중 어디에 둘까 ? -> 엑세스많은 곳(ORDERS)
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //jdk1.8

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태(ORDER, CANCEL)
}
