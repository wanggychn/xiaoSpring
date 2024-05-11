# xiaoSpring
### spring源码学习

1. 创建自己的容器 XiaoApplicationContext
2. 需要读取配置、配置类的方式，此处采取配置类 AppConfig
3. 创建 ComponentScan、Component 注解，配置扫描路径、
4. 在容器构造方法中，生成beanDefinition，放入beanDefinitionMap中，并生成单例bean
5. 通过getBean方法获取bean对象，如果是多例bean，则每次创建新对象


### spi机制
1. 两个概念：（接口）调用方和（接口）实现方
2. api的机制是：接口定义在实现方，调用方调用接口
3. spi的机制是：接口定义在调用方，调用者调用接口
4. com.spi.demo.lib.service-provider-interface.jar中定义日志接口的规范，即调用方
5. com.spi.demo.lib.service-provider.jar中写日志的具体方式，即实现方
6. Java 中的 SPI 机制就是在每次类加载的时候会先去找到 class 相对目录下的 META-INF 文件夹下的 services 文件夹下的文件，将这个文件夹下面的所有文件先加载到内存中，然后根据这些文件的文件名和里面的文件内容找到相应接口的具体实现类，找到实现类后就可以通过反射去生成对应的对象，保存在一个 list 列表里面，所以可以通过迭代或者遍历的方式拿到对应的实例对象，生成不同的实现
7. 然后通过ServiceLoader.load(Interface.class)即可获得所有实例，想用哪个日志具体实现，获取对应的即可 
8. 工作重点原理：ServiceLoader