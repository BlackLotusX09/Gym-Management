# Gym-Management

Smart Gym Management System

Project Summary

This project is a comprehensive backend REST API designed to manage university gym operations. Built with Spring Boot, it utilizes a 
Layered Architecture (Controller, Service, Repository) and a custom file-based persistence engine using JSON. The system features complex 
logic for workout assignment, smart workout logging, algorithmic diet generation (Zig-Zag method), inventory management, and conflict-free session scheduling using constraint satisfaction logic.

# API Endpoints

User Management

Method	Endpoint	     Description
POST	  /users	       Register a new user (can include Health Attributes).
GET	    /users	       Retrieve a list of all users.
GET	    /users/{id}	   Retrieve a specific user by ID.
DELETE	/users/{id}	   Delete a user.

Workout Tracking

Method	Endpoint	        Description
GET	    /users/{id}       /workout	View the workout plan assigned by the coach.
POST	  /users/{id}/logs	Log a completed workout session (Auto-date stamped).
POST	  /users/{id}/logs-assigned	Log a session based on the assigned plan (only inputting reps/sets changed).

Coach & Assignments

Method	Endpoint	                                  Description
POST	  /coaches	                                  Register a new coach.
GET	    /coaches	                                  Retrieve a list of all coaches.
POST	  /coaches/{coachId}/users/{userId}	          Assign a specific user to a coach's roster.
POST	  /coaches/{coachId}/users/{userId}/workouts	Assign a workout plan to a student.
PUT	    /coaches/{coachId}/users/{userId}/workouts	Re-assign/Update a student's workout plan.

Diet & Health

Method	Endpoint	    Description
GET	  /diet/{userId}	Generate a Zig-Zag calorie cycling schedule based on the user's health attributes.

Equipment & Inventory

Method	Endpoint	                              Description
GET	    /equipment	                            List all gym equipment.
POST	  /equipment	                            Add new equipment.
PUT	    /equipment/{id}/status?available=false	Toggle equipment status (Available vs. Under Maintenance).

Scheduling

Method	Endpoint	Description
POST	  /bookings	Book a session (runs conflict detection algorithm for Time/Coach/User availability).
GET	    /bookings	View all active bookings.

# Contributors

    Gagan Syam Reddy     : User.java,  UserRepository.java, UserService.java, UserController.java

    Sai Ranga Reddy      : Equipment.java , EquipmentRepository.java , EquipmentService.java , EquipmentController.java

    Karthik Telluri      : BookingController , SchedulerService , BookingRepository

    Sankalp Gadamsetty   : CoachController.java, Coach.java, CoachRepository.java , CoachService.java

    Miyyapuram Varun     : EquipmentController , EquipmentService , EquipmentRepository , Equipment.java

    Gangavarapu Jashwanth: DietPlan.java , DietSchedule.java , DietRoe.java , HealthAttributes.java ,
                           HealthAttributesRepository.java , HealthAttributesService.java , HealthAttributesController.java 
