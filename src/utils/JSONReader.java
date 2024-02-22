package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import main.models.Answer;
import main.models.Course;
import main.models.Question;
import main.models.Test;
import main.models.User;

public class JSONReader {

    private static JSONReader instance;
    private JSONArray[] list = new JSONArray[4];
    private String actualCourseID;
    private String actualTestID;
    private String actualQuestionID;
    
    private JSONReader(){}

    public static JSONReader getInstance(){

        if(instance == null){
            instance = new JSONReader();
        }

        return instance;

    }

    public void readCourses(){

        System.out.println("INICIANDO CARGA DE DATOS DE CURSOS: \n");

        readFile(PathManager.getInstance().getStringURL("/src/data/Courses.json"), 0);
        
        for(Object object : list[0]){

            JSONObject course = (JSONObject) object;
            String courseName = (String) course.get("courseName");
            actualCourseID = (String) course.get("courseID");

            Course.setInstanceCourse(courseName, actualCourseID);

            readTest();

        }

        System.out.println("\nCARGA DE DATOS DE CURSOS FINALIZADA");

    }

    @SuppressWarnings("unused")
    public void readTest(){

        readFile(PathManager.getInstance().getStringURL("/src/data/Tests.json"), 1);

        for(Object object : list[1]){

            JSONObject test = (JSONObject) object;
            actualTestID = (String) test.get("testID");
            String courseTestID = (String) test.get("courseID");

            if(courseTestID.equals(actualCourseID)){

                String testName = (String) test.get("testName");
                String type = (String) test.get("type");
                int duration = Integer.valueOf(test.get("duration").toString());
                Test loadedTest = new Test(testName,type,duration,actualTestID);

                Course.loadTest(actualCourseID, loadedTest);
                readQuestions();

            }
        }

    }

    private void readQuestions(){

        readFile(PathManager.getInstance().getStringURL("/src/data/Questions.json"), 2);

        for(Object object : list[2]){

            JSONObject question = (JSONObject) object;
            actualQuestionID = (String) question.get("questionID");
            String questionTestID = (String) question.get("testID");
            String questionDescription = (String) question.get("description");
            String questionType = (String) question.get("questionType");

            if(questionTestID.equals(actualTestID)){

                Question loadedQuestion = new Question(questionDescription,Integer.valueOf(questionType),actualQuestionID,questionTestID);

                System.out.println("Pregunta " + loadedQuestion.getQuestionID() + " Creada");
                
                Course.loadQuestion(actualCourseID, questionTestID, loadedQuestion);
                readAnswers();

            }
        }

    }

    private void readAnswers(){

        readFile(PathManager.getInstance().getStringURL("/src/data/Answers.json"), 3);

        for(Object object : list[3]){

            JSONObject answer = (JSONObject) object;
            String answerID = (String) answer.get("answerID");
            String answerQuestionID = (String) answer.get("questionID");
            boolean isCorrect = (boolean) answer.get("isCorrect");
            String answerType = (String) answer.get("answerType");
            String answerText = (String) answer.get("answerText");
            String justification = (String) answer.get("justification");

            if(answerQuestionID.equals(actualQuestionID)){
                
                Answer loadedAnswer = new Answer(answerText,Integer.valueOf(answerType),isCorrect,answerID,answerQuestionID,justification);

                System.out.println("Respuesta " + loadedAnswer.getAnswerID() + " Creada");

                Course.loadAnswer(actualCourseID, actualTestID, actualQuestionID, loadedAnswer);
                
            }
        }

    }

    public boolean readUser(String email, String password){

        readFile(PathManager.getInstance().getStringURL("/src/data/Users.json"),0);
        
        for(Object object : list[0]){

            JSONObject user = (JSONObject) object;
            String userEmail = (String) user.get("email");
            String userPassword = (String) user.get("password");

            if(userEmail.equals(email) && userPassword.equals(password)){

                User.getUserInstance().seteMail(userEmail);
                User.getUserInstance().setPassword(userPassword);

                String name = (String) user.get("name");
                User.getUserInstance().setName(name);

                String lastname = (String) user.get("lastname");
                User.getUserInstance().setLastName(lastname);

                JSONArray approvedCourses = (JSONArray) user.get("approvedCourses");
                readApprovedCourses(approvedCourses);

                return true;

            }

        }

        return false;
    }

    private void readApprovedCourses(JSONArray approvedCourses){

        for(Object object : approvedCourses){

            String courseID = object.toString();
            User.getUserInstance().addCourse(courseID);

        }

    }

    private void readFile(String path, int listIndex){

        JSONParser jsonParser = new JSONParser();
        
        try(FileReader reader = new FileReader(path)){

            Object object = jsonParser.parse(reader);

            list[listIndex] = (JSONArray) object;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

    }

}
