package com.example.dogappusingjepack.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface DogDao {

    @Insert
    List<Long> insertAll(DogBreed...dogs);

    @Query("SELECT * FROM dogbreed where uUid = :dog_id")
    DogBreed getDogs(int dog_id);

    @Query("DELETE FROM dogbreed")
    void deleteAllDogs();

    @Query("SELECT * FROM dogbreed")
    List<DogBreed> getAllDogs();
}
