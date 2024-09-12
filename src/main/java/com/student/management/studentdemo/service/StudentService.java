package com.student.management.studentdemo.service;

import com.student.management.studentdemo.entity.Student;
import com.student.management.studentdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Fully reactive createStudent method
    public Mono<Student> createStudent(Student student) {
        return studentRepository.save(student);  // Non-blocking reactive save
    }

    // Fully reactive getStudentById method
    public Mono<Student> getStudentById(Long id) {
        return studentRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Student not found")));  // Non-blocking reactive find
    }

    // Fully reactive getAllStudents method
    public Flux<Student> getAllStudents() {
        return studentRepository.findAll();  // Non-blocking reactive findAll
    }

    // Fully reactive updateStudent method
    public Mono<Student> updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id)
                .flatMap(existingStudent -> {
                    existingStudent.setFirstName(updatedStudent.getFirstName());
                    existingStudent.setLastName(updatedStudent.getLastName());
                    existingStudent.setEmail(updatedStudent.getEmail());
                    existingStudent.setAge(updatedStudent.getAge());
                    return studentRepository.save(existingStudent);  // Non-blocking reactive save
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Student not found")));
    }

    // Fully reactive deleteStudent method
    public Mono<Void> deleteStudent(Long id) {
        return studentRepository.findById(id)
                .flatMap(studentRepository::delete)  // Non-blocking reactive delete
                .switchIfEmpty(Mono.error(new RuntimeException("Student not found")));
    }
}
