/**
 * Name: Jaden Lui
 * Date: Tuesday, January 14, 2020
 * Project Name: Lui_Jaden_CPT
 * Description: An WPM class to easily calculate WPM and print it.
 */

package stats;

import javax.swing.JLabel;

public class WPM extends Stat {  // uses printStat method 

    private double wpm; // represents numerical value of WPM

    public WPM(int wordCountWPM, double elapsedTime) {
        wpm = (double) wordCountWPM / elapsedTime * 60; // words typed / time (in s) * 60
    }

    /**
     * Description: Prints WPM in numerical form to a label
     * pre-condition: printLabel is instantiated
     * post-condition: WPM is printed out to one decimal 
     *
     * @param printLabel - label that WPM is printed onto
     * @param stat - WPM numerical value
     */
    @Override // overrides Stat's printStat
    public void printStat(JLabel printLabel, double stat) {
        printLabel.setText("WPM: " + super.getDf().format(stat));
        // WPM is printed out to one decimal
    }

    // getter and setter
    public double getWpm() {
        return wpm;
    }

    public void setWpm(double wpm) {
        this.wpm = wpm;
    }

}
