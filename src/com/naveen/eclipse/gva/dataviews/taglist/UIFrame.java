package com.naveen.eclipse.gva.dataviews.taglist;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class UIFrame extends javax.swing.JFrame {

	public JFrame uiWindow; 
	public JTextArea userText;
	
	public UIFrame(String title)
	{
		uiWindow = new JFrame(title);
		//uiWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		uiWindow.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				uiWindow.setVisible(false);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		uiWindow.setSize(new Dimension(1000, 700));
		uiWindow.setPreferredSize(new Dimension(800, 700));
//		LayoutManager gridLayout2;
		uiWindow.setLayout(new GridLayout(1,1));
		userText = new JTextArea();
		
		userText.setEditable(false);
		userText.setVisible(true);
		
		uiWindow.add(userText);	
		
	}
	
	public void setText(String text)
	{
		userText.setText(text);
	
		uiWindow.revalidate();  
		uiWindow.repaint();
	}
		
}
