import java.util.*;
import java.io.*;

/**
 * @title Huffman File Compression
 * @subtitle Assignment: PS-3
 * @Author Nathan Giffard
 * @class Dartmouth CS 10, Winter 2023
 * @date February 10th, 2023
 * @description Uses Binary Search trees to compress and decompress files
 */
public class HuffmanCompression {
    public Map<Character, Integer> freqMap = new HashMap<>(); //
    public TreeComparator myComparison = new TreeComparator(); //Tree Comparator
    public PriorityQueue<BinaryTree<FrequencyData>> myTree = new PriorityQueue<>(myComparison); //priority queue of characters and frequencies
    public Map<Character, String> bitMap = new HashMap<>(); //map of characters with their codes


    /**
     * Constructor loads file, builds a Huffman Tree and compresses and decompresses the file
     * @param pathName  file to compress/decompress
     */
    public HuffmanCompression(String pathName) throws Exception {
        String output = compressedName(pathName); // _compressed file name
        String output2 = decompressedName(output); // _decompressed file name
        loadFile(pathName); //load file and map characters
        buildTree(); //build tree and map each character code
        compress(pathName,output,bitMap); //write the compressed file
        decompress(output2,output);
        System.out.println("\n" + "PROCESSING FINISHED");
    }

    /**
     * Method to add '_compressed' to the end of the file name
     * @param pathName  file path to modify
     */
    private String compressedName(String pathName){
        int period = pathName.lastIndexOf('.');
        String newName;
        newName = pathName.substring(0,period) + "_compressed" + pathName.substring(period);
        return newName;
    }

    /**
     * Method to add '_decompressed' to the end of the file name
     * @param pathName  file path to modify
     */
    private String decompressedName(String pathName){
        int period = pathName.lastIndexOf('.');
        int underscore = pathName.lastIndexOf('_');
        String newName;
        if(underscore != -1) {
            newName = pathName.substring(0, underscore) + "_decompressed" + pathName.substring(period);
        }
        else{
            newName = pathName.substring(0, period) + "_decompressed" + pathName.substring(period);
        }
        return newName;

    }

