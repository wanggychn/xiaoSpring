package com.xiao.spring;

import com.xiao.service.AppConfig;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class XiaoApplicationContext {

    private Class configClass;
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap();
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap();

    public XiaoApplicationContext(Class<AppConfig> appConfigClass) throws ClassNotFoundException {
        this.configClass = appConfigClass;
        // 扫描 --> beanDefinition --> 放入 beanDefinitionMap
        if (appConfigClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = appConfigClass.getAnnotation(ComponentScan.class);
            String componentScanValue = componentScanAnnotation.value();
            String path = componentScanValue.replace(".", "/");
            ClassLoader classLoader = configClass.getClassLoader();
            URL resource = classLoader.getResource(path);
            String filePath = resource.getFile();
            File file = new File(filePath);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    String absolutePath = f.getAbsolutePath();
                    if (absolutePath.endsWith(".class")) {
                        String beanPath = componentScanValue + "." + absolutePath.substring(absolutePath.lastIndexOf("\\") + 1, absolutePath.indexOf(".class"));
                        // 类加载器根据 beanPath(com.xiao.service.UserService格式) 获取类对象
                        Class<?> clazz = classLoader.loadClass(beanPath);

                        if (clazz.isAnnotationPresent(Component.class)) {
                            // 获取beanName，准备放入beanDefinition池中
                            Component componentAnnotation = clazz.getAnnotation(Component.class);
                            String beanName = componentAnnotation.value();
                            // 如果component没有设置value值，则默认用类名首字母小写
                            if ("".equals(beanName)) {
                                beanName = Introspector.decapitalize(clazz.getSimpleName());
                            }

                            // 此处不能直接创建bean，不清楚是单例还是多例，所以创建beanDefinition
                            BeanDefinition beanDefinition = getBeanDefinition(clazz);

                            // 将 扫描并定义好的beanDefinition 放入 beanDefinition池 中
                            beanDefinitionMap.put(beanName, beanDefinition);
                        }
                    }
                }
            }
        }
        // 实例化 单例bean
        for (String beanName : beanDefinitionMap.keySet()) {
            Object singletonBean = createBean(beanName, beanDefinitionMap.get(beanName));
            singletonObjects.put(beanName, singletonBean);
        }
    }

    /**
     * 获取 beanDefinition
     */
    private static BeanDefinition getBeanDefinition(Class<?> clazz) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setType(clazz);
        if (clazz.isAnnotationPresent(Scope.class)) {
            Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
            beanDefinition.setScope(scopeAnnotation.value());
        } else {
            beanDefinition.setScope("singleton");
        }
        return beanDefinition;
    }

    /**
     * 根据 beanName 获取 bean对象
     */
    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException();
        } else {
            if ("singleton".equals(beanDefinition.getScope())) {
                // 单例，直接从单例池中获取
                Object obj = singletonObjects.get(beanName);
                if (obj == null) {
                    Object o = createBean(beanName, beanDefinition);
                    singletonObjects.put(beanName, o);
                }
                return obj;
            } else {
                // 多例，直接创建，每次都返回新的对象
                return createBean(beanName, beanDefinition);
            }
        }
    }

    /**
     * 创建 bean对象
     * 通过反射调用无参构造方法
     */
    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();
        try {
            Object instance = clazz.getConstructor().newInstance();
            Field[] fields = clazz.getFields();
            // 依赖注入
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    field.set(instance, getBean(field.getName()));
                }
            }
            // 属性注入
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
