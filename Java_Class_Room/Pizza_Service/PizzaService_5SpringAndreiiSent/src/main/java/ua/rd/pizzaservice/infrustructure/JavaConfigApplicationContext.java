package ua.rd.pizzaservice.infrustructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author andrii
 */
public class JavaConfigApplicationContext implements ApplicationContext {

    private final Config config = new JavaConfig();
    private final Map<String, Object> context = new HashMap<>();

    @Override
    public Object getBean(String beanName) throws Exception {
        Object bean = context.get(beanName);

        if (bean != null) {
            return bean;
        }

        Class<?> clazz = config.getImpl(beanName);

        if (clazz == null) {
            throw new RuntimeException("Bean definition is not found: " + beanName);
        }

        //bean = createBean(clazz);
        BeanBuilder builder = new BeanBuilder(clazz, this);
        builder.createBean();
        builder.createBeanProxy();
        builder.callPostConstructMethod();
        builder.callInitMethod();
        bean = builder.build();

        context.put(beanName, bean);
        return bean;

    }

//    class BeanBuilder {
//
//        private final Class<?> clazz;
//        private Object bean;
//
//        public BeanBuilder(Class<?> clazz) {
//            this.clazz = clazz;
//        }
//
//        private void createBean() throws SecurityException, Exception {
//            Object bean;
//            Constructor<?> constructor = clazz.getConstructors()[0];
//            if (constructor.getParameterCount() == 0) {
//                bean = clazz.newInstance();
//            } else {
//                bean = createNewInstanceWithParams(constructor);
//            }            
//        }
//
//        private Object createNewInstanceWithParams(Constructor<?> constructor) throws InvocationTargetException, IllegalArgumentException, InstantiationException, Exception, IllegalAccessException {
//            Object bean;
//            Object[] paramBeans = getParams(constructor);
//            bean = constructor.newInstance(paramBeans);
//            return bean;
//        }
//
//        private Object[] getParams(Constructor<?> constructor) throws Exception {
//            Class<?>[] paramTypes = constructor.getParameterTypes();
//            Object[] paramBeans = new Object[paramTypes.length];
//            for (int i = 0; i < paramTypes.length; i++) {
//                paramBeans[i] = getBeanByType(paramTypes[i]);
//            }
//            return paramBeans;
//        }
//
//        private Object getBeanByType(Class<?> paramType) throws Exception {
//            String paramName = getBeanNameByType(paramType);
//            return getBean(paramName);
//        }
//
//        private String getBeanNameByType(Class<?> paramType) {
//            String paramTypeName = paramType.getSimpleName();
//            String paramName
//                    = changeFirstLetterToLowerCase(paramTypeName);
//            return paramName;
//        }
//
//        private String changeFirstLetterToLowerCase(String paramTypeName) {
//            String paramName
//                    = Character.toLowerCase(paramTypeName.charAt(0))
//                    + paramTypeName.substring(1);
//            return paramName;
//        }
//
//        private void createBeanProxy() {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        private void callPostCreateMethod() {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        private void callInitMethod() {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        private Object build() {
//            return bean;
//        }
//    }
}
