package com.analysis.graph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableCaching
public class GraphAnalysisWebApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication springApplication = new SpringApplication(GraphAnalysisWebApplication.class);
        springApplication.run(args);

        System.out.printf("\n\tGraphAnalysisWebApplication started\n\t" +
                        "Access URLs:\n----------------------------------------------------------\n\t" +
                        "Local: \t\thttp://127.0.0.1:%d\n\t" +
                        "External: \thttp://%s:%d\n----------------------------------------------------------\n",
                9001,
                InetAddress.getLocalHost().getHostAddress(),
                9001);
    }
}
