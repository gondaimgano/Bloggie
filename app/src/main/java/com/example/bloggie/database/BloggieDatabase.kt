package com.example.bloggie.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Upsert


@Entity
data class Favorite(
    @PrimaryKey val uid: Int,
    @ColumnInfo val title: String?,
   @ColumnInfo val body: String?
)



@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
   suspend fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE uid = :uid")
   suspend fun findById(uid: Int):Favorite

    @Upsert
   suspend fun save(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE uid = :uid")
  suspend  fun deleteById(uid: Int)
}

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class BloggieDatabase:RoomDatabase(){
  abstract fun favorites():FavoriteDao
}
