/*
输入一个链表，输出该链表中倒数第k个节点。

为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。

例如，一个链表有6个节点，从头节点开始，它们的值依次是1、2、3、4、5、6。这个链表的倒数第3个节点是值为4的节点。
*/
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    /* 递归 */
    ListNode ret;  //全局变量
    
    public ListNode getKthFromEnd(ListNode head, int k) {
        
        solve(head, k);
        
        return ret;
    }
    
    private int solve(ListNode node, int k) {
        if (node == null) 
            return 0;
        
        int cur = solve(node.next, k) + 1;
        if (cur == k) 
            ret = node;
        return cur;
    }
    
    /* 快慢指针 */
    public ListNode getKthFromEnd(ListNode head, int k) {
        if (head == null)
            return null;
        
        ListNode slow = head, fast = head;
        /* 让快指针提前走k步 */
        while (k != 0 && fast != null) {
            fast = fast.next;
            k--;
        }
        /* 链表长度小于k时 */
        if (k != 0 && fast == null) 
            return null;
        /* 让快指针和慢指针一起走 */
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        return slow;
    }
}
