# SPPlatform



Package structure:
    DataService                                 将数据服务抽取出来的数据提供模块，方便复用
        com.spstudio.common                     通用数据格式
        com.spstudio.modules                    每个模块自己的config, entity, dao, service
            ...

    RestServer                                  提供restful api的Server端代码
        com.spstudio.config                     rest-api server端配置
            spring-common.xml                   spring基础配置
            spring-mvc.xml                      spring-mvc的相关配置
        com.spstudio.modules
            member.bean                         member模块下的rest api JSON数据通讯格式
            member.controller                   定义member模块具体的请求route


    WechatServer                                提供微信服务器的Server代码
        com.spstudio.wxserver.config            微信server端配置
            spring-common.xml                   spring基础配置
            spring-mvc.xml                      spring-mvc的相关配置
        com.spstudio.wxserver.modules
            member.controller                   定义member模块具体的请求route