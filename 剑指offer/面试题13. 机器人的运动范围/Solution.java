/*
地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。

一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。

例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？
*/

class Solution {
    
    public int movingCount(int m, int n, int k) {
        boolean[][] visit = new boolean[m][n];
        
        return dfs(0, 0, m, n, k, visit);
        
    }
    
    /* 从起点开始搜索 返回搜索到的格子数 */
    private int dfs(int i, int j, int m, int n, int k, boolean[][] visit) {
        /* 当前格子超出边界 或 已经被访问过 */
        if (i < 0 || i >= m || j < 0 || j >= n || visit[i][j]) 
            return 0;
        
        /* 检查当前格子是否能够被访问 */
        int check = 0;
        for (int ti = i; ti > 0; ti /= 10) 
            check += ti % 10;
        for (int tj = j; tj > 0; tj /= 10)
            check += tj % 10;
        if (check > k) 
            return 0;
        
        /* 对当前格子进行访问 */
        visit[i][j] = true;
        int ret = 1;
        
        /* 继续访问它周围的格子 */
        ret += dfs(i + 1, j, m, n, k, visit);
        ret += dfs(i - 1, j, m, n, k, visit);
        ret += dfs(i, j - 1, m, n, k, visit);
        ret += dfs(i, j + 1, m, n, k, visit);
        return ret;
    }
    
}
