import java.util.Comparator;

/**
 * @title Huffman File Compression
 * @subtitle Assignment: PS-3
 * @Author Nathan Giffard
 * @class Dartmouth CS 10, Winter 2023
 * @date February 10th, 2023
 * @description Implements Comparator to compare the frequency values of two BST
 */
public class TreeComparator implements Comparator<BinaryTree<FrequencyData>>{

    /**
     * Sets ID
     * @param t1 first BST using frequency datatype
     * @param t2 second BST using frequency datatype
     */
    @Override
    public int compare(BinaryTree<FrequencyData> t1, BinaryTree<FrequencyData> t2) {
        //If the frequency of t1 is less than t2
        // return -1
        if(t1.getData().getFrequency() < t2.getData().getFrequency()){
            return -1;
        }
        //If the frequency of t1 is greater than t2
        // return -1
        else if(t1.getData().getFrequency() > t2.getData().getFrequency()){
            return 1;
        }
        //Else return 0
        return 0;
    }
}
