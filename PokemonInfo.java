import java.util.List;

public class PokemonInfo {
    private String name;
    private String type;
    private int hp;
    private List<Attack> attacks;

    public PokemonInfo(String name, String type, int hp, List<Attack> attacks) {
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.attacks = attacks;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getHp() { return hp; }
    public List<Attack> getAttacks() { return attacks; }

    public void takeDamage(int amount) {
        hp -= amount;
        if (hp < 0) hp = 0;
    }

    public void printInfo() {
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);
        System.out.println("HP: " + hp);
        System.out.println("Attacks:");
        for (Attack a : attacks) {
            System.out.println(" - " + a.getName()
                    + " (" + a.getType() + ")");
        }
    }

	public double getNameHP(String defendername) {
		switch (defendername) {
		case "Rayquaza":
			return 414;
		case "Venusaur":
			return 364;
		case "Wailord":
			return 544;
		case "Zekrom":
			return 404;
		case "Blastoise":
			return 362;
		case "Charizard":
			return 360;
		case "Cofagrigus":
			return 320;
		case "Groudon":
			return 404;
		case "Machamp":
			return 384;
		case "Metagross":
			return 364;	
			default:
				return 0;
		}
	}
}
