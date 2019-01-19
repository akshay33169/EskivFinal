public class Ball
{
	//int variables x,y,height,width
	int x;
	int y;
	int BH;
	int BW;

	//boolean variables direction,whatKindOfBall
	boolean dir;
	boolean whatKindOfBall;

	public Ball(Final game)
	{
		//give it a random location
		x= (int)(Math.random()*863)+126;
		y= (int)(Math.random()*589)+1;

		// System.out.println("Inside NEW BALL CONSTRUCTOR x is " + x + " y is " + y);

		int random_boolean;
		boolean used;
		used = false;

		do {


		random_boolean = (int)(Math.random()*2)+1;

		if (random_boolean==1)
			whatKindOfBall=true;
		else
			whatKindOfBall=false;

		 random_boolean = (int)(Math.random()*2)+1;

		if (random_boolean==1)
					dir=true;
		else
			   dir=false;

// System.out.println("BALL LIST SIZE IS " + game.ballList.size());



		for(int k=0;k<game.ballList.size();k++)
		{

					//System.out.println("X value is " + game.ballList.get(k).getX() + " Y value is" + game.ballList.get(k).getY());
					if (game.ballList.get(k).getKind())
					{
						if (whatKindOfBall == true)
						{
						  if (game.ballList.get(k).getY() == y)
							used=true;
						}
					}
					else
					{
						if (whatKindOfBall == false)
						{
							 if (game.ballList.get(k).getX() == x)
									used=true;
						}
					}
		}


		} while (used == true);


	BH=10;
		BW=10;



		//whatKindOfBall=true;	//true means left/right ball and up/down ball
		//whatKindOfBall=false;

		//direction=true;	//true means right or down (depending on kind of ball)
		//dir=true;

	}

	//getx, gety,getwidth,getHeight

	public void move()
	{
		if(whatKindOfBall==true)
		{
			if(dir==true)
			{
				x++;
				if(x>976)
					dir=false;
			}
			else
			{
				x--;
				if(x<126)
					dir=true;

			}


		}
		else
		{
			if (dir==true)
			{
				y++;
				if(y>580)
					dir=false;
			}
			else
			{
				y--;
				if (y<1)
					dir=true;
			}
		}



	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public boolean getDir()
	{
		return dir;
    }

    public boolean getKind()
    {
		return whatKindOfBall;
	}

	public int getHeight()
	{
		return BH;
	}

	public int getWidth()
	{
		return BW;
	}



}