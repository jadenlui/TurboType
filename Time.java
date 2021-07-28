/**
 * Name: Jaden Lui
 * Date: Tuesday, January 14, 2020
 * Project Name: Lui_Jaden_CPT
 * Description: A Time class to easily calculate time and print it.
 */

package stats;

import javax.swing.JLabel;

public class Time extends Stat { // uses printStat method

    private double time;

    public Time(long start, long finish) {
        time = (finish - start) / 1000000000.0; // elapsed (in ns) / 1 billion = time in s
    }

     /**
     * Description: Prints time in numerical form to a label
     * pre-condition: printLabel is instantiated
     * post-condition: time is printed out to one decimal 
     *
     * @param printLabel - label that time is printed onto
     * @param stat - time numerical value
     */
    @Override // overrides Stat's printStat
    public void printStat(JLabel printLabel, double stat) {
        printLabel.setText("TIME: " + super.getDf().format(stat) + " seconds");
        // time is printed out to one decimal with "seconds" after
    }
    
    // getter and setter
    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

}
