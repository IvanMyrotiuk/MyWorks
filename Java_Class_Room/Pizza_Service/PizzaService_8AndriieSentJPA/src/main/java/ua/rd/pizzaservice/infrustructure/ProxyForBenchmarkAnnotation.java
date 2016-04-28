package ua.rd.pizzaservice.infrustructure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author andrii
 */
public class ProxyForBenchmarkAnnotation {

    private final Object proxied;

    ProxyForBenchmarkAnnotation(Object bean) {
        this.proxied = bean;
    }

    public Object proxyObjForBenchmarkAnnotation() throws Exception {
        Class<?> clazz = proxied.getClass();
        System.out.println(clazz);

        for (Method m : clazz.getMethods()) {
            if (m.isAnnotationPresent(Benchmark.class)) {
                return createProxyObj();
            }

        }
        return proxied;

    }

    private Object createProxyObj() throws IllegalArgumentException {

        final Class<?> type = proxied.getClass();

        return Proxy.newProxyInstance(type.getClassLoader(),
                type.getInterfaces(),
                new InvocationHandler() {

                    @Override
                    public Object invoke(
                            Object proxy,
                            Method method,
                            Object[] args) throws Throwable {
                                System.out.println("Called: " + method.getName());
                                if (type.getMethod(method.getName(), method.getParameterTypes())
                                .isAnnotationPresent(Benchmark.class)) {

                                    System.out.println("Benchmark start: "
                                            + method.getName());
                                    long start = System.nanoTime();
                                    Object retVal = method.invoke(proxied, args);
                                    long result = System.nanoTime() - start;
                                    System.out.println(result);
                                    System.out.println("Benchmark finish: "
                                            + method.getName());
                                    return retVal;
                                } else {
                                    return method.invoke(proxied, args);
                                }
                            }
                });

    }

}
