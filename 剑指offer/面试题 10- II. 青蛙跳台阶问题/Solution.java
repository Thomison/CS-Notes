/*
一只青蛙一次可以跳上 1 级台阶，也可以跳上 2 级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。

答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
*/
/* 其实就是Fibonacci数列的变种 那就优化一下空间吧 */
class Solution {
    final int mod = 1000000007;
    
    public int numWays(int n) {
        int a = 1, b = 1;
        
        for (int i = 2; i <= n; i++) {
            int tmp = (a + b) % mod;
            a = b;
            b = tmp;
        }
        
        return b;
    }
    
}
