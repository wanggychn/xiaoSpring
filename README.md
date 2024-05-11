# xiaoSpring
spring源码学习

1. 创建自己的容器 XiaoApplicationContext
2. 需要读取配置、配置类的方式，此处采取配置类 AppConfig
3. 创建 ComponentScan、Component 注解，配置扫描路径、
4. 在容器构造方法中，生成beanDefinition，放入beanDefinitionMap中，并生成单例bean
5. 通过getBean方法获取bean对象，如果是多例bean，则每次创建新对象