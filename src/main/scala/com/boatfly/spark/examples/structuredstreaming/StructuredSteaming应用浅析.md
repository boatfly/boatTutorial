# Spark StructuredStreaming
核心思想：把持续不断的流式数据当成一个不断追加的表。(无界表 unbounded table)
- 输入表
- 结果表
 - 写入到外部接收器(externel sink)
   - file
   - console
   - kafka
   - memory
   - foreach
 - 模式
   - complete 全部输出，必须有聚合
   - append 追加模式，只输出哪些将来不可能再更新的数据
   - update 
     - 只输出变化的部分
     - 没有聚合的时候，==update
     - 有聚合的时候，一定要有水印，才能使用
   



