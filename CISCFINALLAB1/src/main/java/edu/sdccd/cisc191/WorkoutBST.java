package edu.sdccd.cisc191;

public class WorkoutBST {
    private Node root;

    public void addWorkout(Workout workout) {
        root = addRecursive(root, workout);
    }

    private Node addRecursive(Node current, Workout workout) {
        if (current == null) {
            return new Node(workout);
        }

        if (workout.getName().compareTo(current.workout.getName()) < 0) {
            current.left = addRecursive(current.left, workout);
        } else if (workout.getName().compareTo(current.workout.getName()) > 0) {
            current.right = addRecursive(current.right, workout);
        } else {
            return current;
        }

        return current;
    }

    public Workout findWorkoutByName(String name) {
        return findRecursive(root, name);
    }

    private Workout findRecursive(Node current, String name) {
        if (current == null) {
            return null;
        }

        if (name.equals(current.workout.getName())) {
            return current.workout;
        }

        return name.compareTo(current.workout.getName()) < 0
                ? findRecursive(current.left, name)
                : findRecursive(current.right, name);
    }
}