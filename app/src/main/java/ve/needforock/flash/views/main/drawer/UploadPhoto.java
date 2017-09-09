package ve.needforock.flash.views.main.drawer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import ve.needforock.flash.data.CurrentUser;
import ve.needforock.flash.data.EmailProcessor;
import ve.needforock.flash.data.Nodes;
import ve.needforock.flash.data.PhotoPreference;
import ve.needforock.flash.models.LocalUser;

/**
 * Created by Soporte on 28-Aug-17.
 */

public class UploadPhoto {
    Context context;

    public UploadPhoto(Context context) {
        this.context = context;
    }

    public void toFirebase(String path){
        Log.d("upload", path);
        final CurrentUser currentUser = new CurrentUser();
        String folder = new EmailProcessor().sanitizedEmail(currentUser.userEmail()+"/");
        String photoName = "avatar.jpeg";

        String baseUrl = "gs://flash-8cf90.appspot.com/avatars/";
        String refUrl = baseUrl + folder + photoName;
        Log.d("upload", refUrl);

        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);
        storageRef.putFile(Uri.parse(path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests")String[] fullUrl = taskSnapshot.getDownloadUrl().toString().split("&token");
                String url = fullUrl[0];

                new PhotoPreference(context).photoSave(url);

                LocalUser user = new LocalUser();
                user.setEmail(currentUser.userEmail());
                user.setName(currentUser.getCurrentUser().getDisplayName());
                user.setPhoto(url);
                user.setUid(currentUser.getUid());
                String key = new EmailProcessor().sanitizedEmail(currentUser.userEmail());
                new Nodes().user(key).setValue(user);
            }
        });



    }
}
