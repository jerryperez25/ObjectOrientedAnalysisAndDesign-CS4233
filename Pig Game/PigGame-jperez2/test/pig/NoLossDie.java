package pig;

public class NoLossDie implements Die 
{
	int roller = 0;

	@Override
	public int roll() 
	{
		roller++;
		
		switch (roller)
		{
			case 1:
				return 2;
			case 2:
				return 3;
			case 3:
				return 2;
		}
		
		return 1; //1 is the lowest we can roll 
	}

}
