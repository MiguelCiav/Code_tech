package controllers;

import javax.security.auth.login.LoginContext;
import javax.swing.JTextArea;

import java.awt.Dimension;

import views.swingComponents.JFramePopUp;
import views.swingComponents.WrongPassword;
import views.useCaseFrames.LoginFrame;
import views.useCaseFrames.TestListView;

public class LogInController{
    private static LogInController instance;

    private LogInController(){
    }

    public static void validateUser(String email, String password){

        if(JSONReader.getInstance().readUser(email, password)){
            JSONReader.getInstance().readCourses();
            new TestListView();
            LoginFrame.getInstance().disposeFrame();
        } else {
            new JFramePopUp(new WrongPassword(), new Dimension(650,300));
        }

    }

    public static LogInController getInstance(){
        if(instance == null){
            instance = new LogInController();
        }

        return instance;
    }
}
