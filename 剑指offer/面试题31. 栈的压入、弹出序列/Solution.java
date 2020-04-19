/*
输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。

例如，序列 {1,2,3,4,5} 是某栈的压栈序列，序列 {4,5,3,2,1} 是该压栈序列对应的一个弹出序列，但 {4,3,5,1,2} 就不可能是该压栈序列的弹出序列。
*/


class Solution {
    /* 根据入栈序列和出栈序列模拟入栈和出栈的过程 */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        int len1 = pushed.length, len2 = popped.length;
        if (len1 != len2) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        int i = 0, j = 0, len = len1;
        while (i < len && j < len) {
            stack.push(pushed[i]);
            i++;
            while (!stack.empty() && stack.peek() == popped[j]) {
                stack.pop();
                j++;
            } 
        }
        if (stack.empty() && i == len && j == len) {
            return true;
        } else {
            return false;
        }
    }
}
