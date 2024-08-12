package edu.sdccd.cisc191;

class Node {
    Workout workout;
    Node left, right;

    public Node(Workout workout) {
        this.workout = workout;
        left = right = null;
    }
}


