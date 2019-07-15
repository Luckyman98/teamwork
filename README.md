### SQL生成引擎开发文档

#### 当value指定的值是一个sql语句时如何处理?
- FilterParams的value为Object.该value可以为Integer,String,FilterTable等任意类型.如果为FilterTable类型则代表value是一个sql语句

### 逻辑运算符
```java
public class A{
//映射条件判断类型
    private static String mapType(String type) {
        switch (type) {
            case "eq": {
                return "=";
            }
            case "uneq": {
                return "!=";
            }
            case "gt": {
                return ">";
            }
            case "gt_eq": {
                return ">=";
            }
            case "lt": {
                return "<";
            }
            case "lt_eq": {
                return "<=";
            }
            default: {
                return type;
            }
        }
    }
}
```

