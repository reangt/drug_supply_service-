package com.xz.domain;

import lombok.Data;

import java.util.List;

@Data
public class OrdersCart {
    private UserOrders userOrders;
    private List<ShoppingCart> shoppingCarts;
}