    /**
     * Loads file and creates a frequency map of the characters
     * @param fileName  file to compress
     */
    public void loadFile(String fileName) throws Exception {
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader(fileName));
            int current; //current character
            //While the current character code is not -1
            //cast current as a char and add it to the freqMap
            while ((current = input.read()) != -1) {
                char ch = (char) current;
                mapCharacter(ch);
            }
            input.close(); //close the BufferedReader
        }
        catch (FileNotFoundException e) {
            System.out.println("\n" + "FILE DOES NOT EXIST: " + fileName);
        }
        System.out.println("\n" + "FILE PROCESSING: " + fileName);
        //System.out.println(freqMap);
    }

    /**
     * Helper method that adds a character to the frequency map
     * @param x  character to add to the HashMap
     */
    public void mapCharacter(char x){
        //If the freq map contains the key
        //increase the count by 1
        if(freqMap.containsKey(x)){
            int value = freqMap.get(x);
            freqMap.put(x,value+1);
        }
        //If the freq map doesn't contain the key
        //add the character to the map with a count of 1
        else{
            freqMap.put(x,1);
        }
    }

    /**
     * Takes the characters and adds them to a priority queue
     */
    public void buildTree(){
        //For each key
        //get the data of type FrequencyData
        //create a BST using the current data
        //add the tree to the queue
        for(Character key : freqMap.keySet()){
            FrequencyData currData = new FrequencyData(key,freqMap.get(key));
            BinaryTree<FrequencyData> tree = new BinaryTree<>(currData);
            myTree.add(tree);
        }
        //Call tree helper method to create one Huffman tree
        treeHelper();
    }

    /**
     * Uses queue to build a tree with the datatype FrequencyData
     */
    public void treeHelper() {
        while (myTree.size() > 1) {
            //Extract the two lowest freq T1 and T2 from queue
            BinaryTree<FrequencyData> T1 = myTree.remove();
            BinaryTree<FrequencyData> T2 = myTree.remove();
            //Create a new tree T by creating a new root node r, attaching T1 as r's left subtree, and attaching T2 as r's right subtree. (Which of T1 and T2 is the left or right subtree does not matter.)
            //Assign to the new tree T a frequency that equals the sum of the frequencies of T1 and T2.
            int sum = T1.getData().getFrequency() + T2.getData().getFrequency();
            FrequencyData newFreq = new FrequencyData(null,sum);
            BinaryTree<FrequencyData> T = new BinaryTree<>(newFreq,T1,T2);
            //Insert the new tree T into the priority queue (which will base its priority on the frequency value it holds).
            myTree.add(T);
        }
        //System.out.println(myTree.toString()); // Print Huffman tree using BST toString() method
        getCode(myTree.peek());
    }

    /**
     * Traverse the tree using helper and return code words for each character
     * @param tree  binary search tree with character/frequency values
     */
    public void getCode(BinaryTree<FrequencyData> tree) {
        //If tree is null return
        if (tree == null) {
            return;
        }
        //If tree is size 1, put character with value 0
        if (tree.size() == 1) {
            bitMap.put(tree.getData().getID(), "0");
        }
        //Else recurse over tree to build code word
        else {
           codeHelper(bitMap, tree, "");
        }
    }

    /**
     * Helper method that recursively searches tree and creates code words for characters
     * @param bitMap    hash of characters and code words
     * @param tree      tree we're searching
     * @param s         code word we're building
     */
    public void codeHelper(Map<Character,String> bitMap, BinaryTree<FrequencyData> tree, String s){
        //If the node is a leaf, put character and string
        if(tree.isLeaf()){
            bitMap.put(tree.getData().getID(),s);
            //System.out.println(s = print(s));
        }
        //If node has left, add 0 to string and recurse
        if(tree.hasLeft()){
            codeHelper(bitMap,tree.getLeft(),s+"0");
        }
        //If node has right, add 1 to string and recurse
        if(tree.hasRight()){
            codeHelper(bitMap,tree.getRight(),s+"1");
        }
        //System.out.println(print(s));
    }

    /**
     * Prints binary string for the character
     * @param s     code word we want to print
     */
    public String print(String s){
        if(s.length() > 0){
            s = s.substring(0,s.length()-1);
        }
        return s;
    }

    /**
     * Write a compressed file using Huffman tree
     * @param pathName  file name
     * @param compressedFileName    file name with '_compressed'
     * @param codes bitMap with code words
     */
    public void compress(String pathName, String compressedFileName, Map<Character,String> codes) throws Exception{
       try {
           BufferedReader input = new BufferedReader(new FileReader(pathName)); //Create a reader w/ path name
           BufferedBitWriter output = new BufferedBitWriter(compressedFileName); //Create a writer w/ compressed path name
           int x;
           while ((x = input.read()) != -1) {
               char ch = (char) x;
               String bit = codes.get(ch); //get code word
               for (char c : bit.toCharArray()) // Loop over string
                   if (c == '1') {
                       output.writeBit(true); //if 1 write true
                   } else if (c == '0') {
                       output.writeBit(false); //if 0 write false
                   }
           }
           //close input and output files
           input.close();
           output.close();
       }
       catch (Exception e){
           System.out.println("Error w/ compression file: " + compressedFileName);
       }
    }

    /**
     * Write a decompressed file using Huffman tree
     * @param decompressedName  file name with '_decompressed'
     * @param compressedName    file name with '_compressed'
     */
    public void decompress(String decompressedName, String compressedName) throws Exception{
        try {
            BufferedBitReader bits = new BufferedBitReader(compressedName);//Create new BitReader w/ compressed file
            BufferedWriter output = new BufferedWriter(new FileWriter(decompressedName)); //Create a writer w/ decompressed name
            BinaryTree<FrequencyData> tree = myTree.peek();//Get tree
            while(bits.hasNext()){
                boolean bit = bits.readBit(); //read the bit
                //if bit is true search the right side
                if(bit && tree != null){
                    tree = tree.getRight();
                    //If the node is a leaf, write the bit to the output file
                    if(tree.isLeaf()){
                        char ID = tree.getData().getID();
                        output.write(ID);
                        tree= myTree.peek(); //set tree to next value in queue
                    }
                }
                //if the bit is false search the left side
                if(!bit && tree != null && myTree.peek() != null){
                    //If peek is a leaf, write the bit to the output file
                    if(myTree.peek().isLeaf()){
                        char ID = tree.getData().getID();
                        output.write(ID);
                    }
                    //if the node to left is a leaf then write to the output and set tree to next value
                    if(tree.getLeft().isLeaf()){
                        tree = tree.getLeft();
                        char ID = tree.getData().getID();
                        output.write(ID);
                        tree = myTree.peek(); //set tree to next value in queue
                    }
                    //else go left
                    else{
                        tree = tree.getLeft();
                    }
                }
            }
            output.close();
            bits.close();
        }
        catch (Exception e){
            System.out.println("Error w/ decompression file: " + decompressedName);
        }
    }
}
