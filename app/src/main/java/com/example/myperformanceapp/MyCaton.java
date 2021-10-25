package com.example.myperformanceapp;


/*
    1. 自动化卡顿检测方案

    2. ANR
         导出文件： adb pull data/anr/traces.txt

        通过FileObserver监控文件变化
        ARN-watchDog

        IPC 问题检测

    3. 界面秒开
        Lancet  AOP框架

        耗时盲区监测

    4. 你是怎么做卡顿优化的

        第一阶段：通过系统工具定位 通过异步优化解决
        第二阶段： 自动化卡顿方案优化
        第三阶段：线上监控和线下线下监测工具建设

    5. 怎么自动化的获取卡顿信息
         mLooping.println
         高频采集 找出重复堆栈
 */

public class MyCaton {

}
