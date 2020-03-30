class AsFarFromLandAsPossible {
/*
你现在手里有一份大小为 N x N 的『地图』（网格） grid，上面的每个『区域』（单元格）都用 0 和 1 标记好了。
其中 0 代表海洋，1 代表陆地，你知道距离陆地区域最远的海洋区域是是哪一个吗？请返回该海洋区域到离它最近的陆地区域的距离。

我们这里说的距离是『曼哈顿距离』（ Manhattan Distance）：(x0, y0) 和 (x1, y1) 这两个区域之间的距离是 |x0 - x1| + |y0 - y1| 。

如果我们的地图上只有陆地或者海洋，请返回 -1。
*/
    public int maxDistance(int[][] grid) {        //bfs找到离所有岛屿最远的海洋区域，求这个海洋区域距离所有岛屿中最短的距离
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
