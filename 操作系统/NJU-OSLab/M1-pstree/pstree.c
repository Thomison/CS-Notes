/*
 * root-+-child 1-+-grandchild 1
 *      |         |
 *      |         +-grandchild 2 
 *      |         |
 *      |         `-grandchild m
 *      |
 *      +-child 2 
 *      |
 *      ...
 *      |
 *      `-child n 
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h> 
#include <unistd.h>
#include <assert.h>
#include <ctype.h> 
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/sysmacros.h>

#define BUF_SIZE 1024

/* 定义进程的数据结构 */
struct process 
{
    pid_t pid;                      /* 进程id */
    pid_t ppid;                     /* 父进程id */
    char name[32];                  /* 进程名 - 最长16 bytes - 考虑到可能需要添加pid */
    struct process *parent;         /* 指向父进程的指针 */ 
    struct process *child;          /* 指向第一个子进程的指针 */
    struct process *next;           /* 指向下一个兄弟进程的指针 */
};

struct process root = {1, 0, "systemd", NULL, NULL, NULL};   /* 声明并初始化进程树的根节点进程信息 */

/* 定义进程选项的数据结构 */
struct option
{
    char *name;                   /* 选项缩写名 -p,-n,-V */ 
    char *full_name;              /* 选项全称名 --show-pids,--numeric-sort, --version */ 
    int *target;                  /* 标志位 */ 
};

static int OPT_SHOWPID = 0;       /* 是否展示进程id */
static int OPT_NUMERIC = 0;       /* 是否将进程按照id排序 */
static int OPT_VERSION = 0;       /* 是否展示版本信息 */

/* 初始化进程选项的数组 */
const struct option options[] = {
    {"-p", "--show-pids", &OPT_SHOWPID},
    {"-n", "--numeric-sort", &OPT_NUMERIC},
    {"-V", "--version", &OPT_VERSION}
};

const int NMR_OPT = (int) sizeof(options) / sizeof(struct option);    /* 选项的数量 */

/* 函数原型 */
int parseOption(int, char *[]);                                 /* 解析命令行参数 */
void buildPSTree();                                             /* 构造进程树 */
struct process *readProcessStat(char *, struct process *);      /* 读取进程的状态信息 构造进程 */ 
struct process *findProcess(pid_t pid, struct process *);       /* 在进程树中寻找指定进程 */
void addProcess(struct process *);                              /* 在进程树中添加新构造的进程 */
void printPSTree();                                             /* 打印进程树 */
void printProcess(struct process *);                            /* 打印进程 */
void printParentProcess(struct process *);                      /* 打印进程的前缀字符串 */
void appendProcessId(struct process *);                         /* 添加进程号 */

/*
 * 程序入口
 */
int 
main(int argc, char *argv[]) 
{
    int errIdx;
    if ( (errIdx = parseOption(argc, argv)) != 0) {
        printf("Invalid argument: %s\n", argv[errIdx]);
        printf("Please input arguments from: [-p, --show-pids], [-n, --numeric-sort], [-V, --version]\n");
        exit(EXIT_FAILURE);
    } else {
        if (OPT_VERSION) {
            fprintf(stdout, "pstree v0.0.0 of NJU OSLab\n"
                    "By thomison (Yiheng Tang) [2020.4.5]\n");
            exit(EXIT_SUCCESS);
        } else {
            buildPSTree();
            printPSTree();
            exit(EXIT_SUCCESS);
        }
    }
   
}

/* 
 * 解析运行程序的命令行参数 
 *
 * 匹配成功返回0  匹配失败返回失匹下标
 */ 
int
parseOption(int argc, char *argv[])
{
    int isMatch = 0;
    for (int i = 1; i < argc; i++) {
        assert(argv[i]);
        
        for (int j = 0; j < NMR_OPT; j++) { 
            /* 匹配选项成功 */ 
            if (!strcmp(argv[i], options[j].name) || !strcmp(argv[i], options[j].full_name)) {
                *(options[j].target) = 1;      
                isMatch = 1;
            }  
        }
        if (!isMatch) return i;   /* 若该参数未匹配任何选项 则返回出错参数的下标 */
    }
    
    assert(!argv[argc]);
    return 0;
}

/*
 * 构造进程树的数据结构
 */
void
buildPSTree()
{
     /* 打开目录流 /proc */
     DIR *dr =  opendir("/proc");
     if (!dr) {
         perror("open dir /proc error\n");
         exit(EXIT_FAILURE);
     }
     /* 遍历目录流中的文件选项 */
     struct dirent *dp;
     while ( (dp = readdir(dr))) {
         if (!isdigit(*(dp->d_name)))
             continue;
         /* 进程 */
         struct process *parent = readProcessStat(dp->d_name, NULL);
         if (!parent) 
             continue;
         /* 进程内的线程 */
         char taskDirName[64];
         sprintf(taskDirName, "/proc/%.16s/task", dp->d_name);
         DIR *taskDr = opendir(taskDirName);
         if (!taskDr) {
             printf("open dir %s error\n", taskDirName);
             continue;
         }
         struct dirent *childp;
         while ( (childp = readdir(taskDr)) ) {
             if (!isdigit(*(childp->d_name)))
                 continue;
             readProcessStat(childp->d_name, parent);
         }
         closedir(taskDr);
     }
     closedir(dr);
}

