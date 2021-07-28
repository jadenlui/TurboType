/**
 * Name: Jaden Lui
 * Date: Tuesday, January 14, 2020
 * Project Name: Lui_Jaden_CPT
 * Description: A stat class to act as a super class for the three stats of the
 * game.
 */

package stats;

import java.text.DecimalFormat;
import javax.swing.JLabel;

abstract class Stat { // forces each subclass to have a printStat function

    private DecimalFormat df = new DecimalFormat("#.#"); 
    // prints to one decimal place

    abstract void printStat(JLabel printLabel, double stat); 

    // getter and setter
    public DecimalFormat getDf() {
        return df;
    }

    public void setDf(DecimalFormat df) {
        this.df = df;
    }

}
