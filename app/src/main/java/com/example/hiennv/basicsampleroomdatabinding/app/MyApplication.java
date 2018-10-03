package com.example.hiennv.basicsampleroomdatabinding.app;

import android.app.Application;

import com.example.hiennv.basicsampleroomdatabinding.db.DataRepository;
import com.example.hiennv.basicsampleroomdatabinding.db.MyDatabase;

public class MyApplication extends Application {
    private AppExcutors appExcutors;

    @Override
    public void onCreate() {
        super.onCreate();
        appExcutors = new AppExcutors();
    }

    public MyDatabase getDatabase(){
        return MyDatabase.getInstance(this,appExcutors);
    }

    public DataRepository getDataRepository(){
        return DataRepository.getInstance(getDatabase());
    }
}
