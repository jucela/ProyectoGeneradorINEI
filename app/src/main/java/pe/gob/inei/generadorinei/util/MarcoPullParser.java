package pe.gob.inei.generadorinei.util;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import pe.gob.inei.generadorinei.model.dao.SQLConstantes;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Marco;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Marco_hogar;


/**
 * Created by RICARDO on 10/08/2017.
 */

public class MarcoPullParser {
    private static final String _ID = "_id";
    private static final String ANIO = "anio";
    private static final String MES = "mes";
    private static final String PERIODO = "periodo";
    private static final String CONGLOMERADO = "conglomerado";
    private static final String ZONA = "zona";
    private static final String CCDD = "ccdd";
    private static final String DEPARTAMENTO = "departamento";
    private static final String CCPP = "ccpp";
    private static final String PROVINCIA = "provincia";
    private static final String CCDI = "ccdi";
    private static final String DISTRITO = "distrito";
    private static final String CODCCPP = "codccpp";
    private static final String NOMCCPP = "nomccpp";
    private static final String RUC = "ruc";
    private static final String RAZON_SOCIAL = "razon_social";
    private static final String NOMBRE_COMERCIAL = "nombre_comercial";
    private static final String TIPO_EMPRESA = "tipo_empresa";
    private static final String ENCUESTADOR = "encuestador";
    private static final String SUPERVISOR = "supervisor";
    private static final String COORDINADOR = "coordinador";
    private static final String FRENTE = "frente";
    private static final String NUMEROODEN = "numeroorden";
    private static final String MANZANA_ID = "manzana_id";
    private static final String TIPO_VIA = "tipo_via";
    private static final String NOMBRE_VIA = "nombre_via";
    private static final String PUERTA = "puerta";
    private static final String MANZANA = "manzana";
    private static final String LOTE = "lote";
    private static final String PISO = "piso";
    private static final String BLOCK = "block";
    private static final String INTERIOR = "interior";
    private static final String ESTADO = "estado";


    private Marco currentMarco = null;
    private Marco_hogar currentMarco_hogar = null;
    private String currentTag = null;
    ArrayList<Marco> marcos = new ArrayList<Marco>();
    ArrayList<Marco_hogar> marcos_hogar = new ArrayList<Marco_hogar>();

    public ArrayList<Marco> parseXML(Context context){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            try {
                InputStream stream = context.getAssets().open("marco.xml");
                xpp.setInput(stream,null);

                int eventType = xpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.START_TAG){
                        handleStarTag(xpp.getName());
                    }else if(eventType == XmlPullParser.END_TAG){
                        currentTag = null;
                    }else if(eventType == XmlPullParser.TEXT){
                        handleText(xpp.getText());
                    }
                    eventType = xpp.next();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return marcos;
    }

    public ArrayList<Marco_hogar> parseXML_hogar(Context context){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            try {
                InputStream stream = context.getAssets().open("marco_hogar.xml");
                xpp.setInput(stream,null);

                int eventType = xpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.START_TAG){
                        handleStarTag_hogar(xpp.getName());
                    }else if(eventType == XmlPullParser.END_TAG){
                        currentTag = null;
                    }else if(eventType == XmlPullParser.TEXT){
                        handleText_hogar(xpp.getText());
                    }
                    eventType = xpp.next();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return marcos_hogar;
    }

    public ArrayList<Marco> parseXML(Context context, String archivo){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            FileInputStream fis = null;
            try {
                File file = new File(archivo);
                FileInputStream fileInputStream = new FileInputStream(file);
                fis = new FileInputStream(file);
                xpp.setInput(fis, null);
                int eventType = xpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.START_TAG){
                        handleStarTag(xpp.getName());
                    }else if(eventType == XmlPullParser.END_TAG){
                        currentTag = null;
                    }else if(eventType == XmlPullParser.TEXT){
                        handleText(xpp.getText());
                    }
                    eventType = xpp.next();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return marcos;
    }

