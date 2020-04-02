/*
定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
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
    
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        
        ListNode pre = new ListNode(-1);
        ListNode cur = head;
        pre.next = cur;
        
        while (cur != null) {
            ListNode next = cur.next;
            
            //change direction
            cur.next = pre;
            
            pre = cur;
            cur = next;
        }
        
        //delete dump node
        head.next = null;
        
        return pre; 
    }
    
}         

