# change-log-level 日志级别动态调整工具
本工具是根据美团的技术文章而写成的一个日志级别动态调整工具，具体文章请看：<br />
https://tech.meituan.com/change_log_level.html 《日志级别动态调整——小工具解决大问题》<br />

# 更新
2018-05-18<br />
1、增加简单使用http从web获取日志配置的类，可自行扩展。

2018-04-28 <br />
1、初始版，包括实现日志级别动态调整和方法调用处理单元<br />

# 使用帮助
引入依赖<br />
通过工厂获取对应的实现：AbstractProcessUnitImpl process = ProcessUnitFactory.newInstance(serverId).getXXX()<br />
通过process.setLogLevel(var)方法进行日志级别修改操作<br />

参数说明：<br />
1、String 类型，将所有logger统一设定为某个级别<br />
2、List<LoggerBean> 类型，指定类的logger级别<br />
3、null 空参数，默认设置所有logger为默认级别（INFO），如果需要修改默认级别，可以自行修改。<br />

# 未来
按照美团的使用方案，可以通过HTTP或其他RPC提供给其他项目；如果可视化还需要配置后台web或client。<br />
因此如果需要作为中间件本程序还没有完成，但是作为项目引入仍然可以使用。<br />

# 更多请看
https://tech.meituan.com/change_log_level.html 《日志级别动态调整——小工具解决大问题》<br />

