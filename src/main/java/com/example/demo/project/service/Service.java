package com.example.demo.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.text.ParseException;

public interface Service {
    ResponseEntity<InputStreamResource> getInstructions() throws FileNotFoundException;
    ResponseEntity<InputStreamResource> getSchedule() throws FileNotFoundException;
    String getTime() throws ParseException;
}
