package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Boolean existsEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (existsEmail) {
            throw new IllegalStateException("Email already taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exist = studentRepository.existsById(studentId);
        if (!exist) {
            throw new IllegalStateException("Student wiht id " + studentId + " does not exists");
        }
        studentRepository.deleteById(studentId);

    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).
                orElseThrow(() -> new IllegalStateException(
                        "Student with id " + studentId + " does not exist"));
        if (name != null && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }
        if (email != null && !Objects.equals(student.getEmail(), email)) {
            Boolean existsEmail = studentRepository.findStudentByEmail(email);
            if (existsEmail) {
                throw new IllegalStateException("Email already taken");
            }
            student.setEmail(email);
        }
    }
}
