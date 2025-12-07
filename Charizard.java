
import java.util.Arrays;

public class Charizard extends PokemonInfo{
	
	public Charizard() {
		super("Charizard", "Fire, Flying", 360, Arrays.asList(new Attack("Flare Blitz", "Fire", 120), new Attack("Shadow Claw", "Ghost", 70), new Attack("Dragon Claw", "Dragon", 80), new Attack("Slash", "Normal", 70)));
		  
	}

}
