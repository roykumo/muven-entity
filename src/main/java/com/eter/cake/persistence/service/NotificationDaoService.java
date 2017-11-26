package com.eter.cake.persistence.service;

import java.util.List;

import com.eter.cake.persistence.entity.ProductType;
import com.eter.cake.persistence.entity.rest.SaleNotification;


public interface NotificationDaoService extends BaseService{
	List<SaleNotification> getSaleNotifications(ProductType type);
}
