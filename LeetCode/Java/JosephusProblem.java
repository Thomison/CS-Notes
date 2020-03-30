class JosephusProblem {

    //arraylist 模拟  O(n^2) 1482ms
    public int method1(int n, int m) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        int i = 0;
        while (n > 1) {
            i = (i + m - 1) % n;
            list.remove(i);
            n--;
        }
        return list.get(0);
    }

    //数学 公式推导 O(n) 8ms
    public int method2(int n, int m) {
        int ret = 0;
        for (int i = 2; i <= n; i++) {
            ret = (ret + m) % i;
        }
        return ret;
    }

}
```
