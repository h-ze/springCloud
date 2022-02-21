`ORDER BY col_name` 这句话的意思就是让结果按照 col_name 列的具体值做 ASC升序 或 DESC 降序

limit 3 offset 2  当 limit和offset组合使用的时候，limit后面只能有一个参数，表示要取的的数量,offset表示要跳过的数量

Limit 3,2 当limit后面跟两个参数的时候，第一个数表示要跳过的数量，后一位表示要取的数量

Distinct 去重



通过`ON`条件描述的关联关系;`INNER JOIN` 先将两个表数据连接到一起. 两个表中如果通过ID互相找不到的数据将会舍弃

`INNER JOIN` 可以简写做 `JOIN`. 两者是相同的意思，但我们还是会继续写作  `INNER JOIN` 以便和后面的  `LEFT JOIN`， `RIGHT JOIN`等相比较.        

 `INNER JOIN` 只会保留两个表都存在的数据（还记得之前的交集吗），这看起来意味着一些数据的丢失，在某些场景下会有问题.

真实世界中两个表存在差异很正常，所以我们需要更多的连表方式，也就是本节要介绍的左连接`LEFT JOIN`,右连接`RIGHT JOIN` 和 全连接`FULL JOIN`. 这几个    连接方式都会保留不能匹配的行

和`INNER JOIN` 语法几乎是一样的. 我们看看这三个连接方法的工作原理：
        在表A 连接 B, `LEFT JOIN`保留A的所有行，不管有没有能匹配上B        反过来 `RIGHT JOIN`则保留所有B里的行。最后`FULL JOIN` 不管有没有匹配上，同时保留A和B里的所有行

*GROUP BY我们可以先从字面上来理解，GROUP表示分组，BY后面写字段名，就表示根据哪个字段进行分组*

在 `GROUP BY`        分组语法中，我们知道数据库是先对数据做`WHERE`，然后对结果做分组，如果我们要对分组完的数据再筛选出几条如何办？        （想一下按年份统计电影票房，要筛选出>100万的年份？） 

一个不常用的语法 `HAVING` 语法将用来解决这个问题，他可以对分组之后的数据再做SELECT筛选.

**CASE WHEN条件表达式函数：类似JAVA中的IF ELSE语句**

```sql
CASE WHEN SCORE = 'A' THEN '优'



     WHEN SCORE = 'B' THEN '良'



     WHEN SCORE = 'C' THEN '中' ELSE '不及格' END
```

# 查询执行顺序

## 1. `FROM` 和 `JOIN`s

 `FROM` 或 `JOIN`会第一个执行，确定一个整体的数据范围. 如果要JOIN不同表，可能会生成一个临时Table来用于    下面的过程。总之第一步可以简单理解为确定一个数据源表（含临时表)

## 2. `WHERE`

我们确定了数据来源 `WHERE` 语句就将在这个数据源中按要求进行数据筛选，并丢弃不符合要求的数据行，所有的筛选col属性        只能来自`FROM`圈定的表. AS别名还不能在这个阶段使用，因为可能别名是一个还没执行的表达式

## 3. `GROUP BY`

如果你用了 `GROUP BY` 分组，那`GROUP BY` 将对之前的数据进行分组，统计等，并将是结果集缩小为分组数.这意味着    其他的数据在分组后丢弃.

## 4. `HAVING`

如果你用了 `GROUP BY` 分组, `HAVING` 会在分组完成后对结果集再次筛选。AS别名也不能在这个阶段使用	.

## 5. `SELECT`

确定结果之后，`SELECT`用来对结果col简单筛选或计算，决定输出什么数据.

## 6. `DISTINCT`

如果数据行有重复`DISTINCT` 将负责排重.

## 7. `ORDER BY`

在结果集确定的情况下，`ORDER BY` 对结果做排序。因为`SELECT`中的表达式已经执行完了。此时可以用AS别名.

## 8. `LIMIT` / `OFFSET`

最后 `LIMIT` 和 `OFFSET` 从排序的结果中截取部分数据.

## 结论

不是每一个SQL语句都要用到所有的句法，但灵活运用以上的句法组合和深刻理解SQL执行原理将能在SQL层面更好的解决数据问题，而不用把问题        都抛给程序逻辑.
