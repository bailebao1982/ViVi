# ViVi
ViViCheckInSystem


Package structure:
    main.java.spstudio
        common                          ----        定义了木块间通用的一些东西
            response                    ----        通用 response json 数据格式Bean
            search                      ----        通用 search json 数据结构Bean
        config                          ----        项目总的配置文件，包括spring mvc配置文件 以及 spring common配置文件
        modules                         ----        每一模块的总目录
            member                      ----        具体的模块包
                bean                    ----        前后台的通讯数据结构Bean
                    request             ----        请求Json数据Bean
                    response            ----        响应Json数据Bean
                config                  ----        模块单独的spring配置文件目录
                controller              ----        模块的router类，请求分发类
                dao                     ----        Data Access Object
                entity                  ----        Database Table entity
                service                 ----

