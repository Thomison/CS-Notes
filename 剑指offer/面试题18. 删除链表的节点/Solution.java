/*
给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。

返回删除后的链表的头节点。
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
    
    public ListNode deleteNode(ListNode head, int val) {
        /* 在头节点前加上一个哑巴结点，以免删除头节点而导致的丢失 */
        ListNode dump = new ListNode(-1);
        dump.next = head;
        
        /* 两个用来遍历链表的指针 */
        ListNode cur = head;
        ListNode pre = dump;
        
        while (cur != null && cur.val != val) {
            cur = cur.next;
            pre = pre.next;
        }
        /* 删除结点 */
        if (cur != null) {
            pre.next = pre.next.next;
        }
        
        return dump.next;
    }
    
}
