package top.kwseeker.datastructure.list;

import org.junit.Test;

import java.util.Random;

public class SkipListTest {

    /**
     *   8                                507
     *   8                                507         655
     *   8                                507         655
     *   8                329         492 507     551 655
     * 2 8 97 103 115 294 329 365 392 492 507 509 551 655 656 665 686 746 929 980
     */
    @Test
    public void testSkipList() {
        SkipList skipList = new SkipList();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int randNum = random.nextInt(1000);
            skipList.add(randNum);
        }
        skipList.display();
    }
}
