package com.example.demo.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static java.time.Month.APRIL;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void isGetStudents() {
        underTest.getStudents();
        verify(studentRepository).findAll();
    }

    @Test
    void isAddNewStudent() {
        Student student = new Student("Mehmet", "mehmet@gmail.com",
                LocalDate.of(2000, APRIL, 10));

        underTest.addNewStudent(student);
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        assertThat(studentArgumentCaptor.getValue()).isEqualTo(student);
    }

    @Test
    void isNotAddNewStudent() {

        Student student = new Student("Mehmet", "mehmet@gmail.com",
                LocalDate.of(2000, APRIL, 10));

        when(studentRepository.findStudentByEmail(student.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> underTest.addNewStudent(student))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("Email already taken");

        verify(studentRepository, never()).save(any());

    }
}