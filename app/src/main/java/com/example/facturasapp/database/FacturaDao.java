package com.example.facturasapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.facturasapp.model.FacturaVO;

import java.util.List;

@Dao
public interface FacturaDao {

    @Insert
    void insert(List<FacturaVO> listaFacturas);

    @Update
    void update(FacturaVO factura);

    @Delete
    void delete(FacturaVO factura);

    @Query("SELECT * FROM factura")
    List<FacturaVO> select();

    @Query("DELETE FROM factura")
    void deleteAll();

}
