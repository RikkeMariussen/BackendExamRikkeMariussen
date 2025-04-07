#Exam Spring 2025, 3. semester Datamatiker - Rikke Mariussen, RM225

# Table of questions

| Task  | Topic                   | Distribution | Status                          |
|:------|:------------------------|:------------:|:--------------------------------|
| 1     | Setup                   |      5%      | Implemented                     |
| 2     | JPA and DAOs            |     25%      | Implemented                     |
| 3     | REST Service provider   |     25%      | Implemented                     |
| 4     | REST Error Handling     |      5%      | Implemented                     |
| 5     | Streams                 |     10%      | Implemented                     |
| 6     | Getting additional data |     15%      | Not implemented                 |
| 7     | Testing REST endpoints  |     15%      | Almost implemented              |
| 8     | Security                |      5%      | Not implemented                 |
| Total |                         |     105%     | Ca. 70-80% in total implemented |


**Overview of routes**

| Method | Route                                             |                          Description                          | dev.HTTP checked                                                      |
|:-------|:--------------------------------------------------|:-------------------------------------------------------------:|:----------------------------------------------------------------------|
| GET    | /skilessons                                       |                      Get all ski lessons                      | Checked                                                               |
| GET    | /skilessons/{id}                                  |                     Get ski lesson by ID                      | Checked                                                               |
| POST   | /skilessons                                       |         Create a new ski lesson (without instructor).         | Checked                                                               |
| PUT    | /skilessons/{id}                                  |            Update information about a ski lesson.             | Checked                                                               |
| DELETE | /skilessons/{id}                                  |                     Delete a ski lesson.                      | Checked                                                               |
| PUT    | /skilessons/{lessonId}/instructors/{instructorId} |     Add an existing instructor to an existing ski lesson.     | Only shows the lesson that got an instructor, and not the instructor. |
| POST   | /skilessons/populate                              |    Populate the database with ski lessons and instructors.    | Checked - populates instructors and lessons                           |
| GET    | /skilessons/filter/{category}                     |             Filters the skilessons by their level             | Checked                                                               |
| GET    | /skilessons/instructors/totalPrice                | Output is the instructors id and their total price of lessons | Checked                                                               |

**Task 1: Setup**
I called my database for exam_rikke_mariussen, but that is on localHost.
Remember to set up config.properties with your own details:
DB_NAME=exam_rikke_mariussen
DB_USERNAME=<username>
DB_PASSWORD=<password>

Link til github:
https://github.com/RikkeMariussen/BackendExamRikkeMariussen

**Task 2: JPA and DAOs**
2.2 Implement a SkiLesson entity class with the following properties: starttime, endtime, location (latitude,
longitude), name, price, id, level. Use an enum for the level of the ski lesson.
- In regards to this, I have chosen to make two variables; longtitude and latitude. 
- I have chosen to do this, as that is what I am the most comfortable with, another way of doing it, could be to make a location entity, which has the two variables, and then the SkiLesson has a relation to that entity. But when making the entities later on in REST, I just feel more comfortable with the two variables directly in the SkiLesson entity.

2.4 Implement the DAOs for SkiLesson and Instructor.
- I have done this but not to my satisfaction, as there is not any "catch"es for my try's. This is not optimal, as any errors regarding these are not caught. This could be done with ApiException, but in order for this to work with my current apiException, the methods needs to throw exceptions, and that is not good within DAO methods

2.5 Create a Populator class and populate the database with ski lessons and their instructors. 
- My Populator class is located in my utils package
- I do not connect my instructors and lessons in the populator, as I had issues with recursion when filling the database.
- Therefore, in order to have instructors added to lessons, one must use the dev.HTTP where you add a lesson to an instructor

**Task 3: Building a REST Service Provider with Javalin**
3.3.5 Theoretical question
We use a PUT method instead of POST, because it updates the resources, instead of creating a new one resource, in accordance with REST api

**Task 4: REST Error Handling**
In my controller methods I use validation, e.g.  Long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid id").get();
Which originates in my DAO-classes:
e.g.
public boolean validatePrimaryKey(Long id) {
try (EntityManager em = emf.createEntityManager()) {
SkiLesson skiLesson = em.find(SkiLesson.class, id);
return skiLesson != null;
}
}

**Task 5: Streams and queries**

**Task 6: Getting Additional Data from API (15%)**
I did not have time to implement this task

**Task 7: Testing REST Endpoints**
When run together, only three out of seven passes, but when run individually, they all pass.

Test methods (excl. beforeALL, each etc.):
lessonByIdTest
createLessonTest
updateSkiLessonTest
deleteLessonTest - As I do not send a response back, the statuscode here is 204.
addInstructorToLessonTest - In this method I sent back a SkiLessonDTO, which means that in the test, I need to assert that the skiLessonId is equal to id, and instructorId is equal to instructorDTO.id, which is found in the SkiLessonDTO "object".
filterLessonsByLevelTest - As the levels at Enums, the test needs to assert that the level we filter on is in uppercase and not lowercase, as is inputed in the URL
totalPriceForLessonsByInstructorTest - Here it is important to not that the total price is delivered as a list and converted into floats.

7.5 Test the "ski lesson by id" endpoint to verify that the packing items are returned.
I do not understand this questions, so I have not fulfilled whole task 7.

**Task 8: Security**
I did not have time to implement this task