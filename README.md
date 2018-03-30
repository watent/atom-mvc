atom-mvc
==============
    project spring mvc simple demo
    
### 功能
    
    1.Dispatcher  处理请求 分发到类方法
    2.IOC 依赖注入 创建实例
    3.AOP 动态代理 事物控制
    
### 自定义注解
    
    1.元注解
      作用在注解上的注解
      
    @Target     : 作用的目标
    @Retention  : 注解生命周期
    @Documented : 可以被JavaDoc扫描到
    
### 包扫描

    根据基础包扫描所有子包和类 
    根据类拿到所有类文件
    根据包名和类名反射出实例
    
### IOC 

    获取Class对象
    获取Field对象
    获取field上面的annotation对象
    根据annotation对象拿annotation对象的属性
    把属性当key拿到map中的实例
    然后field set把实例设置进去
    
### 建立一个url与类中方法的映射关系
    
    拿到类的Class对象
    拿到Method对象
    拿到Method上面的annotation对象
    把url和method对象存到map中