    public ArrayList<Marco_hogar> parseXML_hogar(Context context, String archivo){
        Log.e("MarcoPullParser","parseXML_hogar-0");
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            FileInputStream fis = null;
            try {
                File file = new File(archivo);
                FileInputStream fileInputStream = new FileInputStream(file);
                fis = new FileInputStream(file);
                xpp.setInput(fis, null);
                int eventType = xpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT){
                    Log.e("MarcoPullParser","parseXML_hogar-1");
                    if(eventType == XmlPullParser.START_TAG){
                        handleStarTag_hogar(xpp.getName());
                    }else if(eventType == XmlPullParser.END_TAG){
                        currentTag = null;
                    }else if(eventType == XmlPullParser.TEXT){
                        handleText_hogar(xpp.getText());
                    }
                    eventType = xpp.next();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return marcos_hogar;
    }

    private void handleText(String text) {
        String xmlText = text;
        if(currentMarco!= null && currentTag != null){
            switch (currentTag){
                case  _ID: currentMarco.set_id(xmlText);break;
                case  ANIO: currentMarco.setAnio(xmlText);break;
                case  MES: currentMarco.setMes(xmlText);break;
                case  PERIODO:currentMarco.setPeriodo(xmlText);break;
                case  CONGLOMERADO: currentMarco.setConglomerado(xmlText);break;
                case  ZONA: currentMarco.setZona(xmlText);break;
                case  CCDD: currentMarco.setCcdd(xmlText);break;
                case  DEPARTAMENTO: currentMarco.setDepartamento(xmlText);break;
                case  CCPP: currentMarco.setCcpp(xmlText);break;
                case  PROVINCIA: currentMarco.setProvincia(xmlText);break;
                case  CCDI: currentMarco.setCcdi(xmlText);break;
                case  DISTRITO: currentMarco.setDistrito(xmlText);break;
                case  CODCCPP: currentMarco.setCodccpp(xmlText);break;
                case  NOMCCPP: currentMarco.setNomccpp(xmlText);break;
                case  RUC: currentMarco.setRuc(xmlText);break;
                case  RAZON_SOCIAL: currentMarco.setRazon_social(xmlText);break;
                case  NOMBRE_COMERCIAL: currentMarco.setNombre_comercial(xmlText);break;
                case  TIPO_EMPRESA: currentMarco.setTipo_empresa(xmlText);break;
                case  ENCUESTADOR: currentMarco.setEncuestador(xmlText);break;
                case  SUPERVISOR: currentMarco.setSupervisor(xmlText);break;
                case  COORDINADOR: currentMarco.setCoordinador(xmlText);break;
                case  FRENTE: currentMarco.setFrente(xmlText);break;
                case  NUMEROODEN: currentMarco.setNumero_orden(xmlText);break;
                case  MANZANA_ID: currentMarco.setManzana_id(xmlText);break;
                case  TIPO_VIA: currentMarco.setTipo_via(xmlText);break;
                case  NOMBRE_VIA: currentMarco.setNombre_via(xmlText);break;
                case  PUERTA: currentMarco.setPuerta(xmlText);break;
                case  MANZANA: currentMarco.setManzana(xmlText);break;
                case  LOTE: currentMarco.setLote(xmlText);break;
                case  PISO: currentMarco.setPiso(xmlText);break;
                case  BLOCK: currentMarco.setBlock(xmlText);break;
                case  INTERIOR: currentMarco.setInterior(xmlText);break;
                case  ESTADO: currentMarco.setEstado(xmlText);break;
            }
        }
    }

    private void handleText_hogar(String text) {
        String xmlText = text;
        Log.e("MarcoPullParser","handleText_hogar-"+text);
        if(currentMarco_hogar!= null && currentTag != null){
            switch (currentTag.toLowerCase()){
                case  SQLConstantes.marco_id: currentMarco_hogar.set_id(xmlText);break;
                case  SQLConstantes.marco_anio: currentMarco_hogar.setAnio(xmlText);break;
                case  SQLConstantes.marco_mes: currentMarco_hogar.setMes(xmlText);break;
                case  SQLConstantes.marco_periodo:currentMarco_hogar.setPeriodo(xmlText);break;
                case  SQLConstantes.marco_zona: currentMarco_hogar.setZona(xmlText);break;
                case  SQLConstantes.marco_ccdd: currentMarco_hogar.setCcdd(xmlText);break;
                case  SQLConstantes.marco_departamento: currentMarco_hogar.setDepartamento(xmlText);break;
                case  SQLConstantes.marco_ccpp: currentMarco_hogar.setCcpp(xmlText);break;
                case  SQLConstantes.marco_provincia: currentMarco_hogar.setProvincia(xmlText);break;
                case  SQLConstantes.marco_ccdi: currentMarco_hogar.setCcdi(xmlText);break;
                case  SQLConstantes.marco_distrito: currentMarco_hogar.setDistrito(xmlText);break;
                case  SQLConstantes.marco_codccpp: currentMarco_hogar.setCodccpp(xmlText);break;
                case  SQLConstantes.marco_nomccpp: currentMarco_hogar.setNomccpp(xmlText);break;
                case  SQLConstantes.marco_conglomerado: currentMarco_hogar.setConglomerado(xmlText);break;
                case  SQLConstantes.marco_norden: currentMarco_hogar.setNorden(xmlText);break;
                case  SQLConstantes.marco_manzana_id: currentMarco_hogar.setManzana_id(xmlText);break;
                case  SQLConstantes.marco_tipvia: currentMarco_hogar.setTipvia(xmlText);break;
                case  SQLConstantes.marco_nomvia: currentMarco_hogar.setNomvia(xmlText);break;
                case  SQLConstantes.marco_nropta: currentMarco_hogar.setNropta(xmlText);break;
                case  SQLConstantes.marco_lote: currentMarco_hogar.setLote(xmlText);break;
                case  SQLConstantes.marco_piso: currentMarco_hogar.setPiso(xmlText);break;
                case  SQLConstantes.marco_manzana: currentMarco_hogar.setMza(xmlText);break;
                case  SQLConstantes.marco_block: currentMarco_hogar.setBlock(xmlText);break;
                case  SQLConstantes.marco_interior: currentMarco_hogar.setInterior(xmlText);break;
                case  SQLConstantes.marco_usuario_id: currentMarco_hogar.setUsuario_id(xmlText);break;
                case  SQLConstantes.marco_usuario_sup_id: currentMarco_hogar.setUsuario_sup_id(xmlText);break;
                case  SQLConstantes.marco_usuario: currentMarco_hogar.setUsuario(xmlText);break;
                case  SQLConstantes.marco_dni: currentMarco_hogar.setDni(xmlText);break;
                case  SQLConstantes.marco_nombre: currentMarco_hogar.setNombre(xmlText);break;
                case  SQLConstantes.marco_estado: currentMarco_hogar.setEstado(xmlText);break;
            }
            Log.e("MarcoPullParser","handleText_hogar-currentTag.toLowerCase()"+currentTag.toLowerCase());
            Log.e("MarcoPullParser","handleText_hogar-marcos_hogar.size()"+marcos_hogar.size());
            Log.e("MarcoPullParser","handleText_hogar-currentMarco_hogar.get_id()"+ currentMarco_hogar.get_id());
          /*
            if(marcos_hogar.size()>0){
                marcos_hogar.set((marcos_hogar.size()-1),currentMarco_hogar);
            }
          */
        }
    }

    private void handleStarTag(String name) {
        if(name.equals("ITEM_MARCO")){
            currentMarco = new Marco();
            marcos.add(currentMarco);
        }else{
            currentTag = name;
        }
    }

    private void handleStarTag_hogar(String name) {
        Log.e("MarcoPullParser","handleStarTag_hogar-"+name);
        if(name.equals("ITEM_MARCO")){
            currentMarco_hogar = new Marco_hogar();
            marcos_hogar.add(currentMarco_hogar);
        }else{
            currentTag = name;
        }
    }
}
