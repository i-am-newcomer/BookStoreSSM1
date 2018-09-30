package com.zy.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.zy.entity.Book;
import com.zy.entity.Order;
import com.zy.entity.OrderItem;
import com.zy.entity.User;
import com.zy.service.BookService;
import com.zy.service.OrderItemService;
import com.zy.service.OrderService;

@Controller("orderAction")
@Scope("prototype")
public class OrderAction {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private BookService bookService;
	private Order order;
	private List<OrderItem> orderitemList;
	private int bid;
	private int quantity;
	
	/*���Ӷ������������������
	 * 1�����������ڣ����Ӷ���
	 * 2�������Ѵ��ڣ����¶���
	 * */
	public String addOrder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		//����book��id��ѯ�õ���Ӧ��book����
		Book book = bookService.selectBookById(bid);
		//�����û�id��ѯ��Ӧ�Ķ���order����
		order = orderService.selectOrderByCId(user.getId());
		//�����û����ﳵ�޶�����Ϣ������������order
		if(order==null) {
			order = new Order();
			order.setUser(user);
			if(orderService.insertOrder(order, book, quantity)>0) {
				return Action.SUCCESS;
			}
		}
		//�����û���������ж�����Ϣ������¶���
		else {
			if(orderService.updateOrder(order, book, quantity)>0) {
				return Action.SUCCESS;
			}
		}
		return Action.ERROR;
	}
	
	/*��ʾ�û����ﳵ�еĶ�������*/
	public String showOrder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		order = orderService.selectOrderByCId(user.getId());
		if(order == null) {
			return "empty";
		}
		orderitemList = orderItemService.selectOrderItemByOId(order.getId());
		System.out.println(orderitemList);
		return Action.SUCCESS;
	}
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public void setOrderItemService(OrderItemService orderItemService) {
		this.orderItemService = orderItemService;
	}
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public List<OrderItem> getOrderitemList() {
		return orderitemList;
	}
	public void setOrderitemList(List<OrderItem> orderitemList) {
		this.orderitemList = orderitemList;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	

}
