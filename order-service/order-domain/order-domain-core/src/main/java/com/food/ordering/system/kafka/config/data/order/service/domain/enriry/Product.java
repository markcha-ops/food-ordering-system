package com.food.ordering.system.kafka.config.data.order.service.domain.enriry;

import com.bood.ordering.system.domain.entity.BaseEntity;
import com.bood.ordering.system.domain.valueobject.BaseId;
import com.bood.ordering.system.domain.valueobject.Money;
import com.bood.ordering.system.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId value, String name, Money price) {
        super.setId(value);
        this.name = name;
        this.price = price;
    }
    public Product(ProductId value) {
        super.setId(value);
    }
    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public void updateWithConfirmedNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
