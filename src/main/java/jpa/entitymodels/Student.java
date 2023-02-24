package jpa.entitymodels;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name="student")
public class Student {

   @Id
   @Column(name="email",length=50)
   private String email;
   @Column(unique = true, nullable = false, length = 50)
   private String name;
   @Column(unique = true, nullable = false, length = 50)
   private String password;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Student_Course",
            joinColumns = { @JoinColumn(name = "email") },
            inverseJoinColumns = { @JoinColumn(name = "id") })
    List<Course> course = new ArrayList<>();

    public Student() {}

    public Student(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Course> getCourse() {
        return course;
    }
    public void setCourses(List<Course> course) {
        this.course = course;
    }


    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Student))
            return false;
        Student other = (Student) obj;
        if (email != other.email)
            return false;
        if (password == null) {
            return other.password == null;
        } else return password.equals(other.password);
    }

    @Override
    public String toString() {
        return "Student [email=" + email + ", Name=" + name + ", password="
                + password + "]";
    }
}
