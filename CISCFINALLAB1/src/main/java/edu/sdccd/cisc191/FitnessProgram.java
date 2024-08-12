package edu.sdccd.cisc191;

import Database.WorkoutRepository;

public class FitnessProgram {
    public static void main(String[] args) {
        WorkoutRepository repository = new WorkoutRepository();
        repository.save(new Workout("workout1", "Cardio"));

        WorkoutManager manager = new WorkoutManager();
        manager.addWorkout(new Workout("workout2", "Strength"));

        WorkoutBST bst = new WorkoutBST();
        bst.addWorkout(new Workout("workout3", "Yoga"));
        Workout foundWorkout = bst.findWorkoutByName("Yoga");
        if (foundWorkout != null) {
            System.out.println("Workout found: " + foundWorkout.getName());
        } else {
            System.out.println("Workout not found.");
        }
    }
}

