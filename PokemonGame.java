import java.util.*;

public class PokemonGame {

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


        for (int p = 1; p <= 2; p++) {

            System.out.println("\n===== PLAYER " + p + " CHOOSE YOUR TEAM =====");

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

        System.out.println("\n===== PLAYER 1 FIRST POKEMON STATS =====");
        PokemonInfo p1Current = player1Team.get(0);
        p1Current.printInfo();

        System.out.println("\n===== PLAYER 2 FIRST POKEMON STATS =====");
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

            playerAttack("Player 1", p1, p2, scanner);

            if (p2.getHp() <= 0) {
                System.out.println(p2.getName() + " has fainted!");

                p2Index++;
                if (p2Index >= p2Team.size()) {
                    System.out.println("Player 2 has no Pokémon left!");
                    System.out.println("PLAYER 1 WINS!");
                    return;
                }

                p2 = p2Team.get(p2Index);
                System.out.println("Player 2 sends out: " + p2.getName());
                p2.printInfo();
            }

            playerAttack("Player 2", p2, p1, scanner);

            if (p1.getHp() <= 0) {
                System.out.println(p1.getName() + " has fainted!");

                p1Index++;
                if (p1Index >= p1Team.size()) {
                    System.out.println("Player 1 has no Pokémon left!");
                    System.out.println("PLAYER 2 WINS!");
                    return;
                }

                p1 = p1Team.get(p1Index);
                System.out.println("Player 1 sends out: " + p1.getName());
                p1.printInfo();
            }
        }
    }

    public static void playerAttack(String player, PokemonInfo attacker, PokemonInfo defender, Scanner scanner) {
        System.out.println("\n" + player + " — Choose an attack for " + attacker.getName() + ":");

        List<Attack> moves = attacker.getAttacks();
        for (int i = 0; i < moves.size(); i++) {
            Attack a = moves.get(i);
            System.out.println((i + 1) + ". " + a.getName() + " (" + a.getType() + ")");
        }

        int choice = scanner.nextInt();
        Attack chosen = moves.get(choice - 1);

        System.out.println(attacker.getName() + " used " + chosen.getName() + "!");

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

    }
    
    
}
