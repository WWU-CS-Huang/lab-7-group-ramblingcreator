/* Name: Ruby Leroux
 * Date: 6/5/25
 * Purpose: Tests Huffman.java
 */

package lab7;

import static org.junit.Assert.*;
import org.junit.FixMethodOrder;

import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class lab7Test {

    @Test
    public void test01countFrequencies() {
        String input = "a";
        Huffman h = new Huffman(input);
        HashMap<Character, Integer> frequencies = h.countFrequencies(input);
        frequencies.forEach((letter, amount) -> {
            System.out.println(letter + ": " + amount);
        });
    }

    @Test
    public void test02countFrequencies() {
        String input = "abbcccddddeeeeeffffffggggggghhhhhhhh";
        Huffman h = new Huffman(input);
        HashMap<Character, Integer> frequencies = h.countFrequencies(input);
        frequencies.forEach((letter, amount) -> {
            System.out.println(letter + ": " + amount);
        });
    }

    @Test
    public void test10treeee() {
        String input = "abbcccddddeeeeeffffffggggggghhhhhhhh";
        Huffman h = new Huffman(input);
        h.printTree();
    }

    @Test
    public void test11treeee() {
        String input = "abcdefgh";
        Huffman h = new Huffman(input);
        h.printTree();
    }

    @Test
    public void test12treeee() {
        String input = "abccddddeeeeeeee";
        Huffman h = new Huffman(input);
        h.printTree();
    }

    @Test
    public void test21decode() {
        String input = "beef feed fed calf";
        Huffman h = new Huffman(input);
        System.out.print("decoding '001010110111011001110100110': ");
        System.out.println(h.decode("001010110111011001110100110"));
        assertEquals(h.decode("001010110111011001110100110"), "feed clad");
    }

    @Test
    public void test31encode() {
        String input = "beef feed fed calf";
        Huffman h = new Huffman(input);
        System.out.print("encoding 'fed ball': ");
        System.out.println(h.encode("fed ball"));
        assertEquals(h.encode("fed ball"), "00101101110101010001110111");
    }


}
