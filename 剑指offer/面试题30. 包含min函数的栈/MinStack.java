/*
定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，调用 min、push 及 pop 的时间复杂度都是 O(1)。
*/

class MinStack {
    private Stack<Integer> dataStack; 
    private Stack<Integer> helpStack;             //辅助栈栈顶存储最小元素
    
    /** initialize your data structure here. */
    public MinStack() {
        dataStack = new Stack<>();
        helpStack = new Stack<>();
    }
    
    public void push(int x) {
        dataStack.push(x);
        if (helpStack.empty()) {
            helpStack.push(x);
        } else {
            helpStack.push(Math.min(x, helpStack.peek()));
        }
    }
    
    public void pop() {
        dataStack.pop();
        helpStack.pop();
    }
    
    public int top() {
        return dataStack.peek();
    }
    
    public int min() {
        return helpStack.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.min();
 */
