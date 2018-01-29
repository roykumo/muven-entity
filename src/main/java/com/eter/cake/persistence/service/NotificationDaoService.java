package com.eter.cake.persistence.service;

import java.util.List;

import com.eter.cake.persistence.entity.ProductType;
import com.eter.cake.persistence.entity.rest.SaleNotification;
import com.eter.cake.persistence.entity.rest.StatusNotification;


public interface NotificationDaoService extends BaseService{
	List<SaleNotification> getSaleNotifications(ProductType type);
	List<StatusNotification> getStatusNotification(ProductType type, String barcode);
	List<StatusNotification> getStatusNotification(ProductType type, String category, String barcode, String group);
}
