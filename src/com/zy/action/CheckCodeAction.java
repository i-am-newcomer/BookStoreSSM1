package com.zy.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;

@Controller("checkCodeAction")
@Scope("prototype")
public class CheckCodeAction {
	
	public String execute() throws IOException {
		//������Ӧ�ײ�
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("image/jpeg");
		response.setCharacterEncoding("utf-8");
		//���ò�����ͼƬ
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		//����ͼƬ���ȺͿ��
		int width = 80;
		int height = 25;	
		//ͨ��BufferedImage���ȡimage����
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//�õ�����g
		Graphics g = image.getGraphics();
		//�û���g������ͼƬ
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, width, height);
		//���������
		Random rd = new Random();
		int rdint = rd.nextInt(8999)+1000;
		String rdstr = String.valueOf(rdint);
		//������������ڻỰ������
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("checkcode", rdstr);
		//�������
		g.setColor(Color.BLUE);
		g.setFont(new Font("", Font.PLAIN, 22));
		g.drawString(rdstr, 15, 24);
		//��100�������
		for(int i=0; i<100; i++) {
			int x = rd.nextInt(width);
			int y = rd.nextInt(height);
			g.drawOval(x, y, 1, 1);		
		}
		//�ͷ���Դ
		g.dispose();
		//���ͼƬ
		ImageIO.write(image, "jpeg", response.getOutputStream());
		
		return Action.NONE;
	}

}
