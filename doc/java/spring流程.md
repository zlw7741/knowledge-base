
    @Overridepublic
    void refresh() throws BeansException, IllegalStateException {
        synchronized (this.startupShutdownMonitor) {
            // 第一步:刷新前的预处理   
            prepareRefresh();
            // 第二步: 获取BeanFactory并注册到 BeanDefitionRegistry  
            ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
            // 第三步:加载BeanFactory的预准备工作(BeanFactory进行一些设置，比如context的类加载器等)  
            prepareBeanFactory(beanFactory);
            try {
                // 第四步:完成BeanFactory准备工作后的前置处理工作     
                postProcessBeanFactory(beanFactory);
                // 第五步:实例化BeanFactoryPostProcessor接口的Bean     
                invokeBeanFactoryPostProcessors(beanFactory);
                // 第六步:注册BeanPostProcessor后置处理器，在创建bean的后执行     
                registerBeanPostProcessors(beanFactory);
                // 第七步:初始化MessageSource组件(做国际化功能;消息绑定，消息解析);     
                initMessageSource();
                // 第八步:注册初始化事件派发器     
                initApplicationEventMulticaster();
                // 第九步:子类重写这个方法，在容器刷新的时候可以自定义逻辑     
                onRefresh();
                // 第十步:注册应用的监听器。就是注册实现了ApplicationListener接口的监听器    
                registerListeners();
                // 第十一步:初始化所有剩下的非懒加载的单例bean 初始化创建非懒加载方式的单例Bean实例(未设置属性)    
                finishBeanFactoryInitialization(beanFactory);
                // 第十二步: 完成context的刷新。主要是调用LifecycleProcessor的onRefresh()方法，完成创建    
                finishRefresh();
            }
        }
    }
