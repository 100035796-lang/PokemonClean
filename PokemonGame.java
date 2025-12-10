import java.util.*;

public class PokemonGame {
	static String[] names = new String[2];
    public static PokemonInfo getPokemonObject(String name) {
        return switch (name) {
            case "Blastoise (Water)" -> new Blastoise();
            case "Charizard (Fire)" -> new Charizard();
            case "Machamp (Fighting)" -> new Machamp();
            case "Venusaur (Grass)" -> new Venusaur();
            case "Groudon (Ground)" -> new Groudon();
            case "Wailord (Water)" -> new Wailord();
            case "Metagross (Steel)" -> new Metagross();
            case "Rayquaza (Dragon)" -> new Rayquaza();
            case "Cofagrigus (Ghost)" -> new Cofagrigus();
            case "Zekrom (Dark)" -> new Zekrom();
            default -> null;
        };
    }

    public static void startGame() {
        
        List<String> originalPokemon = List.of(
            "Blastoise (Water)",
            "Charizard (Fire)",
            "Machamp (Fighting)",
            "Venusaur (Grass)",
            "Groudon (Ground)",
            "Wailord (Water)",
            "Metagross (Steel)",
            "Rayquaza (Dragon)",
            "Cofagrigus (Ghost)",
            "Zekrom (Dark)"
        );

        Scanner scanner = new Scanner(System.in);

        List<PokemonInfo> player1Team = new ArrayList<>();
        List<PokemonInfo> player2Team = new ArrayList<>();
        System.out.println("Player 1, what is your name?");
        String p1name = scanner.nextLine();
        System.out.println("Player 2, what is your name?");
        String p2name = scanner.nextLine();
        names[0] = p1name.toUpperCase();
        names[1] = p2name.toUpperCase();
        
        for (int p = 0; p <= 1; p++) {

            System.out.println("\n===== " + names[p] + ", PLEASE CHOOSE YOUR TEAM =====");

            List<String> available = new ArrayList<>(originalPokemon);
            List<PokemonInfo> currentTeam = (p == 1 ? player1Team : player2Team);

            while (currentTeam.size() < 5) {
                System.out.println("\nChoose a Pokémon (" + (currentTeam.size() + 1) + "/5):");
                for (int i = 0; i < available.size(); i++) {
                    System.out.println((i + 1) + ". " + available.get(i));
                }

                System.out.print("Enter number: ");
                int choice = scanner.nextInt();

                if (choice < 1 || choice > available.size()) {
                    System.out.println("Invalid choice!");
                    continue;
                }

                String selected = available.remove(choice - 1);
                PokemonInfo pokeObj = getPokemonObject(selected);
                currentTeam.add(pokeObj);

                System.out.println("You chose: " + pokeObj.getName());
            }
        }

        System.out.println("\n===== " + names[0] + " FIRST POKEMON STATS =====");
        PokemonInfo p1Current = player1Team.get(0);
        p1Current.printInfo();

        System.out.println("\n===== " + names[1] + " FIRST POKEMON STATS =====");
        PokemonInfo p2Current = player2Team.get(0);
        p2Current.printInfo();

        System.out.println("\nThe battle will now begin!");
        battleLoop(player1Team, player2Team, scanner);
        
    }


    public static void battleLoop(List<PokemonInfo> p1Team, List<PokemonInfo> p2Team, Scanner scanner) {

        int p1Index = 0;
        int p2Index = 0;

        PokemonInfo p1 = p1Team.get(p1Index);
        PokemonInfo p2 = p2Team.get(p2Index);

        while (true) {

            playerAttack(names[0], p1, p2, scanner);

            if (p2.getHp() <= 0) {
                System.out.println(p2.getName() + " has fainted!");

                p2Index++;
                if (p2Index >= p2Team.size()) {
                    System.out.println(names[1] + " has no Pokémon left!");
                    System.out.println(names[0] + " WINS!");
                    return;
                }

                p2 = p2Team.get(p2Index);
                System.out.println(names[1] + " sends out: " + p2.getName());
                p2.printInfo();
            }

            playerAttack(names[1], p2, p1, scanner);

            if (p1.getHp() <= 0) {
            	if (!p2.getName().equals("Wailord")) {
            		 System.out.println(p1.getName() + " has fainted!");
            	} else {
            		System.out.println(p1.getName() + " has been squashed to death. The pokeball has broken and " + p1.getName() + " has literally died.");
            	}
               
                p1Index++;
                if (p1Index >= p1Team.size()) {
                    System.out.println(names[0] + " has no Pokémon left!");
                    System.out.println(names[1] + " WINS!");
                    return;
                }

                p1 = p1Team.get(p1Index);
                System.out.println(names[0] + " sends out: " + p1.getName());
                p1.printInfo();
            }
        }
    }

    public static void playerAttack(String player, PokemonInfo attacker, PokemonInfo defender, Scanner scanner) {
        
    	System.out.println("\n" + player + " — Choose an attack for your " + attacker.getName() + ":");

        List<Attack> moves = attacker.getAttacks();
        for (int i = 0; i < moves.size(); i++) {
            Attack a = moves.get(i);
            System.out.println((i + 1) + ". " + a.getName() + " (" + a.getType() + ")");
        }

        int choice = -1;
        while (true) {
            System.out.print("Enter a number between 1 and " + moves.size() + ": ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }

            choice = scanner.nextInt();

            if (choice >= 1 && choice <= moves.size()) {
                break;
            }

            System.out.println("Choice out of range. Try again.");
        }
        
        Attack chosen = moves.get(choice - 1);


        String defenderType1 = defender.getType();
        String defenderType2 = null; 

        if (defender.getType().contains(",")) {
            String[] types = defender.getType().split(", ");
            defenderType1 = types[0];
            defenderType2 = types[1];
        }

        double multiplier = TypeDamageChecker.getMultiplier(chosen.getType(), defenderType1, defenderType2);

        int finalDamage = (int)(chosen.getDamage() * multiplier);
        defender.takeDamage(finalDamage);

        System.out.println(attacker.getName() + " used " + chosen.getName() + "!");
        System.out.println("It dealt " + finalDamage + " damage! (x" + multiplier + ")");
        System.out.println(defender.getName() + " HP: " + defender.getHp());
        String defendername = defender.getName();
        double originalHP = (defender.getNameHP(defendername));
        int healthy = (int) ((defender.getHp()/originalHP)*20);
        int unhealthy = 20 - healthy;
        for (int i = 0; i < healthy; i++) {
        	System.out.print("█");
        }
        for (int i = 0; i < unhealthy; i++) {
        	System.out.print("░");
        }
        System.out.print("\n");        

    }
    
}
