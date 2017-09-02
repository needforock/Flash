package ve.needforock.flash.views.login;

import com.google.firebase.auth.FirebaseUser;

import ve.needforock.flash.data.CurrentUser;

/**
 * Created by Soporte on 25-Aug-17.
 */

public class LoginPresenter {
    LoginCallback loginCallback;

    public LoginPresenter(LoginCallback loginCallback) {
        this.loginCallback = loginCallback;
    }

    public void verification(){
        FirebaseUser currentUser = new CurrentUser().getCurrentUser();
        if (currentUser != null) {
            loginCallback.logged();
        } else {
            loginCallback.signUp();


        }
    }
}
