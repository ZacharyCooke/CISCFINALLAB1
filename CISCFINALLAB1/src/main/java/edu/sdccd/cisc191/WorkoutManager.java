package edu.sdccd.cisc191;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorkoutManager {
    private final Map<String, Workout> workoutMap = new HashMap<>();
    private final Set<String> workoutNames = new HashSet<>();

    public void addWorkout(Workout workout) {
        workoutMap.put(workout.getId(), workout);
        workoutNames.add(workout.getName());
    }

    public Workout getWorkoutById(String id) {
        return workoutMap.get(id);
    }

    public boolean workoutExists(String name) {
        return workoutNames.contains(name);
    }

    public void removeWorkoutById(String id) {
        Workout workout = workoutMap.remove(id);
        if (workout != null) {
            workoutNames.remove(workout.getName());
        }
    }
}
