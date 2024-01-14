//package top.kwseeker.dsaa.leecode;
//
//import java.util.LinkedList;
//import java.util.Stack;
//
//public class PalindromeList234 {
//
//    public static void main(String[] args) {
//        ListNode node1 = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(2);
//        ListNode node4 = new ListNode(1);
//        node1.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//
//        System.out.println(isPalindrome(node1));
//    }
//
//    static boolean isPalindrome(ListNode head) {
//        //1) 获取链表长度
//        int len;
//        ListNode tmp = head;
//        len = (tmp == null)? 0:1;
//        while(tmp.next != null) {
//            len++;
//            tmp = tmp.next;
//        }
//        int subLen = len / 2;
//        if(subLen == 0) {
//            return true;
//        }
//        //2) 获取后半段子链表头节点，前半段子链表逆序
//        ListNode tmp0 = head;
//        ListNode tmp1 = head.next;
//        ListNode tmp2 = head.next.next;
//        for (int i = 0; i < subLen; i++) {
//            if(i==0) {
//                tmp0.next = null;
//            }
//            tmp1.next = tmp0;
//            tmp0 = tmp1;
//            tmp1 = tmp2;
//            tmp2 = tmp2.next;
//        }
//        //3) 依次比较前半段子链表逆序和后半段子链表的元素是否相同
//        if((len & 1) == 1) {
//
//        }
//        ListNode lastSegmentHead =
//    }
//
//    public static class ListNode {
//         int val;
//         ListNode next;
//         ListNode(int x) { val = x; }
//    }
//
//    //static boolean isPalindrome(LinkedList<Integer> head) {
//    //    Stack<Integer> stack = new Stack<>();
//    //    for (Integer node : head) {
//    //        if(!stack.empty() && stack.peek().equals(node)) {
//    //            stack.pop();
//    //        } else {
//    //            stack.push(node);
//    //        }
//    //    }
//    //    return stack.empty();
//    //}
//}
