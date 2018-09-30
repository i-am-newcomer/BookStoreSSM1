package com.zy.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.plugin.Intercepts;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/*��֤���������������û�ע��͵�¼ʱ��֤����Ϣ�ļ��*/
public class CheckCodeInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String in_checkcode = request.getParameter("in_checkcode");
		String checkcode =  (String)request.getSession().getAttribute("checkcode");
		System.out.println(in_checkcode);
		if(checkcode.equals(in_checkcode)) {
			return invocation.invoke();
		}
		request.setAttribute("checkcode_result", "��֤���������");
		return Action.ERROR;
	}
	

}
