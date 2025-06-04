package lab7;

import java.util.HashMap;
import heap.Heap;
import java.lang.StringBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Huffman {
    treeNode root;
    HashMap<Character, String> codeMap;

    public static void main(String[] args) {
        String filename = args[0];
        String input = "";
        try {
            File f = new File(filename);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                input += s.nextLine();
            }
        } catch (Exception e) {
            System.out.println("bad file");
        }

        Huffman h = new Huffman(input);
        String encoded = h.encode(input);
        String decoded = h.decode(h.encode(input));
        if (input.length() <= 100) {
            System.out.println("Input string: " + input);
            System.out.println("Encoded string: " + encoded);
            System.out.println("Decoded string: " + decoded);
        }

        System.out.println("Decoded equals input: " + input.equals(decoded));
        System.out.println("Compression Ratio: " + (encoded.length()/8.0/input.length()));
    }


    /* constructor: makes a tree and map for this object
     * based on the input string
     */
    public Huffman(String input) {
        HashMap<Character, Integer> frequencies = countFrequencies(input);
        /* frequencies.forEach((letter, amount) -> {
            System.out.println(letter+": "+amount);
        }); */
        /* treeNode huffmanTree = buildTree(frequencies); */
        root = buildTree(frequencies);
        getMap();
    }

    /* creates a hashmap of characters to frequencies for the input string */
    public HashMap<Character, Integer> countFrequencies(String input) {
        HashMap<Character, Integer> frequencies = new HashMap<Character, Integer>();
        for (int i = 0; i < input.length(); i++) {
            char cur = input.charAt(i);
            if (frequencies.get(cur) == null) {
                frequencies.put(cur, 1);
            } else {
                frequencies.put(cur, frequencies.get(cur) + 1);
            }
        }
        return frequencies;
    }

    /* Creates a huffman tree from a hashmap of characters to frequencies
     * pre: frequencies is not empty and has size > 0
     */
    public treeNode buildTree(HashMap<Character, Integer> frequencies) {
        Heap<treeNode, Integer> forest = new Heap<treeNode, Integer>();
        frequencies.forEach((letter, amount) -> {
            treeNode newNode = new treeNode(letter, amount);
            forest.add(newNode, amount);
        });

        while (forest.size() > 1) {
            treeNode l = forest.poll();
            treeNode r = forest.poll();
            treeNode newParent = new treeNode(l, r);
            forest.add(newParent, newParent.frequency);
        }
        root = forest.poll();
        return root;
    }

    /* builds the hashmap used for decoding */
    public void getMap() {
        codeMap = new HashMap<Character, String>();
        traverse(root, "", codeMap);
        /* codeMap.forEach((letter, code) -> {
            System.out.println(letter + ": " + code);
        }); */

    }

    /* performes a pre-order traversal of the tree, adding each leaf's code to the map */
    public static void traverse(treeNode cur, String currentKey, HashMap<Character, String> bitCodes) {
        if (cur.isLeaf) {
            bitCodes.put(cur.letter, currentKey);
            return;
        }
        traverse(cur.left, currentKey + '0', bitCodes);
        traverse(cur.right, currentKey + '1', bitCodes);
    }

    /* prints a representation of the tree rooted at n */
    public static void printTree(treeNode n) {
        printSubtree(n, 0);
    }

    /* wrapper for the print tree method, printing from the root of this tree*/
    public void printTree() {
        printTree(root);
    }

    /* encodes a given input string
     * pre: getMap() has already been called on the tree (??)
     */
    public String encode(String input) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            s.append(codeMap.get(input.charAt(i)));
        }
        return s.toString();
    }

    /* returns the decoded version of the input string
     * pre: input only contains ones and zeroes
     */
    public String decode(String input) {
        StringBuilder s = new StringBuilder();
        int i = 0;
        while (i < input.length()) {
            treeNode currentNode = root;
            while (!currentNode.isLeaf) {
                if (input.charAt(i) == '0') {
                    currentNode = currentNode.left;
                } else {
                    currentNode = currentNode.right;
                }
                i++;
            }
            s.append(currentNode.letter);
        }
        return s.toString();
    }

    /* Displays a subtree rooted at the given node */
    private static void printSubtree(treeNode n, int level) {
        if (n == null) {
            return;
        }
        printSubtree(n.right, level + 1);
        for (int i = 0; i < level; i++) {
            System.out.print("        ");
        }
        System.out.println(n);
        printSubtree(n.left, level + 1);
    }

    /* An inner class for the nodes used to build the huffman tree */
    public static class treeNode {

        public int frequency;
        public char letter;

        public treeNode left;
        public treeNode right;

        boolean isLeaf;

        /* Constructor for a leaf node */
        public treeNode(char letter, int frequency) {
            this.frequency = frequency;
            this.letter = letter;
            isLeaf = true;
        }

        /* Constructor for a non-leaf node */
        public treeNode(treeNode l, treeNode r) {
            this.frequency = l.frequency + r.frequency;
            /* this.letter = null; */
            this.left = l;
            this.right = r;
            isLeaf = false;
        }

        /* Returns a string representation of the node */
        public String toString() {
            if (!isLeaf) {
                return "(" + frequency + ")";
            } else {
                return "(" + letter + ", " + frequency + ")";
            }
        }
    }

}
