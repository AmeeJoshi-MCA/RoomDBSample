package com.amee.sample.roomDB.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.amee.sample.roomDB.RoomDatabase.LoginDao;
import com.amee.sample.roomDB.RoomDatabase.LoginDatabase;
import com.amee.sample.roomDB.RoomDatabase.LoginTable;

import java.util.List;

public class LoginRepository {

    private LoginDao loginDao;
    private LiveData<List<LoginTable>> allData;

    public LoginRepository(Application application) {

        LoginDatabase db = LoginDatabase.getDatabase(application);
        loginDao = db.loginDoa();
        allData = loginDao.getDetails();

    }

    public void deleteData() {
        loginDao.deleteAllData();
    }

    public LiveData<List<LoginTable>> getAllData() {
        return allData;
    }

    public void insertData(LoginTable data) {
        new LoginInsertion(loginDao).execute(data);
    }

    private static class LoginInsertion extends AsyncTask<LoginTable, Void, Void> {

        private LoginDao loginDao;

        private LoginInsertion(LoginDao loginDao) {

            this.loginDao = loginDao;

        }

        @Override
        protected Void doInBackground(LoginTable... data) {

            loginDao.deleteAllData();

            loginDao.insertDetails(data[0]);
            return null;

        }

    }

}
