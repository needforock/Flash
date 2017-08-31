package ve.needforock.flash.views.main.drawer;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.Map;

import ve.needforock.flash.R;
import ve.needforock.flash.data.CurrentUser;
import ve.needforock.flash.views.login.LoginActivity;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment implements PhotoCallBack {

    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 40;
    private MagicalPermissions magicalPermissions;
    private MagicalCamera magicalCamera;
    CircularImageView circularImageView;

    public DrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        /*FirebaseUser currentUser = new CurrentUser().getCurrentUser();

        Uri userImageUri = currentUser.getPhotoUrl();
        if(userImageUri!=null){
            Picasso.with(getContext()).load(userImageUri).into(circularImageView);
        }*/


        TextView signOut = (TextView) view.findViewById(R.id.logoutTv);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(getContext(), LoginActivity.class));
                                getActivity().finish();
                            }

                        });

            }
        });

        TextView email = (TextView) view.findViewById(R.id.emailTv);
        email.setText(new CurrentUser().userEmail());

        String[] permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

        };
        magicalPermissions = new MagicalPermissions(this, permissions);
        magicalCamera = new MagicalCamera(getActivity(), RESIZE_PHOTO_PIXELS_PERCENTAGE, magicalPermissions);

        circularImageView = (CircularImageView) view.findViewById(R.id.avatarCi);

        new PhotoValidation(getContext(), this).validate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Map<String, Boolean> map = magicalPermissions.permissionResult(requestCode, permissions, grantResults);
        for (String permission : map.keySet()) {
            Log.d("PERMISSIONS", permission + " was: " + map.get(permission));
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        magicalCamera.resultPhoto(requestCode, resultCode, data);
        magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_270);

        if (RESULT_OK == resultCode) {

            Bitmap photo = magicalCamera.getPhoto();
            String path = magicalCamera.savePhotoInMemoryDevice(photo, "avatar", "myDirectoryName", MagicalCamera.JPEG, true);
            path = "file://" + path;
            Toast.makeText(getContext(), "result ok", Toast.LENGTH_SHORT).show();
            setPhoto(path);
            new UploadPhoto(getContext()).toFirebase(path);

        } else {
            Toast.makeText(getContext(), "result no ok", Toast.LENGTH_SHORT).show();
            requestSelfie();
        }

    }


    private void requestSelfie() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Selfie")
                .setMessage("Para completar el registro debes tener una selfie actualizada")
                .setCancelable(false)
                .setPositiveButton("Selfie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        magicalCamera.takeFragmentPhoto(DrawerFragment.this);
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void setPhoto(String url) {
        Picasso.with(getContext()).load(url).centerCrop().fit().into(circularImageView);
    }

    @Override
    public void emptyPhoto() {
        requestSelfie();
    }

    @Override
    public void photoAvailable(String url) {
        setPhoto(url);
    }
}
