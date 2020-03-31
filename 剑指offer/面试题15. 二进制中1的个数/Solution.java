/*
请实现一个函数，输入一个整数，输出该数二进制表示中 1 的个数。
*/
public class Solution {
    
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int ret = 0;
        int mask = 1;
        
        for (int i = 0; i < 32; i++) {
            if ((n & mask) == 1) 
                ret++;
            n >>= 1;
        }
        
        return ret;
    }
    
}
