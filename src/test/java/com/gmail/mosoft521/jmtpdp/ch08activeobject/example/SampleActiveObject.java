package com.gmail.mosoft521.jmtpdp.ch08activeobject.example;

import java.util.concurrent.Future;

public interface SampleActiveObject {
    public Future<String> process(String arg, int i);
}