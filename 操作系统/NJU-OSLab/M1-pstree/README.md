## M1: 打印进程树 (pstree)

把系统中的进程按照父亲-孩子的树状结构打印到终端。

- `-p, --show-pids`: 打印每个进程的进程号。
- `-n --numeric-sort`: 按照pid的数值从小到大顺序输出一个进程的直接孩子。
- `-V --version`: 打印版本信息。

在命令行用这些参数执行`pstree` (如pstree -V、pstree --show-pids) 参考它们的行为。这些参数可能任意组合。