/* 
 * 查询stat文件的进程信息 构造进程
 */ 
struct process *
readProcessStat(char *pidStr, struct process *parent)
{
    char statFileName[64];  //对应进程的stat文件名

    if (!parent) {          //进程 
        sprintf(statFileName, "/proc/%.16s/stat", pidStr);
    } else {                //线程
        sprintf(statFileName, "/proc/%d/task/%.16s/stat", parent->pid, pidStr);
    }

    FILE *statfp = fopen(statFileName, "r");
    if (!statfp) return NULL;
    struct process *proc = (struct process *)malloc(sizeof(struct process));
    fscanf(statfp, "%d (%16[^)]) %*c %d", &proc->pid, proc->name, &proc->ppid);
    proc->parent = proc->child = proc->next = NULL;
    
    /* 对于进程内的线程 特殊处理:父进程为当前进程，这些线程作为当前进程的展开形式 */
    if (parent) {
        proc->ppid = parent->pid;
        sprintf(proc->name, "{%.16s}", parent->name);
    }
    
    addProcess(proc);

    fclose(statfp);
    return proc;
}

/*
 * 通过进程号和出发进程 来搜索目标进程
 */ 
struct process *
findProcess(pid_t pid, struct process *cur)
{
    /* 未指定出发结点的话 则从根结点开始查找 */
    if (!cur) cur = &root;
    
    if (cur->pid == pid) return cur;

    struct process *ret = NULL;

    if (cur->child) {
        ret = findProcess(pid, cur->child);
        if (ret) return ret;
    }
    if (cur->next) {
        ret = findProcess(pid, cur->next);
        if (ret) return ret;
    }
    return NULL;
}


/*
 * 将构造好的进程添加到进程树中
 */ 
void 
addProcess(struct process *proc)
{
    if(findProcess(proc->pid, NULL)) return;

    /* 在添加进程结点之前 需要先找到它的父进程结点 */
    struct process *parent = findProcess(proc->ppid, NULL);

    /* 父进程不存在 说明此时它的父进程已经终止 丢弃孤儿进程 */
    if (!parent) return;
    
    proc->parent = parent;  //父进程
    struct process *fstChild = parent->child; //父进程的第一个子进程

    if (!fstChild) {
        /* 父进程当前没有子进程 */
        parent->child = proc;
    } else {
        if (OPT_NUMERIC) { //父进程的子进程按照pid排序
            if (proc->pid < fstChild->pid) {
                /* 头插 */
                proc->next = fstChild;
                parent->child = proc;
            } else {
                /* 找到插入位置 */
                while (fstChild->next && proc->pid > fstChild->pid)
                    fstChild = fstChild->next;
                proc->next = fstChild->next;
                fstChild->next = proc;
            }
        } else {
            /* 头插 */
            proc->next = fstChild;
            parent->child = proc;
        }
    } 
}

/*
 * 在进程名末尾添加进程号
 */
void 
appendProcessId(struct process *proc)
{
    char pidStr[32];
    sprintf(pidStr, "(%d)", proc->pid);
    strncat(proc->name, pidStr, 14);       //避免溢出
}

/*
 * 打印进程树
 */
void
printPSTree()
{
    printProcess(&root);
}

/*
 * 打印单个进程
 */
void 
printProcess(struct process *proc)
{
    if (OPT_SHOWPID) appendProcessId(proc);

    if (!proc) return;

    /* 打印前缀 */
    if (proc != &root) {
        if (proc == proc->parent->child) { //父进程的第一个子进程
            if (proc->next) {
                printf("-+-"); //不止一个子进程
            } else {
                printf("---"); //只有一个子进程
            }
        } else {  //父进程的其他子进程
            if (proc->next) {   
                printf(" |-");
            } else {          //父进程的最后一个子进程
                printf(" `-"); 
            }
        }
    } //else printf("");
    /* 打印进程名 */
    printf("%s", proc->name);
    /* 打印进程后缀 */
    if (!proc->child) printf("\n");  //叶子结点 
    
    /* 递归打印它的兄弟进程 和 子进程 */
    if (proc->child) printProcess(proc->child);
    if (proc->next) {
        /* 打印进程之前的占位字符串 */
        if (proc->next->parent) {
            printParentProcess(proc->next->parent);
        }        
        printProcess(proc->next);
    }
}

/*
 * 打印进程名之前的占位字符串
 */
void
printParentProcess(struct process *proc)
{
    if (proc->parent) printParentProcess(proc->parent);

    if (proc != &root) {
        if (proc->next) printf(" | ");
        else printf("   ");
    } //else printf("");
    
    printf("%*s", (int)strlen(proc->name), "");
}
