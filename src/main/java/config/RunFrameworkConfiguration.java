package config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("main")
public class RunFrameworkConfiguration {
    public RunFrameworkConfiguration(){}
}
