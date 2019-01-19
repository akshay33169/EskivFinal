import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.event.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class Final extends JPanel implements Runnable,KeyListener,MouseListener
{
	JFrame frame=new JFrame();
	Thread t;

	int Ox=200;
	int Oy=200;
	int a; //rectangle x coordinate
	int b; //rectangle y coordinate
	int score;
	Polygon p;
	Rectangle target_rect;
	Rectangle bullet;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	boolean hit=false;
	boolean needToFire, shot;
	boolean Gameover;
	Ellipse2D.Double O;
	int check=0;
	ArrayList<Ball> ballList;
	int highscore=0;
	boolean colorPressed;
	double slope;
	int increment;
	double buly;
	int bul_x;
	int bul_y;
	BufferedImage img;

	int chance; //frequency at which the alien will shoot



	public Final()
	{
		frame.addKeyListener(this);
		addMouseListener(this);
		frame.add(this);
		frame.setSize(1100,700);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setup();
		t=new Thread(this);
		t.start();

		JOptionPane optionPane = new JOptionPane( "", JOptionPane.PLAIN_MESSAGE,JOptionPane.YES_NO_OPTION);

    }

    public void shoot(){
		Rectangle boundary= new Rectangle(126,0,873,600);
		if (shot){

			// bullet.y--;

			buly += slope * (double) increment;

			bullet.y = (int)Math.round(buly);
			bullet.x += increment;

			if (!boundary.contains(bullet)) {
				bullet = new Rectangle(0,0,0,0);
				shot=false;
				needToFire=true;

			}


		}
	}

	public void setup()
	{
		score=0;
		O = new Ellipse2D.Double(Ox,Oy,20,20);

		 img = null;
		ballList=new ArrayList<Ball>();
		ballList.add(new Ball(this));
		Rectangle frame = new Rectangle(126,0,853,600);
		Rectangle target;
		boolean fit=true;
		shot=false;
		needToFire=true;
		bullet = new Rectangle(0,0,0,0);
		try {
			img = ImageIO.read(new File("blueship2.png"));
	}
	catch (IOException e) {
}


		do {

		a= (int)(Math.random()*853)+126;
		b= (int)(Math.random()*579)+1;

		target = new Rectangle(a,b,20,20);
		if (frame.contains(target))    // DETERMINES IF THE TARGET IS STILL CONTAINED WITHIN THE PLAYING SPACE
			fit=true;
		else
		 	fit=false;

		} while (fit=false);

	}



    public void paintComponent(Graphics g)
    {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
			g.setColor(Color.GREEN);
			g.fillRect(0,0,1000,600);
			g.setColor(Color.BLACK);
			g.drawOval((int)O.getX(),(int)O.getY(),(int)O.getWidth(),(int)O.getHeight());
			g.fillOval((int)O.getX(),(int)O.getY(),(int)O.getWidth(),(int)O.getHeight());


			for(int x=0;x<ballList.size();x++)
			{
				g.drawOval((int)ballList.get(x).getX(),(int)ballList.get(x).getY(),(int)ballList.get(x).getWidth(),(int)ballList.get(x).getHeight());
				g.setColor(Color.RED);
				g.fillOval((int)ballList.get(x).getX(),(int)ballList.get(x).getY(),(int)ballList.get(x).getWidth(),(int)ballList.get(x).getHeight());
			}




			//target_rect = new Rectangle(a,b,20,20);
			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(a,b,20,20);
			g.setColor(Color.GRAY);
			g.fillRect(a,b,20,20);

			g.setColor(Color.ORANGE); //Borders between Menu and Playing Field
			g.drawRect(0,0,125,599);

			g.setColor(Color.PINK);
			g.drawRoundRect(15,15,100,60,10,10);
			g.setFont(new Font("Times New Roman",Font.BOLD,15));
			g.drawString("NEW GAME",20,40);

			g.drawString("SCORE:",20,200);
			g.drawString(String.valueOf(score),100,200);

			g.drawString("HIGHSCORE:",5,360);
			g.drawString(String.valueOf(highscore),110,360);

			if (colorPressed==true)
			{

				g.setColor(Color.RED);
				g.fillRoundRect(15,15,100,60,10,10);

				g.setColor(Color.PINK);
				g.drawRoundRect(15,15,100,60,10,10);
				g.setFont(new Font("Times New Roman",Font.BOLD,15));
				g.drawString("NEW GAME",20,40);
			}


			if (shot) {
				g.drawImage(img,bul_x,bul_y,30,30,null);
				g2d.setColor(Color.BLACK);
				g2d.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
			}
	}


	public void run()
	{
		while(true)
		{

			if(!hit)
			{
				//System.out.println(" Before new Rectangle " );
				Rectangle boundary= new Rectangle(126,0,873,600);
				//System.out.println ("After new Rectangle ");

				Rectangle2D boundingBox;


				if(down)
				{

					Rectangle temp= new Rectangle(Ox,Oy+1,20,20);


					if (boundary.contains(temp))
					{
						Oy++;
						O = new Ellipse2D.Double(Ox,Oy,20,20);



						// System.out.println("hit down");
					}

				}

				if(up)
				{

					Rectangle temp= new Rectangle(Ox,Oy-1,20,20);



					if(boundary.contains(temp))
					{
						Oy--;
						O = new Ellipse2D.Double(Ox,Oy,20,20);


						// System.out.println("hit up");
					}
				}

				if(left)
				{


					Rectangle temp= new Rectangle(Ox-1,Oy,20,20);


					if(boundary.contains(temp))
					{
						Ox--;
						O = new Ellipse2D.Double(Ox,Oy,20,20);


						// System.out.println("hit left");
					}
				}

				if(right)
				{
						Rectangle temp= new Rectangle(Ox+1,Oy,20,20);


						if(boundary.contains(temp))
						{
							Ox++;
							O = new Ellipse2D.Double(Ox,Oy,20,20);


							// System.out.println("hit right");
						}
				}
				for(int x=0;x<ballList.size();x++)
					ballList.get(x).move();


				// fire bullets




				if (needToFire) {

					chance= (int)(Math.random()*10000)+1;

					if (chance<=50)
					{
					int distance;
					do {
					 bul_x= (int)(Math.random()*853)+126;
					 bul_y=  (int)(Math.random()*579)+1;
					 distance = (int) Math.sqrt(Math.pow(bul_x-Ox,2)+Math.pow(bul_y-Oy,2));
				 } while (distance < 500);

					bullet=new Rectangle(bul_x,bul_y,3,5);
					slope = (double) (bul_y-Oy)/(bul_x-Ox);
					buly = (double) bul_y;
					if (bul_x > Ox)  increment = -1;
					else increment = 1;
					shot=true;
					needToFire=false;

					}
				}

				shoot();

				boundingBox = O.getBounds2D();
				target_rect = new Rectangle(a,b,20,20);

				if (boundingBox.intersects(bullet))
				{
					Gameover=true;
					hit=true;

				}

				if (target_rect.intersects(boundingBox))
				{
					ballList.add(new Ball(this));
					score+=5;

							Rectangle frame = new Rectangle(0,0,1000,600);
							Rectangle target;
							boolean fit=true;

							do {
									a= (int)(Math.random()*853)+126;
									b= (int)(Math.random()*579)+1;

									target = new Rectangle(a,b,20,20);
									if (frame.contains(target))
										fit=true;
									else
									 	fit=false;

							} while (fit=false);
					// System.out.println(" HIT IS TRUE ");

				}
				//you need to make an object for the obstacles. they should have getX, getY, setX, setY, getSize methods.
				//they also are going to need boolean values for up/down or left/right and a boolean that identi
				Ellipse2D i= new Ellipse2D.Double((int)O.getX(),(int)O.getY(),(int)O.getWidth(),(int)O.getHeight());

				for(int x=0;x<ballList.size();x++)
				{

					if((Math.sqrt(Math.pow(O.getX()+10-(ballList.get(x).getX()+5),2)+Math.pow(O.getY()+10-(ballList.get(x).getY()+5),2)) <=15))
					{
						Gameover=true;
						hit=true;

						if (score>highscore)
						{
							highscore = score;
						}

					}
				}

			}
			repaint();

			try
			{
				t.sleep(2);
			}
			catch(InterruptedException e)
			{
			}
		}

	}


	public void keyTyped(KeyEvent ke)
	{
	}

	public void keyPressed(KeyEvent ke)
	{

		if (ke.getKeyCode()==37)
		left=true;
		if(ke.getKeyCode()==38)
		up=true;
		if (ke.getKeyCode()==39)
		right=true;
		if(ke.getKeyCode()==40)
		down=true;
	}

	public void keyReleased(KeyEvent ke)
	{


		if (ke.getKeyCode()==37)
		left=false;
		if(ke.getKeyCode()==38)
		up=false;
		if (ke.getKeyCode()==39)
		right=false;
		if(ke.getKeyCode()==40)
		down=false;
	}

	public void mouseEntered(MouseEvent me)
	{
	}

	public void mouseExited(MouseEvent me)
	{
	}

	public void mouseReleased(MouseEvent me)
	{
		colorPressed=false;
	}

	public void mousePressed(MouseEvent me)
	{
		colorPressed=true;
		Rectangle rect= new Rectangle(15,15,100,60);
		if(rect.contains(me.getX(),me.getY()))
		{
				setup();
				hit=false;

		}
	}

	public void mouseClicked(MouseEvent me)
	{
	}


	public static void main(String args[])
	{
		Final app=new Final();
	}
}










