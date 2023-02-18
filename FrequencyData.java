/**
 * @title Huffman File Compression
 * @subtitle Assignment: PS-3
 * @Author Nathan Giffard
 * @class Dartmouth CS 10, Winter 2023
 * @date February 10th, 2023
 * @description Defines a datatype to hold character and frequency values
 */
public class FrequencyData {
    Character ID; //character
    Integer freq; //frequency

    /**
     * Creates data object
     * @param a character to set
     * @param b frequency to set
     */
    public FrequencyData(Character a,Integer b){
        this.ID = a;
        this.freq = b;
    }

    /**
     * Sets ID
     * @param x character to set
     */
    public void setID(Character x) {
        this.ID =x;
    }

    /**
     * Sets frequency
     * @param x character to set
     */
    public void setFrequency(Integer x) {
        this.freq =x;
    }

    /**
     * Get ID
     */
    public Character getID() {return this.ID;}

    /**
     * Get frequency
     */
    public Integer getFrequency() {return this.freq;}

    @Override
    /**
     * Print object ID and frequency
     */
    public String toString() {return ID + ":" + "\t" + freq;}
}
