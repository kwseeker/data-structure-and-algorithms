package top.kwseeker.dsaa.tree.btree;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class BTreeTest {

    private BTree<Integer, String> mBTree;
    private Map<Integer, String> mMap;
    private BTree.BTreeIterator<Integer, String> mIter;

    @Before
    public void init() {
        mBTree = new BTree<>();
        mMap = new TreeMap<>();
        mIter = new BTree.BTreeIterator<>();
    }

    @Test
    public void testValidateTestCase0() {
        System.out.println("+ Running TestCase0 validation");
        addManualKeys();
        validateAll();
    }

    private void addManualKeys() {
        System.out.println("Adding manual keys to the tree ...");
        add(35, "hello");
        add(25, "world!");
        add(12, "hey");
        add(40, "dude");
        add(9, "extra9");
        add(4, "extra4");
        add(100, "extra100");
        add(45, "extra45");
        add(80, "hello80");
        add(81, "world!81");
        add(1, "hey1");
        add(402, "dude402");
        add(2, "extra2");
        add(36, "extra36");
        add(101, "extra101");
        add(43, "extra43");

        // Add more for testing
        add(75, "hello75");
        add(13, "world!13");
        add(27, "hey27");
        add(29, "dude29");
        add(309, "extra309");
        add(213, "extra213");
        add(103, "extra103");
        add(125, "extra125");

        // Add more for testing
        add(175, "hello175");
        add(53, "world!53");
        add(127, "hey127");
        add(49, "dude49");
        add(89, "extra89");
        add(17, "extra17");
        add(91, "extra91");
        add(99, "extra99");

        // Add more for testing
        add(65, "hello165");
        add(63, "world!63");
        add(10, "hey10");
        add(47, "dude47");
        add(69, "extra69");
        add(97, "extra97");
        add(95, "extra95");
        add(7, "extra7");

        // Add more for testing
        add(265, "hello1265");
        add(263, "world!263");
        add(110, "hey110");
        add(407, "dude407");
        add(169, "extra169");
        add(297, "extra297");
        add(295, "extra295");
        add(37, "extra37");

        // Add more for testing
        add(175, "hello175");
        add(53, "world!53");
        add(127, "hey127");
        add(239, "dude239");
        add(89, "extra89");
        add(17, "extra1717");
        add(91, "extra91");
        add(99, "extra99");

        // Add more for testing
        add(65, "hello65");
        add(63, "world!63");
        add(59, "hey59");
        add(149, "dude149");
        add(8, "extra8");
        add(117, "extra117");
        add(291, "extra291");
        add(249, "extra249");

        add(388, "extra388");
        add(317, "extra317");
        add(391, "extra391");
        add(349, "extra349");
    }

    private void add(Integer key, String value) {
        mMap.put(key, value);
        mBTree.insert(key, value);
    }

    private void validateAll() {
        System.out.println("Validate data ...");
        for (Map.Entry<Integer, String> entry : mMap.entrySet()) {
            String strVal = mBTree.search(entry.getKey());
            assertEquals(entry.getValue(), strVal);
        }

        System.out.println("Validate size ...");
        assertEquals(mMap.size(), mBTree.size());

        System.out.println("Validate the order of the keys ...");
        mIter.reset();
        mBTree.list(mIter);
        assertTrue(mIter.getStatus());
    }
}