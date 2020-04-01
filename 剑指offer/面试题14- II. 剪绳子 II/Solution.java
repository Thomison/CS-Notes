/*
给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 k[0],k[1]...k[m] 。请问 k[0]*k[1]*...*k[m] 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。

答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。

2 <= n <= 1000
*/


/* 贪婪算法 O(1) or O(n) */

class Solution {
    final int mod = 1000000007;
    
    public int cuttingRope(int n) {
        if (n < 2) {
            return 0;
        } else if (n == 2) {
            return 1;
        } else if (n == 3) {
            return 2;
        }
        
        long ret = 1;
        while (n > 4) { 
            ret *= 3;       //进行贪婪选择 - 当前最优
            ret %= mod;
            n -= 3;
        }
        ret *= n;
        
        return (int) (ret % mod);
        
    }
    
}
