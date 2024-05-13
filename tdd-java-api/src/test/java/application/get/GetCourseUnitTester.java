package application.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import domain.Course;
import domain.CourseRepository;
import domain.InvalidArgumentException;
import infrastructure.InMemoryCourseRepository;

// Randomize JUNIT tests
// Each test suite will be random among different JVM
// But the randomization will be the same within each JVM once executed
@TestMethodOrder(MethodOrderer.Random.class)
public class GetCourseUnitTester {

	@Test
	void it_should_get_an_existing_course_by_id() {
		CourseRepository inMemoryMock = this.givenAnInMemoryMock();
		
		CourseGetter getCourse = new CourseGetter(inMemoryMock);
		// Specify the action and result of calling a mock method
		Course course = null; 
		try {
			course = Course.createFromPrimitives("a87df656-c710-416d-81b6-fe341c2589e8", "Course title");
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		when(inMemoryMock.getCourse("a87df656-c710-416d-81b6-fe341c2589e8")).thenReturn(Optional.of(course));

		// Car to receive compared to the one the repo returns
		Course toRetrieve = null; 
		try {
			toRetrieve = Course.createFromPrimitives("a87df656-c710-416d-81b6-fe341c2589e8", "Course title");
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Course retreieved = getCourse.getCourseById("a87df656-c710-416d-81b6-fe341c2589e8");
		assertEquals(toRetrieve, retreieved);
	}

	@Test
	void it_should_not_get_an_inexistent_course() {
		// Mock the CourseRepository
		CourseRepository inMemoryMock = Mockito.mock(InMemoryCourseRepository.class);
		// Dependency Inversion for the repository using mocks
		CourseGetter getCourse = new CourseGetter(inMemoryMock);
		
		when(inMemoryMock.getCourse("fakeId")).thenReturn(Optional.empty());
		Course retrieved = getCourse.getCourseById("fakeId");
		assertEquals(null, retrieved);
	}

	
	private CourseRepository givenAnInMemoryMock() {
		return Mockito.mock(InMemoryCourseRepository.class);
	}

}
