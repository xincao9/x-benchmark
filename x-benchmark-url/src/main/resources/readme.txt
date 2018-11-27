1.工具 professor-x-url 共分为4个模块 professor,login,flow,source
每个模块的作用
professor ： 用来将测试结果同步到 professor ui 中进行可视化分析和记录
login ：如果测试的url，需要先进行登录认证才可以请求时，使用
flow ：请求流，每个事件都需要进行的请求过程
source ：请求填充的数据来源，可以是固定的参数多次请求(SEQUENCE)，也可以是来源于文件(FILE), 且flow中的请求将通过%integer的方式来引用


{
    "professor": {
        "host": "localhost",
        "port": "8080",
        "account": "admin",
        "passwd": "admin",
        "topic": "professor_x_web性能评估",
        "title": "professor_x_web各项指标随着并发的变化"
    },
    "login": {
        "url": "http://localhost:8080/professor_x_web/do_login",
        "method": "post",
        "data": {
            "account": "admin",
            "passwd": "admin"
        },
        "session": {
            "keepalive": true,
            "timeout": 30000
        }
    },
    "flow": [
        {
            "url": "http://localhost:8080/professor_x_web/report/list",
            "method": "get"
        },
        {
            "url": "http://localhost:8080/professor_x_web/report/chart",
            "method": "get",
            "data": {
                "report_id": "1",
                "start_time": "%1",
                "end_time": "%2"
            }
        }
    ],
    "source": {
        "type": "file",
        "filename": "classes/startTime_endTime.txt",
        "div": ",",
        "indexs": [0, 1],
        "size": 10
    }
}
