public class TypeDamageChecker {

    public static double getMultiplier(String attackType, String defenderType1, String defenderType2) {
        double multiplier = 1.0;

        multiplier *= getSingleMultiplier(attackType, defenderType1);

        if (defenderType2 != null && !defenderType2.isEmpty()) {
            multiplier *= getSingleMultiplier(attackType, defenderType2);
        }

        return multiplier;
    }

    private static double getSingleMultiplier(String attackType, String defenderType) {
        switch (attackType) {
            case "Water":
                switch (defenderType) {
                    case "Fire": return 2.0;
                    case "Water": return 0.5;
                    case "Grass": return 0.5;
                    case "Ground": return 2.0;
                    case "Rock": return 2.0;
                    case "Dragon": return 0.5;
                    default: return 1.0;
                }
            case "Ground":
                switch (defenderType) {
                    case "Fire": return 2.0;
                    case "Electric": return 2.0;
                    case "Grass": return 0.5;
                    case "Flying": return 0.0;
                    case "Steel": return 2.0;
                    default: return 1.0;
                }
            case "Ghost":
                switch (defenderType) {
                    case "Ghost": return 2.0;
                    case "Normal": return 0.0;
                    default: return 1.0;
                }
            case "Fire":
                switch (defenderType) {
                    case "Grass": return 2.0;
                    case "Fire": return 0.5;
                    case "Water": return 0.5;
                    case "Rock": return 0.5;
                    case "Dragon": return 0.5;
                    default: return 1.0;
                }
            case "Grass":
                switch (defenderType) {
                    case "Water": return 2.0;
                    case "Fire": return 0.5;
                    case "Grass": return 0.5;
                    case "Ground": return 2.0;
                    case "Rock": return 2.0;
                    default: return 1.0;
                }
            case "Fighting":
                switch (defenderType) {
                    case "Normal": return 2.0;
                    case "Rock": return 2.0;
                    case "Steel": return 2.0;
                    case "Ghost": return 0.0;
                    case "Psychic": return 0.5;
                    default: return 1.0;
                }
            case "Poison":
                switch (defenderType) {
                    case "Grass": return 2.0;
                    case "Poison": return 0.5;
                    case "Steel": return 0.0;
                    default: return 1.0;
                }
            case "Electric":
                switch (defenderType) {
                    case "Water": return 2.0;
                    case "Flying": return 2.0;
                    case "Electric": return 0.5;
                    case "Ground": return 0.0;
                    default: return 1.0;
                }
            case "Psychic":
                switch (defenderType) {
                    case "Fighting": return 2.0;
                    case "Poison": return 2.0;
                    case "Psychic": return 0.5;
                    default: return 1.0;
                }
            case "Rock":
                switch (defenderType) {
                    case "Fire": return 2.0;
                    case "Flying": return 2.0;
                    case "Fighting": return 0.5;
                    case "Ground": return 0.5;
                    case "Steel": return 0.5;
                    default: return 1.0;
                }
            case "Dragon":
                switch (defenderType) {
                    case "Dragon": return 2.0;
                    default: return 1.0;
                }
            case "Dark":
                switch (defenderType) {
                    case "Psychic": return 2.0;
                    case "Dark": return 0.5;
                    default: return 1.0;
                }
            case "Steel":
                switch (defenderType) {
                    case "Rock": return 2.0;
                    case "Ice": return 2.0;
                    case "Steel": return 0.5;
                    case "Fire": return 0.5;
                    default: return 1.0;
                }
            default:
                return 1.0;
        }
    }
}
