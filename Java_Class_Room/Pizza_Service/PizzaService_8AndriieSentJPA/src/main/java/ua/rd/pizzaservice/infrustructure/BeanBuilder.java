package ua.rd.pizzaservice.infrustructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author andrii
 */
public class BeanBuilder {

        private final Class<?> clazz;
        private ApplicationContext applicationContext;
        
        private Object bean;

        public BeanBuilder(Class<?> clazz, ApplicationContext applicationContext) {
            this.clazz = clazz;
            this.applicationContext = applicationContext;
        }

        void createBean() throws SecurityException, Exception {            
            Constructor<?> constructor = clazz.getConstructors()[0];
            if (constructor.getParameterCount() == 0) {
                bean = clazz.newInstance();
            } else {
                bean = createNewInstanceWithParams(constructor);
            }            
        }

        private Object createNewInstanceWithParams(Constructor<?> constructor) throws InvocationTargetException, IllegalArgumentException, InstantiationException, Exception, IllegalAccessException {
            Object bean;
            Object[] paramBeans = getParams(constructor);
            bean = constructor.newInstance(paramBeans);
            return bean;
        }

        private Object[] getParams(Constructor<?> constructor) throws Exception {
            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] paramBeans = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                paramBeans[i] = getBeanByType(paramTypes[i]);
            }
            return paramBeans;
        }

        private Object getBeanByType(Class<?> paramType) throws Exception {
            String paramName = getBeanNameByType(paramType);
            return applicationContext.getBean(paramName);
        }

        private String getBeanNameByType(Class<?> paramType) {
            String paramTypeName = paramType.getSimpleName();
            String paramName
                    = changeFirstLetterToLowerCase(paramTypeName);
            return paramName;
        }

        private String changeFirstLetterToLowerCase(String paramTypeName) {
            String paramName
                    = Character.toLowerCase(paramTypeName.charAt(0))
                    + paramTypeName.substring(1);
            return paramName;
        }

        void createBeanProxy() throws Exception {
            bean = new ProxyForBenchmarkAnnotation(bean)
                    .proxyObjForBenchmarkAnnotation();
            //Proxy.newProxyInstance(null, interfaces, null);
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        void callPostConstructMethod() throws Exception {
            Method[] methods = clazz.getMethods();
            for (Method m : methods) {
                if (m.getAnnotation(PostConstruction.class) != null) {
                    m.invoke(bean);
                }
            }
        }

        void callInitMethod() {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        Object build() {
            return bean;
        }
    }
