package com.LMS.LibraryManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration //  Marks this class as a configuration class for Spring.
@EnableAsync   // Enables Spring's asynchronous processing capabilities.
public class AsyncConfig {

    @Bean // Defines a custom bean for handling asynchronous tasks.
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // Sets the minimum number of threads that will always remain active in the thread pool.
        executor.setCorePoolSize(5);

        // Sets the maximum number of threads allowed in the pool.
        // If the number of tasks exceeds this limit and the queue is full, new tasks will be rejected.
        executor.setMaxPoolSize(10);

        // Specifies the size of the task queue.
        // Tasks exceeding the core pool size but less than the max pool size will wait in this queue.
        executor.setQueueCapacity(25);

        // Sets a prefix for thread names in this pool, making it easier to identify them during debugging.
        executor.setThreadNamePrefix("AsyncThread-");

        // Initializes the thread pool with the provided configurations.
        executor.initialize();

        // Returns the configured thread pool executor as a bean to be used by Spring.
        return executor;
    }
}


