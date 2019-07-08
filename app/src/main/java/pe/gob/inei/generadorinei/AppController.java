package pe.gob.inei.generadorinei;

import android.app.Application;
import android.os.Environment;

import org.greenrobot.greendao.database.Database;

import java.io.File;

import pe.gob.inei.generadorinei.greendao.DaoMaster;
import pe.gob.inei.generadorinei.greendao.DaoSession;


public class AppController extends Application {

    public static final boolean ENCRYPTED = true;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDB() {
        File path = new File(Environment.getExternalStorageDirectory(), "GreenDao/encuesta-db");
        path.getParentFile().mkdirs();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, path.getAbsolutePath(), null);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

}
