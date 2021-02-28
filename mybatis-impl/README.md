## horizontal-sharding for mybatis

### 1 快速启动

（1）初始化 `initalize.sql` 脚本

（2）编辑 `mybatis-impl/src/main/resources/application.yaml` 文件，修改数据库连接信息

（3）运行测试程序

### 2 测试程序

- `com.github.jitwxs.sharding.horizontal.mybatisimpl.CenterCrudTest` 中央库 CRUD 测试
- `com.github.jitwxs.sharding.horizontal.mybatisimpl.ShardingCrudTest` 分库 CRUD 测试
- `com.github.jitwxs.sharding.horizontal.mybatisimpl.CenterTransactionTest` 中央库事务测试
- `com.github.jitwxs.sharding.horizontal.mybatisimpl.ShardingTransactionTest` 分库事务测试
