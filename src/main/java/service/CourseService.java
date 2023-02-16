package service;

import domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> findByMajor(Long majorId){
        return courseRepository.findByMajor(majorId);
    }

    public Course findById(Long courseId){
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Failed: No Course Info"));
    }
}
