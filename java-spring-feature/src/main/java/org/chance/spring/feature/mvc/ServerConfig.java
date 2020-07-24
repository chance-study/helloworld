package org.chance.spring.feature.mvc;

/**
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-24 14:57:28
 */
//@Configuration
//public class ServerConfig implements EmbeddedServletContainerCustomizer {
//
//    @Override
//    public void customize(ConfigurableEmbeddedServletContainer container) {
//
//        // 此处只处理Tomcat类型的嵌入式容器
//        if (container instanceof TomcatEmbeddedServletContainerFactory) {
//            TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
//
//            // 此处一般是读取配置文件~~~此处我就偷懒了~~~
//            //String[] portsArray = ports.split(",");
//            String[] portsArray = {"7070", "9090"};
//            for (String portStr : portsArray) {
//                int port = Integer.parseInt(portStr);
//                // Tomcat中，一个Connecter监听一个端口 指定协议为HTTP/1.1
//                Connector httpConnector = new Connector("HTTP/1.1");
//                httpConnector.setPort(port);
//                // 添加一个额外的端口  和server.port不冲突~
//                tomcat.addAdditionalTomcatConnectors(httpConnector);
//            }
//        }
//    }
//}

