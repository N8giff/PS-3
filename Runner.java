/**
 * @title Huffman File Compression
 * @subtitle Assignment: PS-3
 * @Author Nathan Giffard
 * @class Dartmouth CS 10, Winter 2023
 * @date February 10th, 2023
 * @description Runner for the HuffmanCompression Class. Creates 5 test cases
 * and outputs a compressed and decompressed .txt file for each
 */
public class Runner {
    static final String test1 = "PS3/inputs/test1.txt"; // single sentence
    static final String test2 = "PS3/inputs/test2.txt"; // single character over and over
    static final String test3 = "PS3/inputs/test3.txt"; // file is empty
    static final String test4 = "PS3/inputs/test4.txt"; // looking for edge cases
    static final String test5 = "PS3/inputs/USConstitution.txt";
    static final String test6 = "PS3/inputs/WarAndPeace.txt";

    public static void main(String[] args) throws Exception {
        HuffmanCompression a = new HuffmanCompression(test1);
        HuffmanCompression b = new HuffmanCompression(test2);
        HuffmanCompression c = new HuffmanCompression(test3);
        HuffmanCompression d = new HuffmanCompression(test4);
        HuffmanCompression e = new HuffmanCompression(test5);
        HuffmanCompression f = new HuffmanCompression(test6);
    }
}
