/**
 * Name: Jaden Lui
 * Date: Tuesday, January 14, 2020
 * Project Name: Lui_Jaden_CPT
 * Description: An Accuracy class to easily calculate accuracy and print it.
 */
package stats;

import javax.swing.JLabel;

public class Accuracy extends Stat { // uses printStat method 

    private double accuracy; // represents numerical value of accuracy

    public Accuracy(int keys, int wrongKey) {
        accuracy = 100 - ((double) wrongKey / keys * 100.0); // 100 - (errors / keystrokes * 100)
    }

    /**
     * Description: Prints accuracy in numerical form to a label
     * pre-condition: printLabel is instantiated
     * post-condition: accuracy is printed out to one decimal with a 
     * percentage sign after
     *
     * @param printLabel - label that accuracy is printed onto
     * @param stat - accuracy numerical value
     */
    @Override // overrides Stat's printStat
    public void printStat(JLabel printLabel, double stat) {
        printLabel.setText("ACCURACY: " + super.getDf().format(stat) + "%"); 
        //prints accuracy to one decimal place and a percentage sign after
    }

    // getter and setter
    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

}
