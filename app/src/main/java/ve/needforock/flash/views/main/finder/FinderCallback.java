package ve.needforock.flash.views.main.finder;

/**
 * Created by Soporte on 05-Sep-17.
 */

public interface FinderCallback {

    void error(String error);
    void success();
    void notFound();
}
