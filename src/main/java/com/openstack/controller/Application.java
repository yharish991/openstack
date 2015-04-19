package com.openstack.controller;

import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	public static HashMap<String, Process> runningAndroidEmulator = new HashMap<String, Process>();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
