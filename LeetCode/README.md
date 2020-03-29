# LeetCode - Solution

- Java
- C  / C++

```java
class Solution {
    
    public int maxDistance(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] move = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        Queue<Point> queue = new ArrayDeque<>();  //用于bfs的队列
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) {
                    queue.add(new Point(i, j));
                }
            }
        }
        if (queue.isEmpty() || queue.size() == row*col) { //全0或全1
            return -1;
        }
        Point point = queue.peek();
        while (!queue.isEmpty()) {
            point = queue.poll();
            for (int i = 0; i < move.length; i++) {
                int x = point.x + move[i][0];
                int y = point.y + move[i][1];
                if (x < 0 || x >= row || y < 0 || y >= col) { //越界
                    continue;
                } else if (grid[x][y] > 0) { //访问过
                    continue;
                }
                grid[x][y] = grid[point.x][point.y] + 1;
                queue.add(new Point(x, y));
            }
        }
        return grid[point.x][point.y] - 1;
    }
    
    /* 定义坐标 */
    private class Point {
        private int x;
        private int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
```




