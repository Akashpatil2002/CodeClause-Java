import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.text.DecimalFormat;

public class watchdemo extends JFrame implements ActionListener
{
	Timer t;
	JOptionPane dialog;
	JLabel display;
	DecimalFormat df;
	JPanel watchPanel,controlPanel;
	JButton ssBtn,reBtn,prBtn,about;
	public watchdemo()
	{
		ButtonPerformed btnPerform=new ButtonPerformed();
		ssBtn=new JButton("Start");
		reBtn=new JButton("Reset");
		prBtn=new JButton("Pause");
		about=new JButton("About");
		prBtn.setEnabled(false);
		watchPanel=new JPanel();
		controlPanel=new JPanel();
		df=new DecimalFormat("00");
		display=new JLabel();
		display.setText("Elapse : "+df.format(hour)+" : "+df.format(minute)
		+" : "+df.format(second)+" : "+df.format(csecond));
		dialog=new JOptionPane();
		display.setFont(new Font("Arial",Font.BOLD,24));
		t=new Timer(10,this);
		watchPanel.setLayout(new FlowLayout());
		controlPanel.setLayout(new FlowLayout());
		getContentPane().setLayout(new BorderLayout());
		watchPanel.add(display);
		controlPanel.add(ssBtn);//Start/Stop
		controlPanel.add(prBtn);//Pause/Resume
		controlPanel.add(reBtn);//Reset
		controlPanel.add(about);
		getContentPane().add(watchPanel,"North");
		getContentPane().add(controlPanel,"Center");
		prBtn.addActionListener(btnPerform);
		ssBtn.addActionListener(btnPerform);
		reBtn.addActionListener(btnPerform);
		about.addActionListener(btnPerform);
		setSize(350,130);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void startTimer()
	{
		t.start();
	}
	void stopTimer()
	{
		t.stop();
		csecond=0;
		second=0;
		minute=0;
		hour=0;
	}
	void pauseTimer()
	{
		t.stop();
	}
	void resumeTimer()
	{
		t.start();
	}
	void resetTimer()
	{
		csecond=0;
		second=0;
		minute=0;
		hour=0;
		display.setText("Elapse : "+df.format(hour)+" : "+df.format(minute)
		+" : "+df.format(second)+" : "+df.format(csecond));
	}
	private int csecond=0;
	private int second=0;
	private int minute=0;
	private int hour=0;
	private boolean isPause=true;
	private boolean isStart=true;
	public void actionPerformed(ActionEvent e){
		csecond++;
		if(csecond==100)
		{
			second++;
			csecond=0;
		}
		if(second==60){
			minute++;
			second=0;
		}
		if(minute==60){
			hour++;
			minute=0;
		}

		display.setText("StopWatch : "+df.format(hour)+" : "+df.format(minute)
		+" : "+df.format(second)+" : "+df.format(csecond));
	}
	private class ButtonPerformed implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==ssBtn)
			{
				if(isStart)
				{
					startTimer();
					isStart=false;
					ssBtn.setText("Stop");
					prBtn.setEnabled(true);

				}
				else
				{
					isStart=true;
					ssBtn.setText("Start");
					stopTimer();
					isPause=true;
					prBtn.setText("Pause");
					prBtn.setEnabled(false);
				}
			}
			if(e.getSource()==prBtn)
			{
				if(isPause)
				{
					prBtn.setText("Resume");
					pauseTimer();
					isPause=false;
				}
				else
				{
					prBtn.setText("Pause");
					startTimer();
					isPause=true;
				}
			}
			if(e.getSource()==reBtn)
			{
				resetTimer();
			}
			if(e.getSource()==about)
			{
				dialog.showMessageDialog(null,"StopWatch Demo.\nCreated by "+
				"Akash Sonawane\nEmail : patilakash2601@gmail.com");
			}
		}
	}
	public static void main(String args[])
	{
		watchdemo watch=new watchdemo();

	}

}