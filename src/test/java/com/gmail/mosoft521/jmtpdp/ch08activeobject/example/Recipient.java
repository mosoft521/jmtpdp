package com.gmail.mosoft521.jmtpdp.ch08activeobject.example;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Recipient implements Serializable {
    private static final long serialVersionUID = -5427696559429827584L;
    private Set<String> to = new HashSet<String>();

    public void addTo(String msisdn) {
        to.add(msisdn);
    }

    public Set<String> getToList() {
        return (Set<String>) Collections.unmodifiableCollection(to);
    }
}