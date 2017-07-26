package com.example.admin.myretrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.Locale;

/**
 * Created by fushuang on 2017/7/25.
 */

public class Retrofit  {

    public String base;

    private Retrofit() {
    }

    //在将接口实例化的时候添加代理,动态实现接口
    public  <T> T create(Class<T> tClass){

        final Object o = Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, new InvocationHandler() {
            /**
             *
             * @param proxy     代理
             * @param method    要代理的方法,当执行接口里面方法的时候,调用invoke方法,相当于用动态将接口进行了实现
             * @param args      接口里面方法传入的参数,在这里面用来替换注解中的占位符,比如%d
             * @return          接口实体对象
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                UrlString annotation = method.getAnnotation(UrlString.class);
                if (annotation != null) {
                    String url = String.format(Locale.CHINA, base + annotation.value(), args);
                    Class<?> returnType = method.getReturnType();
                    if (returnType.equals(Call.class)) {
                        ParameterizedType genericReturnType = (ParameterizedType) method.getGenericReturnType(); //带有泛型的返回值
                        Class type = (Class) genericReturnType.getActualTypeArguments()[0];  //获得返回值中的泛型
                        return new Call(url,type);
                    }
                }
                return null;
            }
        });

        return ((T) o);
    }

    public static class Builder{

        private Retrofit retrofit=new Retrofit();

        public Builder baseUrl(String baseUrl){
            retrofit.base=baseUrl;
            return this;
        }
        public Retrofit build(){
            return retrofit;
        }
    }

}
