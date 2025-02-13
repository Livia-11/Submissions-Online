package com.submission.mis.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends User {
    @OneToMany(mappedBy = "student")
    private Set<Submission> submissions;

    // Getter and setter for submissions only, other getters/setters inherited from User
    public Set<Submission> getSubmissions() { return submissions; }
    public void setSubmissions(Set<Submission> submissions) { this.submissions = submissions; }
} 