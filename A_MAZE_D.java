import java.awt.*;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
public class A_MAZE_D extends JFrame {
	private MyPanel panel1, panel2, panel3; 
	private int delay = 1000, i, difficulty, num=0, optionsState=0;
	private JLabel label, loadLabel=new JLabel(new ImageIcon("Images/blackbg.jpg"));
	private JButton btnEasy, btnNormal, btnHard, btnHelp, btnSound, btnOK, btnViewSolution, btnQuit, btnOptions, btnOptionHelp, btnOptionSound;
    private Clip clip, clip2;
    private ReadSound read_sound = new ReadSound();
	private StoreSound write_sound=new StoreSound();
	private boolean sound;
	private final static Font font=new Font("Serif", Font.PLAIN, 35);
	private final static String text="Loading.";
	private Timer loadTime, counter;
	private ImageIcon img = new ImageIcon("Images/crab1.png");
	/* Constructor. All panels are created here and they get switched when needed. */
    public A_MAZE_D() {
		setResizable(false);
		setLayout(null);
		setTitle("A-MAZE-D");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(img.getImage());
		label=new JLabel(new ImageIcon("Images/loading screen.jpg"));
        setContentPane(label); 
		pack();
		setLocationRelativeTo(null);
        setVisible(true);
		
		
		/* Loading sound file */
		read_sound.loadFile("Files/Sound.txt");
		sound=read_sound.getSound();
		
		/* Background music and buttons' sound */
		try {
            File file = new File("Sound/sound.wav");
			File file2 = new File("Sound/buttons.wav");
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(sound);
			}
			if (file2.exists()) {
                AudioInputStream sound2 = AudioSystem.getAudioInputStream(file2);
                clip2 = AudioSystem.getClip();
                clip2.open(sound2);
			}
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
		catch (IOException exp) {
            exp.printStackTrace();
        }	
		catch (LineUnavailableException e) {
            e.printStackTrace();           
        }
		
		/* Menu screen's panel */
	    panel1=new MyPanel();
		try {
		    panel1.setBackground(ImageIO.read(new File("Images/bg.jpg")));
		}
		catch (IOException e) {
            e.printStackTrace();
        } 
	    panel1.setLayout(null);
		
		/*Button Easy*/
        btnEasy=new JButton(new ImageIcon("Images/Buttons/Easy.png"));
	    btnEasy.setPressedIcon(new ImageIcon("Images/Buttons/EasyClicked.png"));
	    btnEasy.setRolloverEnabled(false);
		btnEasy.setBounds(getContentPane().getSize().width/2-125,getContentPane().getSize().height/3+50, 250, 70);
        btnEasy.setFocusPainted(false);
        btnEasy.addActionListener(new btnEasyListener());		
		panel1.add(btnEasy);
		/*Button Normal*/
	    btnNormal=new JButton(new ImageIcon("Images/Buttons/Normal.png"));
		btnNormal.setPressedIcon(new ImageIcon("Images/Buttons/NormalClicked.png"));
		btnNormal.setRolloverEnabled(false);
        btnNormal.setBounds(getContentPane().getSize().width/2-125,getContentPane().getSize().height/3+120, 250, 70);
        btnNormal.setFocusPainted(false);
		btnNormal.addActionListener(new btnNormalListener());
		panel1.add(btnNormal);
		/*Button Hard*/
        btnHard=new JButton(new ImageIcon("Images/Buttons/Hard.png"));
		btnHard.setPressedIcon(new ImageIcon("Images/Buttons/HardClicked.png"));
	    btnHard.setRolloverEnabled(false);
        btnHard.setBounds(getContentPane().getSize().width/2-125,getContentPane().getSize().height/3+190, 250, 70);
        btnHard.setFocusPainted(false);
		btnHard.addActionListener(new btnHardListener());
		panel1.add(btnHard);
		/*Button Sound*/
		if (sound) 
		    btnSound=new JButton(new ImageIcon("Images/Buttons/sound on.png"));
	    else 
			btnSound=new JButton(new ImageIcon("Images/Buttons/sound off.png"));
		btnSound.setRolloverEnabled(false);
		btnSound.setBounds(getContentPane().getSize().width/2-55,getContentPane().getSize().height/3+300, 35, 35);
		btnSound.setFocusPainted(false);
		btnSound.addActionListener(new btnSoundListener());
        panel1.add(btnSound);
		/*Button Help*/
		btnHelp=new JButton(new ImageIcon("Images/Buttons/help.png"));
		btnHelp.setPressedIcon(new ImageIcon("Images/Buttons/helpClicked.png"));
		btnHelp.setRolloverEnabled(false);
		btnHelp.setFocusPainted(false);
		btnHelp.setBounds(getContentPane().getSize().width/2+20,getContentPane().getSize().height/3+300, 35, 35);
		btnHelp.addActionListener(new btnHelpListener());
		panel1.add(btnHelp);
		

		/* Instructions panel */
        panel2=new MyPanel();
		try {
			panel2.setBackground(ImageIO.read(new File("Images/Instructions.jpg")));
		}
		catch (IOException e) {
            e.printStackTrace();
        }
		panel2.setLayout(null);
		/* Button OK */
		btnOK=new JButton(new ImageIcon("Images/Buttons/ok.png"));
		btnOK.setPressedIcon(new ImageIcon("Images/Buttons/okClicked.png"));
		btnOK.setRolloverEnabled(false);
		btnOK.setBounds(getContentPane().getSize().width/2-30,getContentPane().getSize().height/2+280, 60, 60);
        btnOK.setFocusPainted(false);
        btnOK.setBorderPainted(false);				
		btnOK.addActionListener(new btnOKListener());
		panel2.add(btnOK);
		
		/* maze panel */
		panel3=new MyPanel();
		try {
		    panel3.setBackground(ImageIO.read(new File("Images/mazebg.jpg")));
		}
		catch (IOException e) {
            e.printStackTrace();
        } 
	    panel3.setLayout(null);
	    /*Button ViewSolution*/
		ImageIcon solution=new ImageIcon("Images/Buttons/ViewSolution.png");
        btnViewSolution=new JButton(solution);
	    btnViewSolution.setPressedIcon(new ImageIcon("Images/Buttons/ViewSolutionClicked.png"));
	    btnViewSolution.setRolloverEnabled(false);
		btnViewSolution.setBounds(300/2-(solution.getIconWidth()-4)/2,getContentPane().getSize().height/9,solution.getIconWidth()-4, solution.getIconHeight()-4);
        btnViewSolution.setFocusPainted(false);
	    btnViewSolution.setBorderPainted(false);
        btnViewSolution.addActionListener(new ViewSolutionListener());	
	    panel3.add(btnViewSolution);
		
		/* Button Quit */
		ImageIcon quit=new ImageIcon("Images/Buttons/Quit.png");
        btnQuit=new JButton(quit);
	    btnQuit.setPressedIcon(new ImageIcon("Images/Buttons/QuitClicked.png"));
	    btnQuit.setRolloverEnabled(false);
		btnQuit.setBounds(300/2-(quit.getIconWidth()-4)/2,getContentPane().getSize().height/4,quit.getIconWidth()-4, quit.getIconHeight()-4);
        btnQuit.setFocusPainted(false);
	    btnQuit.setBorderPainted(false);
		btnQuit.addActionListener(new btnQuitListener());			
	    panel3.add(btnQuit);
		/* Button Options */
		ImageIcon options=new ImageIcon("Images/Buttons/options.png");
        btnOptions=new JButton(options);
	    btnOptions.setPressedIcon(new ImageIcon("Images/Buttons/optionsClicked.png"));
	    btnOptions.setRolloverEnabled(false);
		btnOptions.setBounds(300/2-(options.getIconWidth())/2,getContentPane().getSize().height-options.getIconHeight(), options.getIconWidth(), options.getIconHeight());
        btnOptions.setFocusPainted(false);
	    btnOptions.setBorderPainted(false);	
        btnOptions.addActionListener(new btnOptionsListener());		
	    panel3.add(btnOptions);
		/* Button optionHelp */
		ImageIcon optionHelp=new ImageIcon("Images/Buttons/optionHelp.png");
        btnOptionHelp=new JButton(optionHelp);
	    btnOptionHelp.setPressedIcon(new ImageIcon("Images/Buttons/optionHelpClicked.png"));
	    btnOptionHelp.setRolloverEnabled(false);
		btnOptionHelp.setBounds(300/2-(optionHelp.getIconWidth())/2,getContentPane().getSize().height-options.getIconHeight()-optionHelp.getIconHeight(), optionHelp.getIconWidth(), optionHelp.getIconHeight());
        btnOptionHelp.setFocusPainted(false);
	    btnOptionHelp.setBorderPainted(false);
		btnOptionHelp.setEnabled(false);
		btnOptionHelp.setDisabledIcon(new ImageIcon("Images/Buttons/disabled.png"));
		btnOptionHelp.addActionListener(new optionHelpListener());
		panel3.add(btnOptionHelp);
		/* Button optionSound */
		ImageIcon optionSound, optionSound2;
		if (sound) {
		    optionSound=new ImageIcon("Images/Buttons/optionSoundOn.png");
		    optionSound2=new ImageIcon("Images/Buttons/optionSoundOnClicked.png");
		}
		else {
			optionSound=new ImageIcon("Images/Buttons/optionSoundOff.png");
		    optionSound2=new ImageIcon("Images/Buttons/optionSoundOfClicked.png");
		}
        btnOptionSound=new JButton(optionSound);
		btnOptionSound.setPressedIcon(optionSound2);
	    btnOptionSound.setRolloverEnabled(false);
		btnOptionSound.setBounds(300/2-(optionSound.getIconWidth())/2,getContentPane().getSize().height-options.getIconHeight()-optionHelp.getIconHeight()-optionSound.getIconHeight(), optionSound.getIconWidth(), optionSound.getIconHeight());
        btnOptionSound.setFocusPainted(false);
	    btnOptionSound.setBorderPainted(false);	
        btnOptionSound.setEnabled(false);
		btnOptionSound.setDisabledIcon(new ImageIcon("Images/Buttons/disabled.png"));	
		btnOptionSound.addActionListener(new optionSoundListener());
        panel3.add(btnOptionSound);	
        panel3.play=true;		
		/*Timer for loading screen*/
		loadTime= new Timer(300, new loadTimeActionListener());
		loadTime.setRepeats(true);
		/*counter*/
		counter=new Timer(1000, new counterActionListener());
		loadTime.setRepeats(true);
		/*Timer for name screen*/
		Timer timer = new Timer(delay, new MyTimerActionListener());
        timer.setRepeats(false); 
		timer.start();
	}
	
