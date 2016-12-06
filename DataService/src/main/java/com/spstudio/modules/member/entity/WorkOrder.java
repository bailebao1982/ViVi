package com.spstudio.modules.member.entity;

import com.spstudio.modules.product.entity.Product;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

/**
 *
 * @author wewezhu
 */
public class WorkOrder {
    Date registerDate;

    Time registerTime;

    Set<ServiceProvider> sp;

    Set<Member> customer;

    Product consumeProduct;

    String comment;

    String Rate;

    Date createDate;

    Time creationTime;

    String Status;

    Date confirmTime;

    String serviceLocation;
}