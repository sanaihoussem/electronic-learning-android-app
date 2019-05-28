package com.sim.treasity;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by bechirbelkahla on 11/20/17.
 */

public class Constants extends Application {

    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    //Mobile validation pattern
    public static  final String mobregEx="^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$";


    private String tokenUser;
    private String IdConnectedUser;
    private String idQuestionSaved;

    public String getTokenUser() {
        return tokenUser;
    }

    public void setIdConnectedUser(String someVariableUser) {
        this.IdConnectedUser = someVariableUser;
    }

    public String getIdConnectedUser() {
        return IdConnectedUser;
    }

    public void setidQuestionSaved(String someVariableUser) {
        this.idQuestionSaved = someVariableUser;
    }

    public String getidQuestionSaved() {
        return idQuestionSaved;
    }

    public void setTokenUser(String someVariableUser) {
        this.tokenUser = someVariableUser;
    }

//

    public static boolean isOnline(Context applicationContext) {

        ConnectivityManager cm = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting())
            return true;
        else
            return false;

    }

}
