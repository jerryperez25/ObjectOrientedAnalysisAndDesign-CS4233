/**
 * 
 */
package pig;

/**
 * @author jerryperez
 *
 */
public class DuplicateDieTester implements Die 
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
				return 5;
			case 3:
				return 5;
		}
		
		return 4; 
	}

}
