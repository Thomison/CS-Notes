/*
输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。
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
    
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }
        
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
        
    }
    
    /* 迭代 */
    
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        /* 哑结点和指针 */
        ListNode dump = new ListNode(-1), pre = dump;
        
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                pre.next = l1;
                
                l1 = l1.next;
            } else {
                pre.next = l2;
                
                l2 = l2.next;
            }
            pre = pre.next;
        }
        
        if (l1 != null) 
            pre.next = l1;
        if (l2 != null)
            pre.next = l2;
        
        return dump.next;
    }
}




