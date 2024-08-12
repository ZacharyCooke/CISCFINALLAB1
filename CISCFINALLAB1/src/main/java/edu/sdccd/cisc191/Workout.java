package edu.sdccd.cisc191;

import java.io.Serializable;
import java.util.UUID;

public class Workout implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;

    public Workout(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Workout(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
