# LeetCode - Solution

- Java
- C  / C++


## 1162. As Far from Land as Possible

Given an N x N grid containing only values 0 and 1, where 0 represents water and 1 represents land, find a water cell such that its distance to the nearest land cell is maximized and return the distance.

The distance used in this problem is the Manhattan distance: the distance between two cells (x0, y0) and (x1, y1) is |x0 - x1| + |y0 - y1|.

If no land or water exists in the grid, return -1.


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
