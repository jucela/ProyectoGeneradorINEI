package pe.gob.inei.generadorinei.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import pe.gob.inei.generadorinei.model.pojos.componentes.PCheckbox;
import pe.gob.inei.generadorinei.model.pojos.componentes.PEditText;
import pe.gob.inei.generadorinei.model.pojos.componentes.PFormulario;
import pe.gob.inei.generadorinei.model.pojos.componentes.PGps;
import pe.gob.inei.generadorinei.model.pojos.componentes.PRadio;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPCheckbox;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPEdittext;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPFormulario;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPRadio;
import pe.gob.inei.generadorinei.model.pojos.encuesta.CamposMarco;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Encuesta;
import pe.gob.inei.generadorinei.model.pojos.encuesta.FiltrosMarco;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Flujo;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Hogar;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Info_tabla;
import pe.gob.inei.generadorinei.model.pojos.encuesta.ItemMarco;
import pe.gob.inei.generadorinei.model.pojos.encuesta.ItemMarco_hogar;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Marco;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Modulo;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Pagina;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Pregunta;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Residente;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Usuario;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Variable;
import pe.gob.inei.generadorinei.util.Herramientas;
import pe.gob.inei.generadorinei.util.TipoComponente;

public class DAOEncuesta {
    Context contexto;
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    public DAOEncuesta(Context contexto){
        this.contexto = contexto;
        sqLiteOpenHelper = new DBHelper(contexto);
    }

    public DAOEncuesta(Context contexto, int flag) throws IOException {
        this.contexto = contexto;
        sqLiteOpenHelper = new DBHelper(contexto);
        createDataBase();
    }

