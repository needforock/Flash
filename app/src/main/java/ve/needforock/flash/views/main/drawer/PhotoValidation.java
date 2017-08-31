package ve.needforock.flash.views.main.drawer;

import android.content.Context;

import ve.needforock.flash.data.PhotoPreference;

/**
 * Created by Soporte on 30-Aug-17.
 */

public class PhotoValidation {

    private Context context;
    private PhotoCallBack photoCallBack;

    public PhotoValidation(Context context, PhotoCallBack photoCallBack) {
        this.context = context;
        this.photoCallBack = photoCallBack;
    }

    public void validate() {
        String url = new PhotoPreference(context).getPhoto();
        if (url != null) {
            photoCallBack.photoAvailable(url);
        } else {
            photoCallBack.emptyPhoto();
        }

    }

}
