package ve.needforock.flash.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Soporte on 22-Aug-17.
 */

public class CurrentUser {

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }
    public String userEmail(){
        return getCurrentUser().getEmail();
    }
}
