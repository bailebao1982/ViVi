package com.spstudio.wxserver.common.handler;

import com.spstudio.wxserver.common.handler.annotation.BtnHandler;
import com.spstudio.wxserver.modules.member.handler.HelpBtnHandler;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.hibernate.MappingException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Soul on 2016/12/7.
 */

public class BtnHandlerFactoryBean extends AbstractHandler  implements ResourceLoaderAware, InitializingBean, ApplicationContextAware {

    private static final String RESOURCE_PATTERN = "/**/*.class";
    private static final TypeFilter[] DEFAULT_HANDLER_TYPE_FILTERS = new TypeFilter[] {
            new AnnotationTypeFilter(BtnHandler.class, false)};
    private TypeFilter[] handlerTypeFilters = DEFAULT_HANDLER_TYPE_FILTERS;

    private Map<String, Class<?>> keyClassMap;

    private String[] packagesToScan;

    private ResourcePatternResolver resourcePatternResolver;

    private static ApplicationContext applicationContext;

    public BtnHandlerFactoryBean() {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(getResourceLoader());
        this.keyClassMap = new HashMap<String, Class<?>>();
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ResourceLoader getResourceLoader() {
        if (this.resourcePatternResolver == null) {
            this.resourcePatternResolver = new PathMatchingResourcePatternResolver();
        }
        return this.resourcePatternResolver;
    }

    public void setPackagesToScan(String... packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    private boolean matchesHandlerTypeFilter(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {
        if (this.handlerTypeFilters != null) {
            for (TypeFilter filter : this.handlerTypeFilters) {
                if (filter.match(reader, readerFactory)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void scanPackages(){
        Set<String> handlerClassNames = new TreeSet<String>();
        try {
            for (String pkg : packagesToScan) {
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                        ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
                Resource[] resources = this.resourcePatternResolver.getResources(pattern);
                MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        MetadataReader reader = readerFactory.getMetadataReader(resource);
                        String className = reader.getClassMetadata().getClassName();
                        if (matchesHandlerTypeFilter(reader, readerFactory)) {
                            handlerClassNames.add(className);
                        }
                    }
                }
            }
        }

        catch (IOException ex) {
            throw new MappingException("Failed to scan classpath for unlisted classes", ex);
        }
        try {
            ClassLoader cl = this.resourcePatternResolver.getClassLoader();
            for (String className : handlerClassNames) {
                Class<?> clsDef = cl.loadClass(className);
                BtnHandler btnHandler = clsDef.getAnnotation(BtnHandler.class);
                if(btnHandler != null){
                    String key = btnHandler.key();
                    if(!key.isEmpty()){
                        this.keyClassMap.put(key.toLowerCase(), clsDef);
                    }
                }
            }
        }
        catch (ClassNotFoundException ex) {
            throw new MappingException("Failed to load annotated classes from classpath", ex);
        }
    }

    @Override
    public WxMpXmlOutMessage handle(
            WxMpXmlMessage wxMpXmlMessage,
            Map<String, Object> map,
            WxMpService wxMpService,
            WxSessionManager wxSessionManager) throws WxErrorException {

        String eventKey = wxMpXmlMessage.getEventKey().toLowerCase();
        Class<?> clsDef = this.keyClassMap.get(eventKey);

        if(clsDef != null){
            try {
                Method handleProxy = clsDef.getDeclaredMethod("handle",
                        WxMpXmlMessage.class,
                        Map.class,
                        WxMpService.class,
                        WxSessionManager.class);
                Object bean = applicationContext.getBean(clsDef);
                //return bean.handle(wxMpXmlMessage, map, wxMpService, wxSessionManager);
                return (WxMpXmlOutMessage)
                        handleProxy.invoke(bean, wxMpXmlMessage, map, wxMpService, wxSessionManager);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        WxMpXmlOutTextMessage m
                = WxMpXmlOutMessage.TEXT()
                .content("错误：不能找到该key对应的事件处理器")
                .fromUser(wxMpXmlMessage.getToUser())
                .toUser(wxMpXmlMessage.getFromUser())
                .build();
        logger.error("buttonMessageHandler cant find the key handler" + m.getContent());
        return m;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.scanPackages();
    }
}
