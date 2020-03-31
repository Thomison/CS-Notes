/*
问题：

用两个栈实现一个队列。

队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。
*/

/*
思路：

将队列分别存储在两个栈中，

第一个栈保存队列尾部用于添加元素，

第二个栈用于保存队列头部用于删除元素，

添加元素在栈1上操作，而删除元素在栈2上操作，

如果栈2为空则需要从栈1获取，也就是说两个栈中的数据共同组成了一个队列的数据
*/

class CQueue {
    private Stack<Integer> stack;
    private Stack<Integer> stack2;
    int size;

    public CQueue() {
        stack = new Stack<>();
        stack2 = new Stack<>();
        size = 0;
    }
    
    public void appendTail(int value) {
        stack.push(value);
        size++;
    }
    
    public int deleteHead() {
        if (size == 0) {
            return -1;
        } else {
            size--;
            if (!stack2.empty()) {
                return stack2.pop();
            } else {
                while (!stack.empty()) {
                    stack2.push(stack.pop());
                }
                return stack2.pop();
            }
        }
    }
}

//55ms

/**
 * Your CQueue object will be instantiated and called as such:
 * CQueue obj = new CQueue();
 * obj.appendTail(value);
 * int param_2 = obj.deleteHead();
 */
