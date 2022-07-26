package examples.first.core;

import examples.first.core.beans.Client;
import examples.first.core.beans.Event;
import examples.first.core.enums.EventType;
import examples.first.core.loggers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

import static examples.first.core.enums.EventType.*;

public class App {
    public Client client;
    public EventLogger eventLogger;
    public EventLogger defaultLogger;
    public Map<EventType, EventLogger> loggers;

    public void logEvent(EventType type, Event event){
        EventLogger logger = loggers.get(type);
        if (logger == null) {
            logger = defaultLogger;
        }
        logger.logEvent(event);
    }

    public App(Client client, EventLogger eventLogger, EventLogger defaultLogger, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.eventLogger = eventLogger;
        this.defaultLogger = defaultLogger;
        this.loggers = loggers;
    }

    public static void main(String[] args) {

        ApplicationContext actx = new AnnotationConfigApplicationContext(AppConfig.class, LoggersConfig.class);


//        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) actx.getBean("app");
        Event event = (Event) actx.getBean("event");
        ConsoleEventLogger consoleEventLogger = (ConsoleEventLogger) actx.getBean("eventLogger");
        FileEventLogger fileEventLogger = (FileEventLogger) actx.getBean("fileEventLogger");
        CacheFileEventLogger cacheFileEventLogger = (CacheFileEventLogger) actx.getBean("cacheFileEventLogger");



        event.setMsg("Some event for 1");
        app.logEvent(INFO, event);

        event.setMsg("Some event for 2");
        app.logEvent(ERROR, event);

        event.setMsg("Some event for 3");
        app.logEvent(null, event);

//        actx.close();
    }
}
