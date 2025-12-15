import java.util.*;

public class PokemonGameWithPictures {

    static String[] names = new String[2];

    public static PokemonInfo getPokemonObject(String name) {
        return switch (name) {
            case "Blastoise (Water)" -> new Blastoise();
            case "Charizard (Fire, Flying)" -> new Charizard();
            case "Machamp (Fighting)" -> new Machamp();
            case "Venusaur (Grass, Poison)" -> new Venusaur();
            case "Groudon (Ground)" -> new Groudon();
            case "Wailord (Water)" -> new Wailord();
            case "Metagross (Steel, Psychic)" -> new Metagross();
            case "Rayquaza (Dragon, Flying)" -> new Rayquaza();
            case "Cofagrigus (Ghost)" -> new Cofagrigus();
            case "Zekrom (Dark, Electric)" -> new Zekrom();
            default -> null;
        };
    }

    public static void startGame() {
    	
    	DisplayInstructions();

        List<String> originalPokemon = List.of(
            "Blastoise (Water)",
            "Charizard (Fire, Flying)",
            "Machamp (Fighting)",
            "Venusaur (Grass, Poison)",
            "Groudon (Ground)",
            "Wailord (Water)",
            "Metagross (Steel, Psychic)",
            "Rayquaza (Dragon, Flying)",
            "Cofagrigus (Ghost)",
            "Zekrom (Dark, Electric)"
        );

        Scanner scanner = new Scanner(System.in);

        List<PokemonInfo> player1Team = new ArrayList<>();
        List<PokemonInfo> player2Team = new ArrayList<>();

        System.out.println("Player 1, what is your name?");
        names[0] = scanner.nextLine().toUpperCase();

        System.out.println("Player 2, what is your name?");
        names[1] = scanner.nextLine().toUpperCase();

        for (int p = 0; p < 2; p++) {

            System.out.println("\n===== " + names[p] + ", CHOOSE YOUR TEAM =====");

            List<String> available = new ArrayList<>(originalPokemon);
            List<PokemonInfo> currentTeam = (p == 0 ? player1Team : player2Team);

            while (currentTeam.size() < 5) {

                System.out.println("\nChoose a Pokémon (" + (currentTeam.size() + 1) + "/5):");
                for (int i = 0; i < available.size(); i++) {
                    System.out.println((i + 1) + ". " + available.get(i));
                }

                System.out.print("Enter number: ");

                while (!scanner.hasNextInt()) {
                    scanner.next();
                    System.out.print("Enter number: ");
                }

                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice < 1 || choice > available.size()) {
                    System.out.println("Invalid choice!");
                    continue;
                }

                String selected = available.remove(choice - 1);
                PokemonInfo pokeObj = getPokemonObject(selected);

                if (pokeObj == null) continue;

                currentTeam.add(pokeObj);
                System.out.println("You chose: " + pokeObj.getName());
            }
        }

        System.out.println("\n===== " + names[0] + " FIRST POKEMON =====");
        player1Team.get(0).printInfo();

        System.out.println("\n===== " + names[1] + " FIRST POKEMON =====");
        player2Team.get(0).printInfo();

        System.out.println("\nBattle Beginning!");

        BattleImageDisplay.initializeWindow();
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
                if (!p1.getName().equals("Wailord"))
                    System.out.println(p2.getName() + " has fainted!");
                else
                    System.out.println(p2.getName() + " has been squashed to death.");

                p2Index++;
                if (p2Index >= p2Team.size()) {
                    System.out.println(names[1] + " has no more Pokémon!");
                    System.out.println(names[0] + " WINS!");
                    return;
                }

                p2 = p2Team.get(p2Index);
                System.out.println(names[1] + " sends out: " + p2.getName());
                p2.printInfo();
            }

            playerAttack(names[1], p2, p1, scanner);

            if (p1.getHp() <= 0) {
                if (!p2.getName().equals("Wailord"))
                    System.out.println(p1.getName() + " has fainted!");
                else
                    System.out.println(p1.getName() + " has been squashed to death.");

                p1Index++;
                if (p1Index >= p1Team.size()) {
                    System.out.println(names[0] + " has no more Pokémon!");
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

        BattleImageDisplay.updateImages(attacker.getName(), defender.getName());

        System.out.println("\n" + player + " — Choose an attack for " + attacker.getName() + ":");

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

        int finalDamage = (int) (chosen.getDamage() * multiplier);
        defender.takeDamage(finalDamage);

        System.out.println(attacker.getName() + " used " + chosen.getName() + "!");
        System.out.println("It dealt " + finalDamage + " damage! (x" + multiplier + ")");
        System.out.println(defender.getName() + " HP: " + defender.getHp());

        String defenderName = defender.getName();
        double originalHP = defender.getMaxHP(defenderName);
        int healthy = (int) ((defender.getHp() / originalHP) * 20);
        int unhealthy = 20 - healthy;
        for (int i = 0; i < healthy; i++) {
            System.out.print("█");
        }
        for (int i = 0; i < unhealthy; i++) {
            System.out.print("░");
        }
        System.out.print("\n");
    }
    
    public static void DisplayInstructions() {
		String nothing = null;
		Scanner Scannerdi = new Scanner(System.in);
		System.out.println("Welcome to my Pokemon Game.");
		System.out.println("Press any key to continue.");
		nothing = Scannerdi.nextLine();
		System.out.println("This is a two player game, so you can play with a friend or against yourself.");
		System.out.println("Press any key to continue.");
		nothing = Scannerdi.nextLine();
		System.out.println("Here are the rules of the game.");
		System.out.println("Press any key to continue.");
		nothing = Scannerdi.nextLine();
		System.out.println("Player one will create a team out of the 10 available Pokemon.");
		System.out.println("Player two will then pick a team out of the 10 available Pokemon");
		System.out.println("It is possible for the two players to have the same Pokemon");
		System.out.println("However, you can not have a team with more than two of one Pokemon");
		System.out.println("Press any key to continue.");
		nothing = Scannerdi.nextLine();
		System.out.println("As a general rule of thumb, here are the type weaknesses:");
		System.out.println("Fire does more against grass, water does more against fire, and so on");
		System.out.println("Press any key to continue.");
		nothing = Scannerdi.nextLine();
		System.out.println("I'm sure you can figure out the rest :)");
		System.out.println("Press any key to continue.");
		nothing = Scannerdi.nextLine();
		System.out.println("By the way. I'm a huge fan of Wailord!");
		System.out.println("Press any key to continue.");
		nothing = Scannerdi.nextLine();
		
	}
}
