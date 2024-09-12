package com.student.management.studentdemo.controller;

import com.student.management.studentdemo.entity.Student;
import com.student.management.studentdemo.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Corrected: Return Mono<ResponseEntity<Student>> to handle HTTP status
    @PostMapping
    public Mono<ResponseEntity<Student>> createStudent(@RequestBody Student student) {
        return studentService.createStudent(student)
                .map(ResponseEntity::ok)  // Return 200 OK with saved student
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).build()));  // Handle errors
    }

    // Get student by ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Student>> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());  // Handle 404 if student not found
    }

    // Get all students
    @GetMapping
    public Flux<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Update student
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Student>> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student)
                .map(ResponseEntity::ok)  // Return 200 OK with updated student
                .defaultIfEmpty(ResponseEntity.notFound().build());  // Handle 404 if student not found
    }

    // Delete student
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id).map(deleted -> ResponseEntity.noContent().build()) ; // Return 204 No Content on successful delete.defaultIfEmpty(ResponseEntity.notFound().build());  // Handle 404 if student not found
    }
}
