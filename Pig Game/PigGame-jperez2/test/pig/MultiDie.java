/**
 * 
 */
package pig;

/**
 * @author jerryperez
 *
 */
public class MultiDie implements Die 
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
				return 1;
		}
		
		return 1; //1 is the lowest we can roll
	}

}
