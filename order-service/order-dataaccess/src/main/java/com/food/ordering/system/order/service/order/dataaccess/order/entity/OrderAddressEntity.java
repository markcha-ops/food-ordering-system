package com.food.ordering.system.order.service.order.dataaccess.order.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_address")
@Entity
public class OrderAddressEntity {
    @Id
    private UUID id;

    @JoinColumn(name = "ORDER_ID")
    @OneToOne(cascade = CascadeType.ALL)
    private OrderEntity order;

    private String street;
    private String postalCode;
    private String city;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderAddressEntity that = (OrderAddressEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
