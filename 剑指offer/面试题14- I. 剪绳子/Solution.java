/*
给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 k[0],k[1]...k[m] 。

请问 k[0]*k[1]*...*k[m] 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
*/

/* 动态规划 O(n^2) */

class Solution {
    
    public int cuttingRope(int n) {
        if (n < 2) {
            return 0;
        } else if (n == 2) {
            return 1;
        } else if (n == 3) {
            return 2;
        }
        
        /* dp[i]为将长度为i的绳子分割的最大乘积 */
        int[] dp = new int[n + 1];
        
        /* 初始化 */
        dp[0] = 1;      
        dp[1] = 1;      
        dp[2] = 2;      
        dp[3] = 3;      
        
        for (int i = 4; i <= n; i++) {
            
            for (int j = 1; j < i; j++) {   //从子问题中得到最优解
                dp[i] = Math.max(dp[i], dp[j] * dp[i - j]);
            }
        }
        
        return dp[n];
    }
    
}
