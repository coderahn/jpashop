package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    //EnumType.ORDINAL은 사용금지(1,2로 나오며 사이에 다른 컬럼값 업데이트되면 망함)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP
}
