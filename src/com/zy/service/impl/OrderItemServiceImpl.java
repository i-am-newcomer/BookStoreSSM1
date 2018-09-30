package com.zy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zy.dao.OrderItemDao;
import com.zy.entity.Book;
import com.zy.entity.Order;
import com.zy.entity.OrderItem;
import com.zy.service.OrderItemService;

@Service("orderItemService")
public class OrderItemServiceImpl implements OrderItemService {
	
	@Autowired
	private OrderItemDao orderItemDao;
	
	public void setOrderItemDao(OrderItemDao orderItemDao) {
		this.orderItemDao = orderItemDao;
	}
	

	@Override
	public List<OrderItem> selectOrderItemByOId(int oid) {
		return orderItemDao.selectOrderItemByOId(oid);
	}
	
	@Override
	public OrderItem selectOrderItemById(Map<String, Integer> idmap) {
		return orderItemDao.selectOrderItemById(idmap);
	}

	/*orderitems���в����µĶ���������*/
	@Override
	public int insertOrderItem(Order order, Book book, int quantity, float price) {
		OrderItem orderitem = new OrderItem();
		int item = 0;
		orderitem.setOrder(order);
		orderitem.setBook(book);
		orderitem.setQuantity(quantity);
		orderitem.setPrice(price);
		//����order��id��ѯ�õ���Ӧ�Ķ��������б����б�Ϊ�գ���item����ֵ1������Ϊ�գ�������Ϊ��Ӧ�б��е����ֵ+1
		List<OrderItem> itemList = selectOrderItemByOId(order.getId());
		if(itemList.size()>0) {
			item = itemList.get(itemList.size()-1).getItem() + 1;
		}
		else {
			item = 1;
		}
		orderitem.setItem(item);
		return orderItemDao.insertOrderItem(orderitem);
	}

	/*����orderitems�������еĶ���������*/
	@Override
	public int updateOrderItem(Order order, Book book, int quantity, float price) {
		//����book��id�Լ�order��id��ѯ�õ���Ӧ��orderitem���Ϊ�գ���ִ�в������������Ϊ�գ���ִ�и��²���
		HashMap<String, Integer> idmap = new HashMap<>();
		idmap.put("oid", order.getId());
		idmap.put("bid", book.getId());
		OrderItem orderitem = selectOrderItemById(idmap);
		if(orderitem==null) {
			return insertOrderItem(order, book, quantity, price);
		}
		//���º�ļ۸�
		float update_price = orderitem.getPrice() + price;
		//���º������
		int update_quantity = orderitem.getQuantity() + quantity;
		orderitem.setPrice(update_price);
		orderitem.setQuantity(update_quantity);
		return orderItemDao.updateOrderItem(orderitem);
	}

}
