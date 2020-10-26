import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;
/* MyPanel is a JPanel with a background image, that can be set using the method setBackground(BufferedImage value). */
public class MyPanel extends JPanel implements KeyListener{
	public boolean play=false;
	private BufferedImage img;
	private boolean flagRect=false, flagNum=false, left=false, right=false, up=false, down=false, flagSolution=false;
	private int height1, num, circleX, circleY;
	private final static Font font=new Font("Serif", Font.PLAIN, 45);
	private boolean flagMaze=false;
	public int width, height;
	private Image crab, crabdown;
	
	private static final int WALL = 0;
    private static final int SPACE = 1;
	public byte[][] data;
	private Random rand=new Random();
	public static final int squareSize=30;
	private static int dx, dy, dxtemp, dytemp;
	public static int posx, posy;
	public static int dxstart, dystart, datax, datay;
	public MyPanel() {
		addKeyListener(this);
    }
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }
	public void setBackground(BufferedImage value) {
        if (value != img) {
            this.img = value;
            
        }
    }
	public void drawRect(int height1){
		this.height1=height1;
		flagRect=true;
        
	}
	public void drawNumber(int num) {
		if (play) {
            this.num=num;
		    flagNum=true;
		    repaint(new Rectangle(300/2-3*300/5/2, height1/2, 3*300/5, height1/8));
		}
	}
	public void drawMaze(int dx, int dy, int width, int height){
		this.dx=dx;
		this.dy=dy;
		datax=1;
		datay=2;
		posx=dx;
		posy=dy+2*squareSize;
		dxstart=posx;
		dystart=posy;
		dxtemp=dx;
		dytemp=dy;
		this.width=width;
		this.height=height;
		flagMaze=true;
		
	}
	public void drawCircle (int x, int y) {
		circleX=x;
		circleY=y;
		flagSolution=true;
		int r=squareSize/3;
		x=dxtemp+squareSize*circleX+(squareSize-r)/2;
		y=dytemp+squareSize*circleY+(squareSize-r)/2;
		paintImmediately(x, y, r, r);
	}
    private void carve(int x, int y) {

        final int[] upx = { 1, -1, 0, 0 };
        final int[] upy = { 0, 0, 1, -1 };

        int dir = rand.nextInt(4);
        int count = 0;
        while(count < 4) {
            final int x1 = x + upx[dir];
            final int y1 = y + upy[dir];
            final int x2 = x1 + upx[dir];
            final int y2 = y1 + upy[dir];
            if(data[x1][y1] == WALL && data[x2][y2] == WALL) {
               data[x1][y1] = SPACE;
               data[x2][y2] = SPACE;
               carve(x2, y2);
            } else {
               dir = (dir + 1) % 4;
               count += 1;
            }
        }
    }
	private void generate() {
        for(int x = 0; x < width; x++) {
            data[x] = new byte[height];
            for(int y = 0; y < height; y++) {
               data[x][y] = WALL;
            }
        }
        for(int x = 0; x < width; x++) {
            data[x][0] = SPACE;
            data[x][height - 1] = SPACE;
        }
        for(int y = 0; y < height; y++) {
            data[0][y] = SPACE;
            data[width - 1][y] = SPACE;
        }

        data[2][2] = SPACE;
        carve(2, 2);

        data[1][2] = SPACE;
        data[width - 2][height - 3] = SPACE;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img!=null)
            g.drawImage(img, 0, 0, this);
		if (flagRect || flagNum){
			g.setColor(Color.BLACK);
			g.fillRect(300/2-3*300/5/2, height1/2, 3*300/5, height1/8 );
			flagRect=false;
		}
		if (flagNum){
			g.setFont(font);
		    FontMetrics metrics = g.getFontMetrics(font);
			g.setColor(Color.RED);
			String strnum="";
			if (num<10)
				strnum="00:0"+String.valueOf(num);
			else if (num<60)
				strnum="00:"+String.valueOf(num);
			else if (num<600 && num%60<10)
				strnum="0"+String.valueOf(num/60)+":0"+String.valueOf(num%60);
			else if (num<600 && num%60<60)
				strnum="0"+String.valueOf(num/60)+":"+String.valueOf(num%60);
			else if (num>=600 && num%60<10)
				strnum=String.valueOf(num/60)+":"+"0"+String.valueOf(num%60);
			else if (num>=600 && num%60<60)
				strnum=String.valueOf(num/60)+":"+String.valueOf(num%60);
			    g.drawString(strnum, 300/2-metrics.stringWidth(strnum)/2 , height1/2+metrics.getHeight()/ 2+metrics.getAscent()-metrics.getDescent());
			flagNum=false;
		}
		if (flagMaze) {
			data=new byte[width][];
		    generate();
			g.setColor(Color.BLACK);
			try {
				crab=ImageIO.read(new File("Images/crab.png"));
				crabdown=ImageIO.read(new File("Images/crabdown.png"));
		        g.drawImage(crab, dx+squareSize, dy+2*squareSize, this);
				g.drawImage(ImageIO.read(new File("Images/seaweed.png")), dx+(width-2)*squareSize, dy+(height-3)*squareSize, this);
		    }
		    catch (IOException e) {
                e.printStackTrace();
            } 
			
		    for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    if(data[x][y] == WALL) {
                        g.fillRect(dx, dy, squareSize, squareSize);
                    } 
					dx+=squareSize;
                }
                dx=dxtemp;
				dy+=squareSize;
            }
			flagMaze=false;
	    }
		if (flagSolution) {
			int r=squareSize/3;
			int x=dxtemp+squareSize*circleX+(squareSize-r)/2;
			int y=dytemp+squareSize*circleY+(squareSize-r)/2;
			g.setColor(Color.YELLOW);
			g.fillOval(x, y, r, r);
			flagSolution=false;
		}
		if (right && play) {
			g.drawImage(crab, posx+squareSize, posy, this);
			right=false;
		}
		if (left && play) {
			g.drawImage(crab, posx+squareSize, posy, this);
            left=false;
		}
		if (down && play) {
			g.drawImage(crabdown, posx+squareSize, posy, this);
            down=false;
		}
		if (up && play) {
            g.drawImage(crabdown, posx+squareSize, posy, this);
            up=false;
		}
    }
	public void keyPressed(KeyEvent e) { 
	    if (play){
        if(e.getKeyCode()== KeyEvent.VK_RIGHT) {
			if ((datax!=width-2 || datay!=height-3) && data[datax+1][datay]==SPACE) {
			    right=true;
			    repaint(new Rectangle(posx+squareSize, posy, 2*squareSize, squareSize));
			    posx=posx+squareSize;
			    datax=datax+1;
			}
			if (datax==width-2 && datay==height-3) {
				play=false;
				ImageIcon icon = new ImageIcon("Images/crab1.png");
			    JOptionPane.showMessageDialog(this, "        Yay!", " ",JOptionPane.INFORMATION_MESSAGE, icon);
				posx=dxstart;
				posy=dystart;
				datax=1;
				datay=2;
			}
		}
        else if(e.getKeyCode()== KeyEvent.VK_LEFT) {
			if ((datax!=1 || datay!=2) && data[datax-1][datay]==SPACE) { 
			    left=true;
			    repaint(new Rectangle(posx, posy, 2*squareSize, squareSize));
			    posx=posx-squareSize;
				datax=datax-1;
			}
		}
        else if(e.getKeyCode()== KeyEvent.VK_DOWN) {
			if (data[datax][datay+1]==SPACE) {
			    down=true;
			    repaint(new Rectangle(posx+squareSize, posy, squareSize, 2*squareSize));
                posy=posy+squareSize;
			    datay=datay+1;
			}
		}
        else if(e.getKeyCode()== KeyEvent.VK_UP) {
			if (data[datax][datay-1]==SPACE) {
			    up=true;
			    repaint(new Rectangle(posx+squareSize, posy-squareSize, squareSize, 2*squareSize));
                posy=posy-squareSize;
			    datay=datay-1;
			}
		}
        }

    }
    public void keyReleased(KeyEvent e) { }
	public void keyTyped(KeyEvent e) { }
	public int getX() {
		return datax;
	}
	public int getY() {
		return datay;
	}
}