    public DAOEncuesta(Context contexto, String inputPath) throws IOException {
        this.contexto = contexto;
        sqLiteOpenHelper = new DBHelper(contexto);
        createDataBase(inputPath);
    }


    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(!dbExist){
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
            sqLiteDatabase.close();
            try{
                copyDataBase();
                sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
//                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CARATULA);

                sqLiteDatabase.close();
            }catch (IOException e){
                throw new Error("Error: copiando base de datos");
            }
        }

    }


    public void createDataBase(String inputPath) throws IOException {
        boolean dbExist = checkDataBase();
        if(dbExist){
            File dbFile = new File(SQLConstantes.DB_PATH + SQLConstantes.DB_NAME);
            SQLiteDatabase.deleteDatabase(dbFile);
        }
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase.close();
        try{
            copyDataBase(inputPath);
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
//            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CARATULA);

            sqLiteDatabase.close();
        }catch (IOException e){
            throw new Error("Error: copiando base de datos");
        }
    }


    public void copyDataBase() throws IOException{
        InputStream myInput = contexto.getAssets().open(SQLConstantes.DB_NAME);
        String outFileName = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) != -1){
            if (length > 0){
                myOutput.write(buffer,0,length);
            }
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();
    }


    public void copyDataBase(String inputPath) throws IOException{
        InputStream myInput = new FileInputStream(inputPath);
        String outFileName = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) != -1){
            if (length > 0){
                myOutput.write(buffer,0,length);
            }
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();

    }

    public void open() throws SQLException {
        String myPath = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close(){
        if(sqLiteDatabase != null){
            sqLiteDatabase.close();
        }
    }

    public boolean checkDataBase(){
        try{
            String myPath = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
            Log.e("myPath",""+myPath);
            sqLiteDatabase = SQLiteDatabase.openDatabase(myPath,null, SQLiteDatabase.OPEN_READWRITE);
        }catch (Exception e){
            File dbFile = new File(SQLConstantes.DB_PATH + SQLConstantes.DB_NAME);
            return dbFile.exists();
        }
        if (sqLiteDatabase != null) sqLiteDatabase.close();

        return sqLiteDatabase != null ? true : false;
    }

    public Encuesta getEncuesta(){
        open();
        Encuesta encuesta = new Encuesta();
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaencuestas,
                    null, SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                encuesta.setTipo(cursor.getString(cursor.getColumnIndex(SQLConstantes.encuestas_tipo)));
                encuesta.setTitulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.encuestas_titulo)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return encuesta;
    }


    public Usuario getUsuario(String idUsuario){
        open();
        Usuario usuario = new Usuario();
        String[] whereArgs = new String[]{idUsuario};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablausuarios,
                    null, SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                usuario.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_id)));
                usuario.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_dni)));
                usuario.setNombre(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_nombre)));
                usuario.setClave(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_clave)));
                usuario.setCargo_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_cargo_id)));
                usuario.setCoordinador_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_coordinador_id)));
                usuario.setSupervisor_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_supervisor_id)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return usuario;
    }


    public Marco getMarco(String idMarco){
        open();
        Marco marco = new Marco();
        String[] whereArgs = new String[]{idMarco};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null, SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                marco.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setAnio(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_anio)));
                marco.setMes(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_mes)));
                marco.setPeriodo(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_periodo)));
                marco.setZona(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_zona)));
                marco.setConglomerado(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_conglomerado)));
                marco.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ccdd)));
                marco.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_departamento)));
                marco.setCcpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ccpp)));
                marco.setProvincia(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_provincia)));
                marco.setCcdi(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ccdi)));
                marco.setDistrito(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_distrito)));
                marco.setCodccpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_codccpp)));
                marco.setNomccpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_nomccpp)));
                marco.setRuc(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ruc)));
                marco.setRazon_social(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_razon_social)));
                marco.setNombre_comercial(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_nombre_comercial)));
                marco.setTipo_empresa(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_tipo_empresa)));
                marco.setEncuestador(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_encuestador)));
                marco.setSupervisor(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_supervisor)));
                marco.setCoordinador(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_coordinador)));
                marco.setFrente(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_frente)));
                marco.setNumero_orden(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_numero_orden)));
                marco.setManzana_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_manzana_id)));
                marco.setTipo_via(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_tipo_via)));
                marco.setNombre_via(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_nombre_via)));
                marco.setPuerta(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_puerta)));
                marco.setLote(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_lote)));
                marco.setPiso(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_piso)));
                marco.setManzana(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_manzana)));
                marco.setBlock(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_block)));
                marco.setInterior(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_interior)));
                marco.setEstado(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_estado)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marco;
    }

    public ArrayList<Marco> getAllMarco(String idUsuario){
        open();
        ArrayList<Marco> marcos = new ArrayList<>();
        String[] whereArgs = new String[]{idUsuario};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null, SQLConstantes.clausula_where_encuestador,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                Marco marco = new Marco();
                marco.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setAnio(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_anio)));
                marco.setMes(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_mes)));
                marco.setPeriodo(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_periodo)));
                marco.setZona(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_zona)));
                marco.setConglomerado(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_conglomerado)));
                marco.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ccdd)));
                marco.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_departamento)));
                marco.setCcpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ccpp)));
                marco.setProvincia(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_provincia)));
                marco.setCcdi(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ccdi)));
                marco.setDistrito(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_distrito)));
                marco.setCodccpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_codccpp)));
                marco.setNomccpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_nomccpp)));
                marco.setRuc(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ruc)));
                marco.setRazon_social(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_razon_social)));
                marco.setNombre_comercial(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_nombre_comercial)));
                marco.setTipo_empresa(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_tipo_empresa)));
                marco.setEncuestador(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_encuestador)));
                marco.setSupervisor(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_supervisor)));
                marco.setCoordinador(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_coordinador)));
                marco.setFrente(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_frente)));
                marco.setNumero_orden(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_numero_orden)));
                marco.setManzana_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_manzana_id)));
                marco.setTipo_via(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_tipo_via)));
                marco.setNombre_via(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_nombre_via)));
                marco.setPuerta(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_puerta)));
                marco.setLote(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_lote)));
                marco.setPiso(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_piso)));
                marco.setManzana(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_manzana)));
                marco.setBlock(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_block)));
                marco.setInterior(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_interior)));
                marco.setEstado(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_estado)));
                marcos.add(marco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marcos;
    }

    public ArrayList<ItemMarco> getAllItemsMarco(String idUsuario, CamposMarco camposMarco){
        open();
        ArrayList<ItemMarco> marcos = new ArrayList<>();
        String[] whereArgs = new String[]{idUsuario};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null, SQLConstantes.clausula_where_encuestador,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco itemMarco = new ItemMarco();
                if (!camposMarco.getVar1().equals("")) itemMarco.setCampo1(cursor.getString(cursor.getColumnIndex(camposMarco.getVar1())));
                if (!camposMarco.getVar2().equals("")) itemMarco.setCampo2(cursor.getString(cursor.getColumnIndex(camposMarco.getVar2())));
                if (!camposMarco.getVar3().equals("")) itemMarco.setCampo3(cursor.getString(cursor.getColumnIndex(camposMarco.getVar3())));
                if (!camposMarco.getVar4().equals("")) itemMarco.setCampo4(cursor.getString(cursor.getColumnIndex(camposMarco.getVar4())));
                if (!camposMarco.getVar5().equals("")) itemMarco.setCampo5(cursor.getString(cursor.getColumnIndex(camposMarco.getVar5())));
                if (!camposMarco.getVar6().equals("")) itemMarco.setCampo6(cursor.getString(cursor.getColumnIndex(camposMarco.getVar6())));
                if (!camposMarco.getVar7().equals("")) itemMarco.setCampo7(cursor.getString(cursor.getColumnIndex(camposMarco.getVar7())));
                marcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marcos;
    }

    public ArrayList<ItemMarco> getAllItemsMarcoFiltro1(String idUsuario, CamposMarco camposMarco,String column1,String valorFiltro1){
        open();
        ArrayList<ItemMarco> marcos = new ArrayList<>();
        String[] whereArgs = new String[]{idUsuario,valorFiltro1};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null, SQLConstantes.clausula_where_encuestador + " AND " + column1+"=?",whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco itemMarco = new ItemMarco();
                if (!camposMarco.getVar1().equals("")) itemMarco.setCampo1(cursor.getString(cursor.getColumnIndex(camposMarco.getVar1())));
                if (!camposMarco.getVar2().equals("")) itemMarco.setCampo2(cursor.getString(cursor.getColumnIndex(camposMarco.getVar2())));
                if (!camposMarco.getVar3().equals("")) itemMarco.setCampo3(cursor.getString(cursor.getColumnIndex(camposMarco.getVar3())));
                if (!camposMarco.getVar4().equals("")) itemMarco.setCampo4(cursor.getString(cursor.getColumnIndex(camposMarco.getVar4())));
                if (!camposMarco.getVar5().equals("")) itemMarco.setCampo5(cursor.getString(cursor.getColumnIndex(camposMarco.getVar5())));
                if (!camposMarco.getVar6().equals("")) itemMarco.setCampo6(cursor.getString(cursor.getColumnIndex(camposMarco.getVar6())));
                if (!camposMarco.getVar7().equals("")) itemMarco.setCampo7(cursor.getString(cursor.getColumnIndex(camposMarco.getVar7())));
                marcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marcos;
    }

    public ArrayList<ItemMarco> getAllItemsMarcoFiltro2(String idUsuario, CamposMarco camposMarco,String column1,String valorFiltro1,String column2,String valorFiltro2){
        open();
        ArrayList<ItemMarco> marcos = new ArrayList<>();
        String[] whereArgs = new String[]{idUsuario,valorFiltro1,valorFiltro2};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null, SQLConstantes.clausula_where_encuestador + " AND " + column1+"=?" + " AND " + column2+"=?",whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco itemMarco = new ItemMarco();
                if (!camposMarco.getVar1().equals("")) itemMarco.setCampo1(cursor.getString(cursor.getColumnIndex(camposMarco.getVar1())));
                if (!camposMarco.getVar2().equals("")) itemMarco.setCampo2(cursor.getString(cursor.getColumnIndex(camposMarco.getVar2())));
                if (!camposMarco.getVar3().equals("")) itemMarco.setCampo3(cursor.getString(cursor.getColumnIndex(camposMarco.getVar3())));
                if (!camposMarco.getVar4().equals("")) itemMarco.setCampo4(cursor.getString(cursor.getColumnIndex(camposMarco.getVar4())));
                if (!camposMarco.getVar5().equals("")) itemMarco.setCampo5(cursor.getString(cursor.getColumnIndex(camposMarco.getVar5())));
                if (!camposMarco.getVar6().equals("")) itemMarco.setCampo6(cursor.getString(cursor.getColumnIndex(camposMarco.getVar6())));
                if (!camposMarco.getVar7().equals("")) itemMarco.setCampo7(cursor.getString(cursor.getColumnIndex(camposMarco.getVar7())));
                marcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marcos;
    }

    public ArrayList<ItemMarco> getAllItemsMarcoFiltro3(String idUsuario, CamposMarco camposMarco,String column1,String valorFiltro1,
                                                 String column2,String valorFiltro2,String column3,String valorFiltro3){
        open();
        ArrayList<ItemMarco> marcos = new ArrayList<>();
        String[] whereArgs = new String[]{idUsuario,valorFiltro1,valorFiltro2,valorFiltro3};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null, SQLConstantes.clausula_where_encuestador + " AND " + column1+"=?" + " AND " + column2+"=?" + " AND " + column3+"=?",
                    whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco itemMarco = new ItemMarco();
                if (!camposMarco.getVar1().equals("")) itemMarco.setCampo1(cursor.getString(cursor.getColumnIndex(camposMarco.getVar1())));
                if (!camposMarco.getVar2().equals("")) itemMarco.setCampo2(cursor.getString(cursor.getColumnIndex(camposMarco.getVar2())));
                if (!camposMarco.getVar3().equals("")) itemMarco.setCampo3(cursor.getString(cursor.getColumnIndex(camposMarco.getVar3())));
                if (!camposMarco.getVar4().equals("")) itemMarco.setCampo4(cursor.getString(cursor.getColumnIndex(camposMarco.getVar4())));
                if (!camposMarco.getVar5().equals("")) itemMarco.setCampo5(cursor.getString(cursor.getColumnIndex(camposMarco.getVar5())));
                if (!camposMarco.getVar6().equals("")) itemMarco.setCampo6(cursor.getString(cursor.getColumnIndex(camposMarco.getVar6())));
                if (!camposMarco.getVar7().equals("")) itemMarco.setCampo7(cursor.getString(cursor.getColumnIndex(camposMarco.getVar7())));
                marcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marcos;
    }

    public ArrayList<ItemMarco> getAllItemsMarcoFiltro4(String idUsuario, CamposMarco camposMarco,String column1,String valorFiltro1,
                                                 String column2,String valorFiltro2,String column3,String valorFiltro3,String column4,String valorFiltro4){
        open();
        ArrayList<ItemMarco> marcos = new ArrayList<>();
        String[] whereArgs = new String[]{idUsuario,valorFiltro1,valorFiltro2,valorFiltro3,valorFiltro4};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco, null,
                    SQLConstantes.clausula_where_encuestador + " AND " + column1+"=?" + " AND " + column2+"=?" + " AND " + column3+"=?" + " AND " + column4+"=?",
                    whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco itemMarco = new ItemMarco();
                if (!camposMarco.getVar1().equals("")) itemMarco.setCampo1(cursor.getString(cursor.getColumnIndex(camposMarco.getVar1())));
                if (!camposMarco.getVar2().equals("")) itemMarco.setCampo2(cursor.getString(cursor.getColumnIndex(camposMarco.getVar2())));
                if (!camposMarco.getVar3().equals("")) itemMarco.setCampo3(cursor.getString(cursor.getColumnIndex(camposMarco.getVar3())));
                if (!camposMarco.getVar4().equals("")) itemMarco.setCampo4(cursor.getString(cursor.getColumnIndex(camposMarco.getVar4())));
                if (!camposMarco.getVar5().equals("")) itemMarco.setCampo5(cursor.getString(cursor.getColumnIndex(camposMarco.getVar5())));
                if (!camposMarco.getVar6().equals("")) itemMarco.setCampo6(cursor.getString(cursor.getColumnIndex(camposMarco.getVar6())));
                if (!camposMarco.getVar7().equals("")) itemMarco.setCampo7(cursor.getString(cursor.getColumnIndex(camposMarco.getVar7())));
                marcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marcos;
    }

    public ArrayList<String> getArrayFiltro1(String idUsuario, String column){
        open();
        ArrayList<String> arrayFiltro1 = new ArrayList<>();
        arrayFiltro1.add("Seleccionar");
        String[] columns = {column};
        String[] whereArgs = new String[]{idUsuario};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(true, SQLConstantes.tablamarco,
                    columns,SQLConstantes.clausula_where_encuestador,whereArgs,null,null,column,null);
            while (cursor.moveToNext()){
                String elemento = cursor.getString(cursor.getColumnIndex(column));
                arrayFiltro1.add(elemento);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return arrayFiltro1;
    }

    public ArrayList<String> getArrayFiltro2(String idUsuario, String column1,String valorFiltro1,String column2){
        open();
        ArrayList<String> arrayFiltro2 = new ArrayList<>();
        arrayFiltro2.add("Seleccionar");
        String[] whereArgs = new String[]{idUsuario,valorFiltro1};
        String[] columns = {column2};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(true, SQLConstantes.tablamarco,
                    columns,SQLConstantes.clausula_where_encuestador + " AND "+ column1+"=?",whereArgs,null,null,column2,null);
            while (cursor.moveToNext()){
                String elemento = cursor.getString(cursor.getColumnIndex(column2));
                arrayFiltro2.add(elemento);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return arrayFiltro2;
    }

    public ArrayList<String> getArrayFiltro3(String idUsuario,String column1,String valorFiltro1,String column2,String valorFiltro2,String column3){
        open();
        ArrayList<String> arrayFiltro3 = new ArrayList<>();
        arrayFiltro3.add("Seleccionar");
        String[] whereArgs = new String[]{idUsuario,valorFiltro1,valorFiltro2};
        String[] columns = {column3};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(true, SQLConstantes.tablamarco,
                    columns,SQLConstantes.clausula_where_encuestador + " AND "+ column1+"=?" + " AND " + column2+"=?",whereArgs,null,null,column3,null);
            while (cursor.moveToNext()){
                String elemento = cursor.getString(cursor.getColumnIndex(column3));
                arrayFiltro3.add(elemento);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return arrayFiltro3;
    }

    public ArrayList<String> getArrayFiltro4(String idUsuario, String column1,String valorFiltro1,
                                             String column2,String valorFiltro2,
                                             String column3,String valorFiltro3,String column4){
        open();
        ArrayList<String> arrayFiltro4 = new ArrayList<>();
        arrayFiltro4.add("Seleccionar");
        String[] whereArgs = new String[]{idUsuario,valorFiltro1,valorFiltro2,valorFiltro3};
        String[] columns = {column4};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(true, SQLConstantes.tablamarco,
                    columns,SQLConstantes.clausula_where_encuestador + " AND "+ column1+"=?" + " AND " + column2+"=?"+ " AND " + column3+"=?",whereArgs,null,null,column4,null);
            while (cursor.moveToNext()){
                String elemento = cursor.getString(cursor.getColumnIndex(column4));
                arrayFiltro4.add(elemento);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return arrayFiltro4;
    }

    public CamposMarco getCamposMarco(){
        open();
        CamposMarco camposMarco = new CamposMarco();
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacamposmarco,
                    null, SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                camposMarco.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                camposMarco.setNombre1(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre1)));
                camposMarco.setNombre2(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre2)));
                camposMarco.setNombre3(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre3)));
                camposMarco.setNombre4(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre4)));
                camposMarco.setNombre5(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre5)));
                camposMarco.setNombre6(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre6)));
                camposMarco.setNombre7(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre7)));
                camposMarco.setPeso1(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso1)));
                camposMarco.setPeso2(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso2)));
                camposMarco.setPeso3(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso3)));
                camposMarco.setPeso4(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso4)));
                camposMarco.setPeso5(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso5)));
                camposMarco.setPeso6(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso6)));
                camposMarco.setPeso7(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso7)));
                camposMarco.setVar1(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var1)));
                camposMarco.setVar2(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var2)));
                camposMarco.setVar3(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var3)));
                camposMarco.setVar4(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var4)));
                camposMarco.setVar5(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var5)));
                camposMarco.setVar6(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var6)));
                camposMarco.setVar7(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var7)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return camposMarco;
    }

    public FiltrosMarco getFiltrosMarco(){
        open();
        FiltrosMarco filtrosMarco = new FiltrosMarco();
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablafiltrosmarco,
                    null, SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                filtrosMarco.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_id)));
                filtrosMarco.setFiltro1(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_filtro1)));
                filtrosMarco.setFiltro2(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_filtro2)));
                filtrosMarco.setFiltro3(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_filtro3)));
                filtrosMarco.setFiltro4(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_filtro4)));
                filtrosMarco.setNombre1(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_nombre1)));
                filtrosMarco.setNombre2(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_nombre2)));
                filtrosMarco.setNombre3(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_nombre3)));
                filtrosMarco.setNombre4(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_nombre4)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return filtrosMarco;
    }


    public Modulo getModulo(String numModulo,String tipoActividad){
        open();
        Modulo modulo = new Modulo();
        String[] whereArgs = new String[]{numModulo,tipoActividad};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamodulos,
                    null,SQLConstantes.clausula_where_numero_modulo +
                    " AND " + SQLConstantes.clausula_where_tipo_actividad,
                    whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                modulo.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_id)));
                modulo.setTitulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_titulo)));
                modulo.setCabecera(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_cabecera)));
                modulo.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_numero)));
                modulo.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_tipo_actividad)));
            }
        }finally {
            if(cursor != null) cursor.close();
        }
        close();
        return modulo;
    }

    public ArrayList<Modulo> getAllModulos(String tipoActividad){
        open();
        ArrayList<Modulo> modulos = new ArrayList<Modulo>();
        String[] whereArgs = new String[]{tipoActividad};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamodulos,
                    null,SQLConstantes.clausula_where_tipo_actividad_activa,
                    whereArgs,null,null,null);
            while(cursor.moveToNext()){
                Modulo modulo = new Modulo();
                modulo.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_id)));
                modulo.setTitulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_titulo)));
                modulo.setCabecera(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_cabecera)));
                modulo.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_numero)));
                modulo.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_tipo_actividad)));
                modulos.add(modulo);
            }
        }finally {
            if(cursor != null) cursor.close();
        }
        close();
        return modulos;
    }

    public Pagina getPagina(String numPagina,String tipoActividad){
        open();
        Pagina pagina = new Pagina();
        String[] whereArgs = new String[]{numPagina,tipoActividad};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapaginas,
                    null,SQLConstantes.clausula_where_numero_pagina +
                            " AND " + SQLConstantes.clausula_where_tipo_actividad,
                    whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                pagina.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_id)));
                pagina.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_modulo)));
                pagina.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_numero)));
                pagina.setNombre(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_nombre)));
                pagina.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_actividad)));
                pagina.setTipo_pagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_pagina)));
            }
            cursor = sqLiteDatabase.query(SQLConstantes.tablapaginas,
                    null,null,
                    null,null,null,null);
            if(cursor.getCount() > 1){
                cursor.moveToFirst();
                Log.e("paginas_id",""+cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_id)));
                Log.e("paginas_modulo",""+cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_modulo)));
                Log.e("paginas_numero",""+cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_numero)));
                Log.e("paginas_nombre",""+cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_nombre)));
                Log.e("paginas_tipo_actividad",""+cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_actividad)));
                Log.e("paginas_tipo_pagina",""+cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_pagina)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return pagina;
    }

    public ArrayList<Pagina> getAllPaginas(String tipoActividad){
        open();
        ArrayList<Pagina> paginas = new ArrayList<>();
        String[] whereArgs = new String[]{tipoActividad};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapaginas,
                    null,SQLConstantes.clausula_where_tipo_actividad_activa,
                    whereArgs,null,null,null);
            while (cursor.moveToNext()){
                Pagina pagina = new Pagina();
                pagina.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_id)));
                pagina.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_modulo)));
                pagina.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_numero)));
                pagina.setNombre(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_nombre)));
                pagina.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_actividad)));
                pagina.setTipo_pagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_pagina)));
                pagina.setTipo_guardado(cursor.getInt(cursor.getColumnIndex(SQLConstantes.paginas_tipo_guardado)));
                pagina.setTabla_guardado(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tabla_guardado)));
                paginas.add(pagina);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return paginas;
    }

    public int getNroPaginas(String tipoActividad){
        open();
        int numero = 0;
        String[] whereArgs = new String[]{tipoActividad};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapaginas,
                    null,SQLConstantes.clausula_where_tipo_actividad_activa,
                    whereArgs,null,null,null);
            if (cursor.getCount() > 0){
                numero = cursor.getCount();
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return numero;
    }

    public ArrayList<Pagina> getPaginasxModulo(String idModulo){
        open();
        ArrayList<Pagina> paginas =  new ArrayList<Pagina>();
        String[] whereArgs = new String[]{idModulo};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapaginas,
                    null,SQLConstantes.clausula_where_modulo_pagina,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                Pagina pagina = new Pagina();
                pagina.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_id)));
                pagina.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_modulo)));
                pagina.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_numero)));
                pagina.setNombre(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_nombre)));
                pagina.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_actividad)));
                pagina.setTipo_pagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_pagina)));
                paginas.add(pagina);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return paginas;
    }

    public Pregunta getPregunta(String idPregunta){
        open();
        Pregunta pregunta = new Pregunta();
        String[] whereArgs = new String[]{idPregunta};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapreguntas,
                    null,SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                pregunta.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_id)));
                pregunta.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_modulo)));
                pregunta.setPagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_pagina)));
                pregunta.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_numero)));
                pregunta.setTipo_pregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_tipo_pregunta)));
                pregunta.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_tipo_actividad)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return pregunta;
    }

    public ArrayList<Pregunta> getPreguntasXPagina(String idPagina){
        open();
        ArrayList<Pregunta> preguntas = new ArrayList<>();
        String[] whereArgs = new String[]{idPagina};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapreguntas,
                    null,SQLConstantes.clausula_where_pagina_pregunta_activa,whereArgs,null,null,SQLConstantes.preguntas_numero);
            while (cursor.moveToNext()){
                Pregunta pregunta = new Pregunta();
                pregunta.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_id)));
                pregunta.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_modulo)));
                pregunta.setPagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_pagina)));
                pregunta.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_numero)));
                pregunta.setTipo_pregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_tipo_pregunta)));
                pregunta.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_tipo_actividad)));
                preguntas.add(pregunta);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return preguntas;
    }

    public PEditText getPEditText(String idPOJOEditText){
        open();
        PEditText pEditText = new PEditText();
        String[] whereArgs = new String[]{idPOJOEditText};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaedittext, null,
                    SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                pEditText.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.edittext_id)));
                pEditText.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.edittext_modulo)));
                pEditText.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.edittext_numero)));
                pEditText.setPregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.edittext_pregunta)));
                pEditText.setId_tabla(cursor.getString(cursor.getColumnIndex(SQLConstantes.edittext_id_tabla)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return pEditText;
    }

    public ArrayList<SPEdittext> getSPEditTexts(String idPregunta) {
        open();
        ArrayList<SPEdittext> spEditTexts = new ArrayList<SPEdittext>();
        String[] whereArgs = new String[]{idPregunta};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaspedittext, null,
                    SQLConstantes.clausula_where_id_pregunta, whereArgs, null, null, null);
            while(cursor.moveToNext()){
                SPEdittext spEditText = new SPEdittext();
                spEditText.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_edittext_id)));
                spEditText.setId_pregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_edittext_id_pregunta)));
                spEditText.setSubpregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_edittext_subpregunta)));
                spEditText.setVar_input(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_edittext_var_input)));
                spEditText.setTipo(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_edittext_tipo)));
                spEditText.setLongitud(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_edittext_longitud)));
                spEditTexts.add(spEditText);
            }
        }finally {
            if(cursor != null) cursor.close();
        }
        close();
        return spEditTexts;
    }

    public PCheckbox getPCheckbox(String idCheckbox){
        open();
        PCheckbox PCheckBox = new PCheckbox();
        String[] whereArgs = new String[]{idCheckbox};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacheckbox, null,
                    SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                PCheckBox.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.checkbox_id)));
                PCheckBox.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.checkbox_modulo)));
                PCheckBox.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.checkbox_numero)));
                PCheckBox.setPregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.checkbox_pregunta)));
                PCheckBox.setId_tabla(cursor.getString(cursor.getColumnIndex(SQLConstantes.checkbox_id_tabla)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return PCheckBox;
    }

    public ArrayList<SPCheckbox> getSPCheckBoxs(String idPregunta) {
        open();
        ArrayList<SPCheckbox> spCheckBoxs = new ArrayList<SPCheckbox>();
        String[] whereArgs = new String[]{idPregunta};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaspcheckbox, null,
                    SQLConstantes.clausula_where_id_pregunta, whereArgs, null, null, null);
            while(cursor.moveToNext()){
                SPCheckbox spCheckBox = new SPCheckbox();
                spCheckBox.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_checkbox_id)));
                spCheckBox.setId_pregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_checkbox_id_pregunta)));
                spCheckBox.setSubpregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_checkbox_subpregunta)));
                spCheckBox.setVar_guardado(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_checkbox_var_guardado)));
                spCheckBox.setVar_especifique(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_checkbox_var_especifique)));
                spCheckBox.setDeshabilita(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_checkbox_deshabilita)));
                spCheckBoxs.add(spCheckBox);
            }
        }finally {
            if(cursor != null) cursor.close();
        }
        close();
        return spCheckBoxs;
    }

    public PRadio getPRadio(String idCRadio){
        open();
        PRadio PRadio = new PRadio();
        String[] whereArgs = new String[]{idCRadio};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaradio, null,
                    SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                PRadio.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.radio_id)));
                PRadio.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.radio_modulo)));
                PRadio.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.radio_numero)));
                PRadio.setPregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.radio_pregunta)));
                PRadio.setId_tabla(cursor.getString(cursor.getColumnIndex(SQLConstantes.radio_id_tabla)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return PRadio;
    }

    public ArrayList<SPRadio> getSPRadios(String idPregunta) {
        open();
        ArrayList<SPRadio> spRadios = new ArrayList<SPRadio>();
        String[] whereArgs = new String[]{idPregunta};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaspradio, null,
                    SQLConstantes.clausula_where_id_pregunta, whereArgs, null, null, null);
            while(cursor.moveToNext()){
                SPRadio spRadio = new SPRadio();
                spRadio.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_radio_id)));
                spRadio.setId_pregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_radio_id_pregunta)));
                spRadio.setSubpregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_radio_subpregunta)));
                spRadio.setVar_input(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_radio_var_input)));
                spRadio.setVar_especifique(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_radio_var_especifique)));
                spRadios.add(spRadio);
            }
        }finally {
            if(cursor != null) cursor.close();
        }
        close();
        return spRadios;
    }

    public PFormulario getFormulario(String idPregunta){
        open();
        PFormulario formulario = new PFormulario();
        String[] whereArgs = new String[]{idPregunta};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaformulario,
                    null, SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                formulario.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.formulario_id)));
                formulario.setId_modulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.formulario_id_modulo)));
                formulario.setId_numero(cursor.getString(cursor.getColumnIndex(SQLConstantes.formulario_id_numero)));
                formulario.setTitulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.formulario_titulo)));
                formulario.setId_tabla(cursor.getString(cursor.getColumnIndex(SQLConstantes.formulario_id_tabla)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return formulario;
    }

    public ArrayList<SPFormulario> getSPFormularios(String idPregunta) {
        open();
        ArrayList<SPFormulario> spFormularios = new ArrayList<SPFormulario>();
        String[] whereArgs = new String[]{idPregunta};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaspformulario,
                    null, SQLConstantes.clausula_where_id_pregunta_activa, whereArgs, null, null, null);
            while(cursor.moveToNext()){
                SPFormulario spFormulario = new SPFormulario();
                spFormulario.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_id)));
                spFormulario.setId_pregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_id_pregunta)));
                spFormulario.setSubpregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_subpregunta)));
                spFormulario.setVar_edittext(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_var_edittext)));
                spFormulario.setLong_edittext(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_long_edittext)));
                spFormulario.setTipo_edittext(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_tipo_edittext)));
                spFormulario.setVar_spinner(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_var_spinner)));
                spFormulario.setVar_esp_spinner(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_var_esp_spinner)));
                spFormulario.setTipo_esp_spinner(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_tipo_esp_spinner)));
                spFormulario.setLong_esp_spinner(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_long_esp_spinner)));
                spFormulario.setHab_esp_spinner(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_hab_esp_spinner)));
                spFormulario.setVar_check_no(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_var_check_no)));
                spFormulario.setHab_edittext(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_hab_edittext)));
                spFormulario.setVar_radio(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_var_radio)));
                spFormulario.setVar_radio_o(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_var_radio_o)));
                spFormulario.setVar_esp_radio(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_formulario_var_esp_radio)));
                spFormularios.add(spFormulario);
            }
        }finally {
            if(cursor != null) cursor.close();
        }
        close();
        return spFormularios;
    }

    public ArrayList<String> getOpcionesSpinner(String idVarSpinner) {
        open();
        ArrayList<String> opcionSpinners = new ArrayList<String>();
        opcionSpinners.add("SELECCIONE...");
        String[] whereArgs = new String[]{idVarSpinner};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaOpcionSpinner,
                    null, SQLConstantes.WHERE_CLAUSE_ID_VARIABLE, whereArgs, null, null, null);
            while(cursor.moveToNext()){
                String opcionSpinner = "";
                opcionSpinner = opcionSpinner + cursor.getString(cursor.getColumnIndex(SQLConstantes.OPCION_SPINNER_NDATO)) + "."
                        + cursor.getString(cursor.getColumnIndex(SQLConstantes.OPCION_SPINNER_DATO));
                opcionSpinners.add(opcionSpinner);
            }
        }finally {
            if(cursor != null) cursor.close();
        }
        close();
        return opcionSpinners;
    }

    public long getNumeroItemsGPS(){
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, SQLConstantes.tablagps);
    }

    public void actualizarGPS(String idPregunta, ContentValues contentValues){
        sqLiteDatabase.update(SQLConstantes.tablagps, contentValues, SQLConstantes.clausula_where_id,new String[]{idPregunta});
    }

    public void insertarGPS(PGps gps){
        ContentValues contentValues = gps.toValues();
        sqLiteDatabase.insert(SQLConstantes.tablagps,null,contentValues);
    }

    public PGps getGPS(String idGPS){
        open();
        PGps gps = new PGps();
        String[] whereArgs = new String[]{idGPS};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablagps,
                    null, SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                gps.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_id)));
                gps.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_numero)));
                gps.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_modulo)));
                gps.setVar_alt(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_var_alt)));
                gps.setVar_lat(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_var_lat)));
                gps.setVar_long(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_var_long)));
                gps.setId_tabla(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_id_tabla)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return gps;
    }

    public ArrayList<PGps> getAllGPS(){
        open();
        ArrayList<PGps> gpsArrayList = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablagps, null, null,null,null,null,null);
            while(cursor.moveToNext()){
                PGps gps = new PGps();
                gps.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_id)));
                gps.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_numero)));
                gps.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_modulo)));
                gps.setVar_alt(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_var_alt)));
                gps.setVar_lat(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_var_lat)));
                gps.setVar_long(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_var_long)));
                gps.setId_tabla(cursor.getString(cursor.getColumnIndex(SQLConstantes.gps_id_tabla)));
                gpsArrayList.add(gps);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return gpsArrayList;
    }

    public void getTablasEncuesta(){
        String[] whereArgs;
        String create_table=" CREATE TABLE ";
        String inicio=" ( ", fin=" );";
        String coma=",";
        String text_not_null=" TEXT NOT NULL ";
        String pk_ini="primary key(";
        String pk_fin=")";
        String pk=")";
        String text=" TEXT DEFAULT '' ";
        String crear_tabla="";
        int contador=0;
        open();
        ArrayList<Info_tabla> info_tablaArrayList = new ArrayList<>();
        ArrayList<Info_tabla> tablas = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablainfo_tablas, null, null,null,null,null,null);
            while(cursor.moveToNext()){
                Info_tabla info_tabla = new Info_tabla();
                info_tabla.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.info_tablas_id)));
                info_tabla.setNombre(cursor.getString(cursor.getColumnIndex(SQLConstantes.info_tablas_nombre)));
                info_tablaArrayList.add(info_tabla);
            }

            if(info_tablaArrayList.size()>0){
                for(Info_tabla info_tabla:info_tablaArrayList){
                    Info_tabla tabla = new Info_tabla();
                    tabla.set_id(info_tabla.get_id());
                    tabla.setNombre(info_tabla.getNombre());
                    crear_tabla = create_table + info_tabla.getNombre() + inicio;
                    whereArgs = new String[]{info_tabla.get_id(),"1"};
                    cursor = sqLiteDatabase.query(SQLConstantes.tablavariables,
                            null, SQLConstantes.clausula_where_idtabla_pk,whereArgs,null,null,null);
                    pk = pk_ini;
                    contador=0;
                    while(cursor.moveToNext()){
                        contador++;
                        Variable variable = new Variable();
                        variable.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.variables_id)));
                        crear_tabla = crear_tabla + variable.get_id() + text_not_null + coma;
                        if(contador<cursor.getCount())  pk = pk + variable.get_id() + coma;
                        else pk = pk + variable.get_id() + pk_fin;
                    }

                    whereArgs = new String[]{info_tabla.get_id(),"0"};
                    cursor = sqLiteDatabase.query(SQLConstantes.tablavariables,
                            null, SQLConstantes.clausula_where_idtabla_pk,whereArgs,null,null,null);
                    Log.e("cursor.getCount():",""+info_tabla.get_id()+": "+cursor.getCount());
                    contador=0;
                    while(cursor.moveToNext()){
                        contador++;
                        Variable variable = new Variable();
                        variable.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.variables_id)));
                        if(contador<cursor.getCount()) crear_tabla = crear_tabla + variable.get_id() + text + coma;
                        else crear_tabla = crear_tabla + variable.get_id() + text + coma + pk;
                    }
                    crear_tabla = crear_tabla + fin;
                    tabla.setSql_tabla(crear_tabla);
                    tablas.add(tabla);
                }

                for(Info_tabla tabla:tablas){
                    if(!existeTabla(tabla.getNombre())) CrearTabla(tabla.getSql_tabla());

                    if(existeTabla(tabla.getNombre())) Log.e("ExisteTabla",""+tabla.getNombre());
                    else Log.e("NoExisteTabla",""+tabla.getNombre());
                }
            }


        }finally{
            if(cursor != null) cursor.close();
        }
        close();
    }

    public boolean existeTabla(String nombre_tabla){
        String sql="SELECT name FROM sqlite_master WHERE TYPE='table' AND name='"+nombre_tabla+"';";
        boolean existe=false;
        String[] whereArgs = new String[]{nombre_tabla};
        open();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery(sql,null);
            if(cursor.getCount()>0){
                existe = true;
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();

        return existe;
    }

    public void CrearTabla(String sql_crear_tabla){
        open();
        Cursor cursor = null;
        try{
             sqLiteDatabase.execSQL(sql_crear_tabla);
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
    }



    public void EjecutarQuery(String sql_query){
        Cursor cursor = null;
        try{
            sqLiteDatabase.execSQL(sql_query);
        }finally{
            if(cursor != null) cursor.close();
        }
    }

    public boolean existeElemento(int tipo, String nombre_tabla, String id_vivienda, String id_hogar, String id_residente){
        boolean existe=false;
        String[] whereArgs = new String[]{};
        String where="";

        switch (tipo){
            case 1:
                whereArgs = new String[]{id_vivienda};
                where = SQLConstantes.clausula_where_id_vivienda;
                break;
            case 2:
                whereArgs = new String[]{id_vivienda,id_hogar};
                where = SQLConstantes.clausula_where_id_hogar;
                break;
            case 3:
                whereArgs = new String[]{id_vivienda,id_hogar,id_residente};
                where = SQLConstantes.clausula_where_id_residente;
                break;
        }
        Cursor cursor = null;
        try{
            Log.e("dao-nombre_tabla","6-"+nombre_tabla);
            cursor = sqLiteDatabase.query(nombre_tabla,null, where,whereArgs,null,null,null);
            if(cursor.getCount()>0){
                existe = true;
            }
        }finally{
            if(cursor != null) cursor.close();
        }

        return existe;
    }

    public void actualizarElemento(int tipo, String nombre_tabla, String id_vivienda, String id_hogar, String id_residente, ContentValues contentValues){
        String[] whereArgs = new String[]{};
        String where="";

        switch (tipo){
            case 1:
                whereArgs = new String[]{id_vivienda};
                where = SQLConstantes.clausula_where_id_vivienda;
                break;
            case 2:
                whereArgs = new String[]{id_vivienda,id_hogar};
                where = SQLConstantes.clausula_where_id_hogar;
                break;
            case 3:
                whereArgs = new String[]{id_vivienda,id_hogar,id_residente};
                where = SQLConstantes.clausula_where_id_residente;
                break;
        }
        sqLiteDatabase.update(nombre_tabla, contentValues, where,whereArgs);
    }

    public void insertarElemento(String nombre_tabla, ContentValues contentValues){
        sqLiteDatabase.insert(nombre_tabla,null,contentValues);
    }

    public String[] getElementos(int tipo, String nombre_tabla, String[] variables, String id_vivienda, String id_hogar, String id_residente){
        String[] whereArgs = new String[]{};
        String where="";
        String[] elementos = new String[variables.length];

        switch (tipo){
            case 1:
                whereArgs = new String[]{id_vivienda};
                where = SQLConstantes.clausula_where_id_vivienda;
                break;
            case 2:
                whereArgs = new String[]{id_vivienda,id_hogar};
                where = SQLConstantes.clausula_where_id_hogar;
                break;
            case 3:
                whereArgs = new String[]{id_vivienda,id_hogar,id_residente};
                where = SQLConstantes.clausula_where_id_residente;
                break;
        }
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(nombre_tabla,
                    null, where,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                for (int i = 0; i < variables.length; i++) {
                    elementos[i] = cursor.getString(cursor.getColumnIndex(variables[i]));
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return elementos;
    }

    public String getElemento(int tipo, String nombre_tabla, String variable, String id_vivienda, String id_hogar, String id_residente){
        String[] whereArgs = new String[]{};
        String where="";
        String elemento="";

        switch (tipo){
            case 0:
                whereArgs = new String[]{id_vivienda};
                where = SQLConstantes.clausula_where_id;
                break;
            case 1:
                whereArgs = new String[]{id_vivienda};
                where = SQLConstantes.clausula_where_id_vivienda;
                break;
            case 2:
                whereArgs = new String[]{id_vivienda,id_hogar};
                where = SQLConstantes.clausula_where_id_hogar;
                break;
            case 3:
                whereArgs = new String[]{id_vivienda,id_hogar,id_residente};
                where = SQLConstantes.clausula_where_id_residente;
                break;
        }
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(nombre_tabla,
                    null, where,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                elemento = cursor.getString(cursor.getColumnIndex(variable));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return elemento;
    }

    public ArrayList<Residente> getAllResidentes(String id_vivienda, String id_hogar){
        open();
        String[] whereArgs = new String[]{};
        String where="";
        whereArgs = new String[]{id_vivienda,id_hogar};
        where = SQLConstantes.clausula_where_id_hogar;
        ArrayList<Residente> residenteArrayList = new ArrayList<>();
        Cursor cursor = null;
        try{
            Log.e("class.getName()",""+Residente.class.getSimpleName());
            Residente res = new Residente();
            Log.e("nombre.getName()",""+res.getNombre_var());
            cursor = sqLiteDatabase.query(Residente.class.getSimpleName(), null, where,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                Residente residente = new Residente();
                residente.setId_vivienda(cursor.getString(cursor.getColumnIndex("id_vivienda")));
                residente.setId_hogar(cursor.getString(cursor.getColumnIndex("id_hogar")));
                residente.setId_residente(cursor.getString(cursor.getColumnIndex("id_residente")));
                residente.setNombre(cursor.getString(cursor.getColumnIndex(residente.getNombre_var())));
                residente.setParentesco(cursor.getString(cursor.getColumnIndex(residente.getParentesco_var())));
                residente.setSexo(cursor.getString(cursor.getColumnIndex(residente.getSexo_var())));
                residente.setEdad_anio(cursor.getString(cursor.getColumnIndex(residente.getEdad_anio_var())));
                residente.setEdad_mes(cursor.getString(cursor.getColumnIndex(residente.getEdad_mes_var())));
                residente.setEstado_civil(cursor.getString(cursor.getColumnIndex(residente.getEstado_civil_var())));
                residente.setCompleto(cursor.getString(cursor.getColumnIndex(residente.getCompleto_var())));
                residente.setCobertura(cursor.getString(cursor.getColumnIndex(residente.getCobertura_var())));
                residenteArrayList.add(residente);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return residenteArrayList;
    }

    public void eliminarResidente(Residente residente){
        String[] whereArgs = new String[]{};
        String where="";
        String tabla="";
        String sql="select nombre from info_tablas where _id in (select id_tabla from variables where id_pregunta='id_residente');";
        whereArgs = new String[]{residente.getId_vivienda(),residente.getId_hogar(),residente.getId_residente()};
        where = SQLConstantes.clausula_where_id_residente;
        open();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery(sql,null);
            while(cursor.moveToNext()){
                tabla = cursor.getString(cursor.getColumnIndex("nombre"));
                sqLiteDatabase.delete(tabla,where,whereArgs);
            }
        }finally{
            if(cursor != null) cursor.close();
        }

        close();
    }

    public ArrayList<Hogar> getAllHogares(String id_vivienda){
        open();
        String[] whereArgs = new String[]{};
        String where="";
        whereArgs = new String[]{id_vivienda};
        where = SQLConstantes.clausula_where_id_vivienda;
        ArrayList<Hogar> hogarArrayList = new ArrayList<>();
        Cursor cursor = null;
        try{
            Log.e("class.getName()",""+Hogar.class.getSimpleName());
            Hogar hog = new Hogar();
            Log.e("nombre.getName()",""+hog.getNom_jefe_var());
            cursor = sqLiteDatabase.query(Hogar.class.getSimpleName(), null, where,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                Hogar hogar = new Hogar();
                hogar.setId_vivienda(cursor.getString(cursor.getColumnIndex("id_vivienda")));
                hogar.setId_hogar(cursor.getString(cursor.getColumnIndex("id_hogar")));
                hogar.setNom_jefe(cursor.getString(cursor.getColumnIndex(hogar.getNom_jefe_var())));
                hogar.setEstado(cursor.getString(cursor.getColumnIndex(hogar.getEstado_var())));
                hogar.setVive(cursor.getString(cursor.getColumnIndex(hogar.getVive_var())));
                hogar.setNroviven(cursor.getString(cursor.getColumnIndex(hogar.getNroviven_var())));
                hogar.setPrincipal(cursor.getString(cursor.getColumnIndex(hogar.getPrincipal_var())));
                hogar.setCobertura(cursor.getString(cursor.getColumnIndex(hogar.getCobertura_var())));
                hogarArrayList.add(hogar);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return hogarArrayList;
    }

    public void eliminarHogar(Hogar hogar){
        String[] whereArgs = new String[]{};
        String where="";
        String tabla="";
        String sql="select nombre from info_tablas where _id in (select id_tabla from variables where id_pregunta='id_hogar');";
        whereArgs = new String[]{hogar.getId_vivienda(),hogar.getId_hogar()};
        where = SQLConstantes.clausula_where_id_hogar;
        open();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery(sql,null);
            while(cursor.moveToNext()){
                tabla = cursor.getString(cursor.getColumnIndex("nombre"));
                sqLiteDatabase.delete(tabla,where,whereArgs);
            }
        }finally{
            if(cursor != null) cursor.close();
        }

        close();
    }

    public boolean existeElementoMarco(String idMarco, String tabla){
        boolean existe = false;
        String[] whereArgs = new String[]{idMarco};
        Cursor cursor = null;
        Log.e("existeElementoMarco","0-"+idMarco);
        try{
            cursor = sqLiteDatabase.query(tabla, null,SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                existe = true;
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return existe;
    }

    public ArrayList<ItemMarco_hogar> getListMarcoFiltrado2(String anio, String mes, String periodo, String zona){
        ArrayList<ItemMarco_hogar> itemMarcos = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(anio), String.valueOf(mes),String.valueOf(periodo),String.valueOf(zona)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco_hogar,
                    null,SQLConstantes.WHERE_CLAUSE_ANIO + " AND " +
                            SQLConstantes.WHERE_CLAUSE_MES + " AND " +
                            SQLConstantes.WHERE_CLAUSE_PERIODO + " AND " +
                            SQLConstantes.WHERE_CLAUSE_ZONA,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco_hogar itemMarco = new ItemMarco_hogar();
                itemMarco.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                itemMarco.setAnio(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_anio)));
                itemMarco.setMes(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_mes)));
                itemMarco.setPeriodo(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_periodo)));
                itemMarco.setZona(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_zona)));
                itemMarco.setNorden(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_norden)));
                itemMarco.setEstado(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_estado)));
                itemMarcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return itemMarcos;
    }

    public ArrayList<ItemMarco_hogar> getListMarco(String idUsuario){
        ArrayList<ItemMarco_hogar> itemMarcos = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idUsuario)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco_hogar,
                    null,SQLConstantes.WHERE_CLAUSE_USUARIO,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco_hogar itemMarco = new ItemMarco_hogar();
                itemMarco.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                itemMarco.setAnio(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_anio)));
                itemMarco.setMes(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_mes)));
                itemMarco.setPeriodo(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_periodo)));
                itemMarco.setZona(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_zona)));
                itemMarco.setNorden(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_norden)));
                itemMarco.setEstado(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_estado)));

                itemMarcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return itemMarcos;
    }

    public ArrayList<ItemMarco_hogar> getListMarcoSupervisor(String idUsuario){
        ArrayList<ItemMarco_hogar> itemMarcos = new ArrayList<>();
        String[] whereArgs = new String[]{String.valueOf(idUsuario)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco_hogar,
                    null,SQLConstantes.WHERE_CLAUSE_USUARIO_SUP_ID,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco_hogar itemMarco = new ItemMarco_hogar();
                itemMarco.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                itemMarco.setAnio(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_anio)));
                itemMarco.setMes(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_mes)));
                itemMarco.setPeriodo(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_periodo)));
                itemMarco.setZona(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_zona)));
                itemMarco.setNorden(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_norden)));
                itemMarco.setEstado(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_estado)));
                itemMarcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return itemMarcos;
    }

    public boolean realizar_flujo(int tipo, String variable, String id_vivienda, String id_hogar, String id_residente){
        boolean tiene_flujo=false;
        ArrayList<Flujo> itemFlujos = new ArrayList<>();
        itemFlujos = getFlujos(variable);

        if(itemFlujos.size()>0) tiene_flujo=true;

        for(Flujo flujo: itemFlujos) {
            if (!Herramientas.texto(flujo.getNombre_flujo()).equals("")) {
                String sql = "update preguntas set estado='1' where _id in (select _id from preguntas where cast(orden_flujo as int)>" + flujo.getOrden_flujo_ini() + " and cast(orden_flujo as int)<" + flujo.getNumero_go_to_max() + ");";

                EjecutarQuery(sql);

                String[] whereArgs_0 = new String[]{String.valueOf(flujo.getNombre_flujo())};
                String[] whereArgs = new String[flujo.getNumero_var()];
                String where = "";
                String operador = "";
                String cast_entero_ini="", cast_entero_fin="";
                Cursor cursor = null,cursor2 = null;
                cursor = sqLiteDatabase.query(SQLConstantes.tablavariable_flujo,
                        null, SQLConstantes.clausula_where_nombre_flujo, whereArgs_0, null, null, null);
                int pos = 0;
                String var_flujo = "", val_var_flujo = "", tabla="";
                while (cursor.moveToNext()) {
                    cast_entero_ini=""; cast_entero_fin="";
                    var_flujo = cursor.getString(cursor.getColumnIndex(SQLConstantes.variable_flujo_nombre_var));
                    tabla = cursor.getString(cursor.getColumnIndex(SQLConstantes.variable_flujo_nombre_tabla));
                    operador= cursor.getString(cursor.getColumnIndex(SQLConstantes.variable_flujo_operador));
                    if(!operador.equals("=")) {
                        cast_entero_ini="cast(";
                        cast_entero_fin=" as int)";
                    }
                    val_var_flujo = getElemento(tipo, tabla, var_flujo, id_vivienda, id_hogar, id_residente);
                    whereArgs[pos] = val_var_flujo;
                    pos++;
                    if (pos < cursor.getCount()) where = where + cast_entero_ini + var_flujo + cast_entero_fin + operador + "? and ";
                    else where = where + cast_entero_ini + var_flujo + cast_entero_fin + operador + "?";
                }
                cursor = sqLiteDatabase.query(flujo.getNombre_flujo(),
                        null, where, whereArgs, null, null, null);
                while (cursor.moveToNext()) {
                    flujo.setGo_to(cursor.getString(cursor.getColumnIndex(SQLConstantes.flujo_go_to)));
                    flujo.setNumero_go_to(getOrden_flujo(flujo.getGo_to()));
                }
                if (!Herramientas.texto(flujo.getGo_to()).equals("")) {
                    sql = "update preguntas set estado='0' where _id in (select _id from preguntas where cast(orden_flujo as int)>" + flujo.getOrden_flujo_ini() + " and cast(orden_flujo as int)<" + flujo.getNumero_go_to() + ");";
                    EjecutarQuery(sql);


                    sql = "select _id,tipo_pregunta from preguntas where cast(orden_flujo as int)>" + flujo.getOrden_flujo_ini() + " and cast(orden_flujo as int)<" + flujo.getNumero_go_to() + ";";
                    cursor = sqLiteDatabase.rawQuery(sql,null);
                    while (cursor.moveToNext()) {
                        String nom_var = cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_id));
                        String nom_tabla = getTabla(nom_var);
                        int tipo_pregunta  = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_tipo_pregunta)));
                        int tipo_guardado = getTipoGurdado(nom_var);
                        ContentValues contentValues;
                        contentValues = new ContentValues();
                        switch (tipo_guardado){
                            case 1: contentValues.put("id_vivienda", id_vivienda);
                                break;
                            case 2: contentValues.put("id_vivienda", id_vivienda);
                                contentValues.put("id_hogar", id_hogar);
                                break;
                            case 3: contentValues.put("id_vivienda", id_vivienda);
                                contentValues.put("id_hogar", id_hogar);
                                contentValues.put("id_residente", id_residente);
                                break;
                        }
                        switch (tipo_pregunta){
                            case TipoComponente.EDITTEXT: case TipoComponente.RADIO:
                                contentValues.put(nom_var, "");
                                if(tipo_pregunta==TipoComponente.RADIO){
                                    sql = "select var_especifique from c_sp_radio where id_pregunta='"+nom_var+"' and var_especifique<>'';";
                                    cursor2 = sqLiteDatabase.rawQuery(sql,null);
                                    while (cursor2.moveToNext()) {
                                        contentValues.put(cursor2.getString(cursor2.getColumnIndex(SQLConstantes.sp_radio_var_especifique)), "");
                                    }
                                }
                                actualizarElemento(tipo_guardado,nom_tabla,id_vivienda,id_hogar,id_residente,contentValues);
                                break;
                            case TipoComponente.CHECKBOX:
                                contentValues.put(nom_var, "");
                                sql = "select var_guardado from c_sp_checkbox where id_pregunta='"+nom_var+"';";
                                cursor2 = sqLiteDatabase.rawQuery(sql,null);
                                while (cursor2.moveToNext()) {
                                    contentValues.put(cursor2.getString(cursor2.getColumnIndex(SQLConstantes.sp_checkbox_var_guardado)), "");
                                }
                                sql = "select var_especifique from c_sp_checkbox where id_pregunta='"+nom_var+"' and var_especifique<>'';";
                                cursor2 = sqLiteDatabase.rawQuery(sql,null);
                                while (cursor2.moveToNext()) {
                                    contentValues.put(cursor2.getString(cursor2.getColumnIndex(SQLConstantes.sp_checkbox_var_especifique)), "");
                                }
                                actualizarElemento(tipo_guardado,nom_tabla,id_vivienda,id_hogar,id_residente,contentValues);
                                break;
                            case TipoComponente.FORMULARIO:
                                sql = "select var_guardado from c_sp_checkbox where id_pregunta='"+nom_var+"';";
                                cursor2 = sqLiteDatabase.rawQuery(sql,null);
                                while (cursor2.moveToNext()) {
                                    contentValues.put(cursor2.getString(cursor2.getColumnIndex(SQLConstantes.sp_checkbox_var_guardado)), "");
                                }
                                sql = "select var_edittext variable from c_sp_formulario where id_pregunta='"+nom_var+"' and var_edittext<>'' union ";
                                sql = sql + "select var_spinner variable from c_sp_formulario where id_pregunta='"+nom_var+"' and var_spinner<>'' union ";
                                sql = sql + "select var_esp_spinner variable from c_sp_formulario where id_pregunta='"+nom_var+"' and var_esp_spinner<>'' union ";
                                sql = sql + "select var_radio variable from c_sp_formulario where id_pregunta='"+nom_var+"' and var_radio<>'' union ";
                                sql = sql + "select var_esp_radio variable from c_sp_formulario where id_pregunta='"+nom_var+"' and var_esp_spinner<>'';";
                                cursor2 = sqLiteDatabase.rawQuery(sql,null);
                                while (cursor2.moveToNext()) {
                                    contentValues.put(cursor2.getString(cursor2.getColumnIndex("variable")), "");
                                }
                                actualizarElemento(tipo_guardado,nom_tabla,id_vivienda,id_hogar,id_residente,contentValues);
                                break;
                        }
                    }
                }
            }
        }
        return tiene_flujo;
    }

    public ArrayList<Flujo> getFlujos(String variable){
        ArrayList<Flujo> itemFlujos = new ArrayList<>();
        Flujo flujo = new Flujo();

        flujo.setNombre_flujo("");
        flujo.setOrden_flujo_ini(-1);
        String[] whereArgs = new String[]{String.valueOf(variable)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablavariable_flujo,
                    null,SQLConstantes.clausula_where_nombre_var,whereArgs,null,null,SQLConstantes.variable_flujo_numero_flujo);
            while (cursor.moveToNext()){
                flujo = new Flujo();
                flujo.setNombre_flujo(cursor.getString(cursor.getColumnIndex(SQLConstantes.flujo_nombre_flujo)));
                if(!Herramientas.texto(flujo.getNombre_flujo()).equals("")){
                    whereArgs = new String[]{String.valueOf(flujo.getNombre_flujo())};
                    cursor = sqLiteDatabase.query(SQLConstantes.tablainfo_flujos,
                            null,SQLConstantes.clausula_where_id,whereArgs,null,null,null);
                    while (cursor.moveToNext()){
                        flujo.setNumero_var(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLConstantes.flujo_numero_var))));
                        flujo.setOrden_flujo_ini(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLConstantes.flujo_orden_flujo_ini))));
                        flujo.setNumero_go_to_max(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLConstantes.flujo_numero_go_to_max))));
                    }
                    itemFlujos.add(flujo);
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }

        Log.e("DAOEncuesta","getFlujo-flujo.getNombre_flujo():"+flujo.getNombre_flujo());
        Log.e("DAOEncuesta","getFlujo-flujo.getOrden_flujo_ini():"+flujo.getOrden_flujo_ini());
        Log.e("DAOEncuesta","getFlujo-flujo.getNumero_go_to_max():"+flujo.getNumero_go_to_max());

        return  itemFlujos;
    }

    public String getTabla(String variable){
        String tabla="",pagina="";
        String[] whereArgs = new String[]{String.valueOf(variable)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapreguntas,
                    null,SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                pagina = cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_pagina));
            }
            if(!Herramientas.texto(pagina).equals("")){
                whereArgs = new String[]{String.valueOf(pagina)};
                cursor = sqLiteDatabase.query(SQLConstantes.tablapaginas,
                        null,SQLConstantes.clausula_where_id,whereArgs,null,null,null);
                while (cursor.moveToNext()){
                    tabla = cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tabla_guardado));
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }

        return  tabla;
    }

    public int getTipoGurdado(String variable){
        String tipo_guardado="",pagina="";
        String[] whereArgs = new String[]{String.valueOf(variable)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapreguntas,
                    null,SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                pagina = cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_pagina));
            }
            if(!Herramientas.texto(pagina).equals("")){
                whereArgs = new String[]{String.valueOf(pagina)};
                cursor = sqLiteDatabase.query(SQLConstantes.tablapaginas,
                        null,SQLConstantes.clausula_where_id,whereArgs,null,null,null);
                while (cursor.moveToNext()){
                    tipo_guardado = cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_guardado));
                }
            }
        }finally{
            if(cursor != null) cursor.close();
        }

        return  Integer.parseInt(tipo_guardado);
    }

    public int getOrden_flujo(String variable){
        int orden_flujo=-1;
        String[] whereArgs = new String[]{String.valueOf(variable)};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapreguntas,
                    null,SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                orden_flujo = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_orden_flujo)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }

        return  orden_flujo;
    }
}
