package fr.ensimag.deca.tools;

/**
 * Super banner for our team
 */
public class SuperBanner {
    String wowTresBelleBanniere = "\033[1;94m" +
            " +------  GL49 Hall of fame  ------+\n" +
            " |      *  Teimur Abu Zaki  *      |\n" +
            " |      *   Adrien Bouchet  *      |\n" +
            " |      *     Troy Fau      *      |\n" +
            " |      *   Paul Marthelot  *      |\n" +
            " |      *    Hugo Robert    *      |\n" +
            " +---------------------------------+\n" +
            "\u001B[0m";

    @Override
    public String toString() {
        return wowTresBelleBanniere;
    }

}
