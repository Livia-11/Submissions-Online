package com.submission.mis.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "instructors")
public class Instructor extends User {
    @OneToMany(mappedBy = "instructor")
    private Set<Assignment> assignments;

    // Getter and setter for assignments only, other getters/setters inherited from User
    public Set<Assignment> getAssignments() { return assignments; }
    public void setAssignments(Set<Assignment> assignments) { this.assignments = assignments; }
} 