	/* Timer's listener. After timer stops the menu screen appears. */
    private class MyTimerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
			if (sound) {
			    clip.start();
			    clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
			setContentPane(panel1);
        	setVisible(true);
		}
    }
	
	/* Sound button's listener. */
	private class btnSoundListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (sound) {
				clip2.setMicrosecondPosition(0);
				clip2.start();
				btnSound.setPressedIcon(new ImageIcon("Images/Buttons/sound off Clicked.png"));
				btnSound.setIcon(new ImageIcon("Images/Buttons/sound off.png"));
				sound=false;
				clip.stop();
				btnOptionSound.setIcon(new ImageIcon("Images/Buttons/optionSoundOff.png"));
				btnOptionSound.setPressedIcon(new ImageIcon("Images/Buttons/optionSoundOffClicked.png"));
			}
			else {
				btnSound.setPressedIcon(new ImageIcon("Images/Buttons/sound on Clicked.png"));
				btnSound.setIcon(new ImageIcon("Images/Buttons/sound on.png"));
				sound=true;
				clip.setMicrosecondPosition(0);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				btnOptionSound.setIcon(new ImageIcon("Images/Buttons/optionSoundOn.png"));
				btnOptionSound.setPressedIcon(new ImageIcon("Images/Buttons/optionSoundOnClicked.png"));
			}
			write_sound.createFile("Files/Sound.txt", sound);
		}
	}
    /* Easy button's listener. */
	private class btnEasyListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if (sound) {
			    clip2.setMicrosecondPosition(0);
			    clip2.start();
			}
			setContentPane(loadLabel);
			setVisible(true);
            difficulty=1;			
            paintGame();

        }
	}
	
	/* Normal button's listener. */
	private class btnNormalListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if (sound) {
			    clip2.setMicrosecondPosition(0);
			    clip2.start();
			}
			setContentPane(loadLabel);
			setVisible(true);
			difficulty=2;
            paintGame();

        }
	}
	
	/* Hard button's listener. */
	private class btnHardListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if (sound) {
			    clip2.setMicrosecondPosition(0);
			    clip2.start();
			}
			setContentPane(loadLabel);
			setVisible(true);
			difficulty=3;
            paintGame();

        }
	}
	
	/* Help button's listener. */
	private class btnHelpListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (sound) {
			    clip2.setMicrosecondPosition(0);
			    clip2.start();
			}
			
            setContentPane(panel2);
			setVisible(true);

        }
	}
	
	/* OK button's listener. */
	private class btnOKListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (sound) {
			    clip2.setMicrosecondPosition(0);
			    clip2.start();
			}
            setContentPane(panel1);
			setVisible(true);

        }
	}
	/* Quit button's listener. */
	private class btnQuitListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if (sound) {
			    clip2.setMicrosecondPosition(0);
			    clip2.start();
			}
			counter.stop();
            setContentPane(panel1);
			setVisible(true);
			num=0;
			btnOptionHelp.setEnabled(false);
			btnOptionSound.setEnabled(false);
			optionsState=0;
			panel3.play=true;
			panel3.posx=panel3.dxstart;
			panel3.posy=panel3.dystart;
        }
	}
	/* Creates loading screen and maze screen */
	private void paintGame() {
		i=0;
        loadTime.restart();
		if (i==4) {
			loadTime.stop();
		}
		if (difficulty==1)
			panel3.drawMaze(474, 171, 25, 15);
		else if (difficulty==2) 
			panel3.drawMaze(383, 80, 31, 21);
		
		else if (difficulty==3)
			panel3.drawMaze(294, -11, 37, 27);
		difficulty=0;
    }
	/* loadTime listener */
    private class loadTimeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Graphics g=getGraphics();
		    g.setFont(font);
            g.setColor(Color.WHITE);
		    FontMetrics metrics = g.getFontMetrics(font);
			i+=1;
            if (i==1) 
			    g.drawString("Loading.", getContentPane().getSize().width/2-metrics.stringWidth(text)/2 ,getContentPane().getSize().height/2+metrics.getHeight()/ 2+metrics.getAscent());
			else if (i==2)
				g.drawString("Loading..", getContentPane().getSize().width/2-metrics.stringWidth(text)/2 ,getContentPane().getSize().height/2+metrics.getHeight()/ 2+ metrics.getAscent());
			else if (i==3)
				g.drawString("Loading...", getContentPane().getSize().width/2-metrics.stringWidth(text)/2 ,getContentPane().getSize().height/2+ metrics.getHeight()/ 2+ metrics.getAscent());
		    else if (i==4) {
				panel3.drawRect(getContentPane().getSize().height);
				setContentPane(panel3);
				setVisible(true);
				panel3.drawNumber(num);
				counter.restart();
			}
        }
    }
	/* counter listener */
	private class counterActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			num+=1;
			panel3.drawNumber(num);
		}
	}
    /* Options button's listener. */
	private class btnOptionsListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if (sound) {
			    clip2.setMicrosecondPosition(0);
			    clip2.start();
			}
            if (optionsState==0) {
                btnOptionHelp.setEnabled(true);
				btnOptionSound.setEnabled(true);
				optionsState=1;
			}
			else {
                btnOptionHelp.setEnabled(false);
				btnOptionSound.setEnabled(false);
				optionsState=0;
			}
			panel3.requestFocus();
        }
	}
	/* optionSound button's listener. */
	private class optionSoundListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if (sound) {
				clip2.setMicrosecondPosition(0);
				clip2.start();
				btnOptionSound.setIcon(new ImageIcon("Images/Buttons/optionSoundOff.png"));
				btnOptionSound.setPressedIcon(new ImageIcon("Images/Buttons/optionSoundOffClicked.png"));
				sound=false;
				clip.stop();
				btnSound.setIcon(new ImageIcon("Images/Buttons/sound off.png"));
				btnSound.setPressedIcon(new ImageIcon("Images/Buttons/sound off Clicked.png"));
			}
			else { 
			    btnOptionSound.setIcon(new ImageIcon("Images/Buttons/optionSoundOn.png"));
				btnOptionSound.setPressedIcon(new ImageIcon("Images/Buttons/optionSoundOnClicked.png"));
                sound=true;
				clip.setMicrosecondPosition(0);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				btnSound.setIcon(new ImageIcon("Images/Buttons/sound on.png"));
				btnSound.setPressedIcon(new ImageIcon("Images/Buttons/sound on Clicked.png"));
			}
			write_sound.createFile("Files/Sound.txt", sound);
			panel3.requestFocus();
        }
	}
	/* optionHelp button's listener. */
	private class optionHelpListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			btnOptionHelp.setEnabled(false);
		    btnOptionSound.setEnabled(false);
			optionsState=0;
            JOptionPane.showMessageDialog(panel3, "        Use arrow keys to move the crab!", "Help",JOptionPane.PLAIN_MESSAGE);
			panel3.requestFocus();
        }
	}
	/* View Solution button's listener. */
	private class ViewSolutionListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			clip2.setMicrosecondPosition(0);
			clip2.start();
			counter.stop();
			panel3.play=false;
			Stack<int[]> stack=new Stack();
			int x=panel3.getX();
			int y=panel3.getY();
			int[] entrance = {x, y};
			int[] currentCell=entrance;
			stack.push(currentCell);
			int rows=panel3.height;
			int cols=panel3.width;
            byte[][] maze=panel3.data;
			maze[x][y]=2;
			while (!stack.isEmpty()) { 
			    try {
                    currentCell=stack.peek();
				    x=currentCell[0];
				    y=currentCell[1];
				    if (x==cols-2 && y==rows-3) {
					   break;
					}
				    else if (up(maze, currentCell)) {
					    currentCell[0]=x;
						currentCell[1]=y-1;
						stack.push(new int[] {currentCell[0], currentCell[1]});
						maze[currentCell[0]][currentCell[1]]=2;
				    }
					else if (down(maze, currentCell, rows)) {
						currentCell[0]=x;
						currentCell[1]=y+1;
						stack.push(new int[] {currentCell[0], currentCell[1]});
						maze[currentCell[0]][currentCell[1]]=2;
					}
					else if (left(maze, currentCell)){
						currentCell[0]=x-1;
						currentCell[1]=y;
						stack.push(new int[] {currentCell[0], currentCell[1]});
						maze[currentCell[0]][currentCell[1]]=2;
					}
					else if (right(maze, currentCell, cols)){
						currentCell[0]=x+1;
						currentCell[1]=y;
						stack.push(new int[] {currentCell[0], currentCell[1]});
						maze[currentCell[0]][currentCell[1]]=2;
					}
					else {
						System.out.println("Out: "+currentCell[0]+", "+currentCell[1]);////////////////////////////
					    stack.pop();
				    }
	            }
	            catch (NoSuchElementException nsee) {
		            nsee.printStackTrace();
	            }
		    }
			System.out.println(stack.size());
			for (int i=stack.size(); i>=3; i--) {
				currentCell=stack.get(i);
				panel3.drawCircle(currentCell[0], currentCell[1]);
			}
			for (int i=stack.size(); i>=1; i--) {////////////////////////////////////////////////////////
				currentCell=stack.get(i);
				System.out.println(currentCell[0]+", "+currentCell[1]);
			}
        }
		public boolean up(byte[][] maze, int[] cell) {
		    int xcord=cell[0];
            int ycord=cell[1];
            if (ycord==2)
			    return false;
		    else
			    return maze[xcord][ycord-1]==1;
	    }
	
	    public boolean down(byte[][] maze, int[] cell, int rows) {
		    int xcord=cell[0];
            int ycord=cell[1];
            if (ycord==rows-3)
			    return false;
		    else
			    return maze[xcord][ycord+1]==1;
	    }
	
	    public boolean left(byte[][] maze, int[] cell) {
		    int xcord=cell[0];
            int ycord=cell[1];
            if (xcord==1)
			    return false;
		    else
			    return maze[xcord-1][ycord]==1;
	    }
	
	    public boolean right(byte[][] maze, int[] cell, int cols) {
	        int xcord=cell[0];
            int ycord=cell[1];
            if (xcord==cols-2)
			    return false;
		    else
			    return maze[xcord+1][ycord]==1;
	    }
	}
	/* Main. */
    public static void main(String args[]) {
		new A_MAZE_D();
	}
}



