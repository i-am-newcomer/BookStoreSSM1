package com.zy.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.zy.entity.User;
import com.zy.service.UserService;

@Controller("userAction")
@Scope("prototype")
public class UserAction {
	
	@Autowired
	private UserService userService;
	private User user;
	private String confirm_pwd;
	private String logName;
	private String logPwd;
	
	/*�û�ע��*/
	public String register() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String result = userService.register(user, confirm_pwd);
		//ע��ɹ�
		if(result.equals("success")) {
			return Action.SUCCESS;
		}
		//ע��ʧ��
		request.setAttribute("register_result", result);
		return Action.ERROR;
	}
	
	/*����û��Ƿ��Ѿ���¼�����ѵ�¼����ʾ�ѵ�¼����û����ת����¼ҳ��*/
	public String beforeLogin() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User)session.getAttribute("user");
		if(user!=null) {
			return Action.SUCCESS;
		}
		return Action.LOGIN;
	}
	
	/*�û���¼*/
	public String login() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Object result = userService.login(logName, logPwd);
		//��result��user���ͣ����ʾ��¼�ɹ�������"success"����user��Ϣ�����ڻỰ������
		if(result.getClass().isInstance(new User())) {
			user = (User)result;
			session.setAttribute("user", user);
			return Action.SUCCESS;
		}
		//��result����user���ͣ����ʾ��½ʧ�ܣ����ص���¼ҳ�沢��������Ϣresult����������������
		request.setAttribute("login_result", (String)result);
		return Action.LOGIN;
	}
	
	/*�˳���¼*/
	public String logout() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("user", null);
		return Action.LOGIN;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String getConfirm_pwd() {
		return confirm_pwd;
	}

	public void setConfirm_pwd(String confirm_pwd) {
		this.confirm_pwd = confirm_pwd;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getLogPwd() {
		return logPwd;
	}

	public void setLogPwd(String logPwd) {
		this.logPwd = logPwd;
	}
	

}
