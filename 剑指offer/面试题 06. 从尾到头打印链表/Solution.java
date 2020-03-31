/*
输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
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
    
    /* 利用栈先进后出的特点 逆向存储链表节点的值 */
    public int[] reversePrint(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        ListNode tmp = head;
        int len = 0;
        while (tmp != null) {
            stack.push(tmp.val);
            tmp = tmp.next;
            len++;
        }
        int[] ret = new int[len];
        int i = 0;
        while (!stack.empty()) {
            ret[i++] = stack.pop();
        }
        return ret;
    }
    
    /* 利用栈帧结构，递归实现 */
    public int[] reversePrint(ListNode head) {
        int len = 0;
        ListNode tmp = head;
        while (tmp != null) {
            tmp = tmp.next;
            len++;
        }
        tmp = head;
        int[] ret = new int[len];
        solve(ret, tmp, 0, len);
        return ret;
    }
    private void solve(int[] nums, ListNode node, int i, int len) {
        if (node == null) {
            return;
        }else if (node != null) {
            solve(nums, node.next, i + 1, len);
        }
        nums[len - 1 - i] = node.val;
    }
}
