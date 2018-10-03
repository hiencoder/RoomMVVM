package com.example.hiennv.basicsampleroomdatabinding.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.hiennv.basicsampleroomdatabinding.app.AppExcutors;
import com.example.hiennv.basicsampleroomdatabinding.db.converter.DateConverter;
import com.example.hiennv.basicsampleroomdatabinding.db.dao.CommentDao;
import com.example.hiennv.basicsampleroomdatabinding.db.dao.ProductDao;
import com.example.hiennv.basicsampleroomdatabinding.model.CommentEntity;
import com.example.hiennv.basicsampleroomdatabinding.model.ProductEntity;

import java.util.List;

@Database(entities = {ProductEntity.class, CommentEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class MyDatabase extends RoomDatabase {
    private static MyDatabase sMyDatabase;

    //Database name
    @VisibleForTesting
    public static final String DATABASE_NAME = "sample_db";

    public abstract ProductDao productDao();

    public abstract CommentDao commentDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static MyDatabase getInstance(final Context context, final AppExcutors appExcutors){
        if (sMyDatabase == null){
            synchronized (MyDatabase.class){
                if (sMyDatabase == null){
                    sMyDatabase = buildDatabase(context,appExcutors);
                    sMyDatabase.updateDatabaseCreated(context);
                }
            }
        }
        return sMyDatabase;
    }

    private static MyDatabase buildDatabase(Context context, AppExcutors appExcutors) {
        return Room.databaseBuilder(context,MyDatabase.class,DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        appExcutors.diskIO().execute(() -> {
                            //Delay
                            addDelay();
                            //Generate data
                            MyDatabase myDatabase = MyDatabase.getInstance(context,appExcutors);
                            List<ProductEntity> productEntities = DataGenerator.generateProducts();
                            List<CommentEntity> commentEntities = DataGenerator.generateCommentsForProducts(productEntities);
                            insertData(myDatabase,productEntities,commentEntities);
                            //Thong bao database da dc tao
                            myDatabase.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    /**
     * @param database
     * @param productEntities
     * @param commentEntities
     */
    private static void insertData(MyDatabase database, List<ProductEntity> productEntities, List<CommentEntity> commentEntities) {
        database.runInTransaction(() -> {
            database.productDao().insertAll(productEntities);
            database.commentDao().insertAll(commentEntities);
        });
    }

    /**
     * @param context
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            //Neu database da duoc tao
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
