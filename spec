openapi: 3.0.1
info:
  title: OpenAPI definition
  version: v0
servers:
- url: http://localhost:8080/
  description: Generated server url
paths:
  /{id}:
    get:
      tags:
      - student-controller
      operationId: getStudentById
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Student'
    put:
      tags:
      - student-controller
      operationId: updateStudentName
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      - name: newFaculty
        in: query
        required: true
        schema:
          type: string
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
  /teacher/edit:
    put:
      tags:
      - teacher-controller
      operationId: editTeacher
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Teacher'
        required: true
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Teacher'
  /teacher/editAcademicDegree/{id}:
    put:
      tags:
      - teacher-controller
      operationId: editTeacher_1
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      requestBody:
        content:
          application/json:
            schema:
              type: string
        required: true
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                type: string
  /courses/{id}:
    get:
      tags:
      - course-controller
      operationId: getCourseById
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Course'
    put:
      tags:
      - course-controller
      operationId: updateCourseName
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      - name: newName
        in: query
        required: true
        schema:
          type: string
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
    delete:
      tags:
      - course-controller
      operationId: deleteCourse
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
  /courses/{id}/groups:
    get:
      tags:
      - course-controller
      operationId: getAllGroupsForCourse
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/GroupCourse'
    put:
      tags:
      - course-controller
      operationId: updateNumberOfGroups
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      - name: newNumberOfGroups
        in: query
        required: true
        schema:
          type: string
          format: byte
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
    post:
      tags:
      - course-controller
      operationId: addGroupsToCourse
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      - name: numberOfGroups
        in: query
        required: true
        schema:
          type: string
          format: byte
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/GroupCourse'
    delete:
      tags:
      - course-controller
      operationId: deleteGroupsForCourse
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
  /:
    get:
      tags:
      - student-controller
      operationId: getAllStudents
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Student'
    post:
      tags:
      - student-controller
      operationId: addStudent
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Student'
        required: true
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Student'
  /teacher/add:
    post:
      tags:
      - teacher-controller
      operationId: addTeacher
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Teacher'
        required: true
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Teacher'
  /courses:
    get:
      tags:
      - course-controller
      operationId: getAllCourses
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Course'
    post:
      tags:
      - course-controller
      operationId: addCourse
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Course'
        required: true
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Course'
  /admins/add:
    post:
      tags:
      - admin-controller
      operationId: addAdmin
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Admin'
        required: true
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Admin'
  /teacher:
    get:
      tags:
      - teacher-controller
      operationId: getTeachers
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Teacher'
  /teacher/{id}:
    get:
      tags:
      - teacher-controller
      operationId: getTeacher
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Teacher'
  /admins:
    get:
      tags:
      - admin-controller
      operationId: getAllAdmins
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Admin'
  /admins/{id}:
    get:
      tags:
      - admin-controller
      operationId: getAdminById
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/Admin'
  /teacher/remove/{id}:
    delete:
      tags:
      - teacher-controller
      operationId: removeTeacher
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                type: string
  /student/delete/{id}:
    delete:
      tags:
      - student-controller
      operationId: deleteStudent
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
          content:
            '*/*':
              schema:
                type: string
  /admins/remove/{id}:
    delete:
      tags:
      - admin-controller
      operationId: deleteAdmin
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: integer
          format: int64
      responses:
        "404":
          description: Not Found
          content:
            '*/*':
              schema:
                type: string
        "500":
          description: Internal Server Error
          content:
            '*/*':
              schema:
                type: string
        "400":
          description: Bad Request
          content:
            '*/*':
              schema:
                type: string
        "200":
          description: OK
components:
  schemas:
    Course:
      required:
      - name
      type: object
      properties:
        id:
          type: integer
          format: int64
        name:
          type: string
    GroupCourse:
      required:
      - teachers
      type: object
      properties:
        id:
          type: integer
          format: int64
        course:
          $ref: '#/components/schemas/Course'
        groupNum:
          type: string
          format: byte
        students:
          uniqueItems: true
          type: array
          items:
            $ref: '#/components/schemas/Student'
        teachers:
          uniqueItems: true
          type: array
          items:
            $ref: '#/components/schemas/Teacher'
    Student:
      required:
      - email
      - firstName
      - lastName
      - password
      - role
      type: object
      properties:
        userId:
          type: integer
          format: int64
        email:
          type: string
        password:
          type: string
        firstName:
          type: string
        lastName:
          type: string
        role:
          type: string
        authorized:
          type: boolean
        studTicketSeries:
          type: string
        faculty:
          type: string
        specialty:
          type: string
        yearAdmission:
          type: integer
          format: int32
        groupCourse:
          uniqueItems: true
          type: array
          items:
            $ref: '#/components/schemas/GroupCourse'
    Teacher:
      required:
      - academicDegree
      - department
      - email
      - firstName
      - lastName
      - password
      - role
      type: object
      properties:
        userId:
          type: integer
          format: int64
        email:
          type: string
        password:
          type: string
        firstName:
          type: string
        lastName:
          type: string
        role:
          type: string
        authorized:
          type: boolean
        academicDegree:
          type: string
        department:
          type: string
        groupCourse:
          uniqueItems: true
          type: array
          items:
            $ref: '#/components/schemas/GroupCourse'
    Admin:
      required:
      - email
      - firstName
      - lastName
      - password
      - role
      type: object
      properties:
        userId:
          type: integer
          format: int64
        email:
          type: string
        password:
          type: string
        firstName:
          type: string
        lastName:
          type: string
        role:
          type: string
        authorized:
          type: boolean
