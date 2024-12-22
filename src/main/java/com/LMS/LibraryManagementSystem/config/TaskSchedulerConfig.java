package com.LMS.LibraryManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration // This annotation marks the class as a Spring configuration class
public class TaskSchedulerConfig {

    // The @Bean annotation indicates that this method will provide a bean that will be managed by Spring
    @Bean
    public TaskScheduler taskScheduler() {
        // Creating an instance of ThreadPoolTaskScheduler
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        // The setPoolSize method defines the number of threads that will be available in the thread pool
        // Setting it to 10 means that up to 10 tasks can be executed concurrently.
        // If more tasks are scheduled, they will be queued and executed when a thread is free.
        scheduler.setPoolSize(100); // Maximum number of concurrent tasks to be executed.

        // The setThreadNamePrefix method sets a custom name prefix for threads in the thread pool
        // This helps in identifying the threads related to this specific scheduler in logs or when debugging.
        // Threads will have names like "Library-Scheduler-1", "Library-Scheduler-2", etc.
        scheduler.setThreadNamePrefix("Library-Scheduler-"); // Custom prefix for thread names

        // Returning the configured TaskScheduler bean
        // Spring will manage this instance and inject it wherever needed in the application
        return scheduler;
    }
}


