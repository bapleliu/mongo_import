# mongo_import
mongodb导入工具，可以直接执行，或者打成jar包执行。
run configuration：
  第一个参数为文件路径
  第二个参数为集合名，即导入的目标表。
  第三个参数为列名，以%号分割。
  
默认每行一条数据，以 分割，可以执行修改分隔符。

项目中使用到了Java8中的Stream<String>流式处理和lambda表达式，所以需要安装jdk8才能正常使用。
  
