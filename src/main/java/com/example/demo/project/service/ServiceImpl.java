package com.example.demo.service;

import com.example.demo.handler.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.text.ParseException;

@org.springframework.stereotype.Service
public class ServiceImpl implements com.example.demo.service.Service {
    Handler handler;

    @Autowired
    public ServiceImpl(Handler handler){
        this.handler=handler;
    }
    @Override
    public ResponseEntity<InputStreamResource> getInstructions() throws FileNotFoundException {
        return handler.getInstructionsFile();
    }

    @Override
    public ResponseEntity<InputStreamResource> getSchedule() throws FileNotFoundException {
        return handler.getScheduleFile();
    }

    @Override
    public String getTime() throws ParseException {
        return handler.getTime();
    }
}
