1.工具 professor-x-jms 共分为4个模块 professor,jms,flow,source
每个模块的作用
professor ： 用来将测试结果同步到 professor ui 中进行可视化分析和记录
jms ：用来创建activemq链接的参数
flow ：请求流，每个事件都需要进行的请求过程
source ：请求填充的数据来源，可以是固定的参数多次请求(SEQUENCE)，也可以是来源于文件(FILE), 且flow中的请求将通过%integer的方式来引用


{
    "professor": {
        "host": "localhost",
        "port": "8080",
        "account": "admin",
        "passwd": "admin",
        "topic": "acitvemq",
        "title": "activemq各项指标随着并发的变化"
    },
    "jms": {
        "address": "failover:(tcp://amq-1:61616,tcp://amq-2:61616,amq-3:61616)?jms.useAsyncSend=true",
        "account": "admin",
        "passwd": "admin"
    },
    "flow": [
        {
            "destination": "app-1",
            "data": {
                "message": "%0"
            }
        }
    ],
    "source": {
        "type": "FILE",
        "filename": "classes/message.txt",
        "div": ",",
        "indexs": [0],
        "size": 10
    }
}