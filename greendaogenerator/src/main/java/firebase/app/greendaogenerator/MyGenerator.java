package firebase.app.greendaogenerator;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyGenerator {

    public static void main(String[] args) {
        Schema schema = new Schema(1, "pe.gob.inei.generadorinei.greendao"); // El nombre de su paquete de aplicación y (.model) es la carpeta donde se generarán los archivos DAO
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addCaratulaEntities(schema);
        addHogarEntities(schema);
        addResidenteEntities(schema);
        addVisitaEntities(schema);
    }

    // This is use to describe the colums of your table
    private static Entity addCaratulaEntities(final Schema schema) {
        Entity user = schema.addEntity("Caratula");
        user.addIdProperty().primaryKey();
        user.addStringProperty("anio");
        user.addStringProperty("mes");
        user.addStringProperty("periodo");
        user.addStringProperty("conglomerado");
        user.addStringProperty("nom_dep");
        user.addStringProperty("nom_prov");
        user.addStringProperty("nom_dist");
        user.addStringProperty("nom_ccpp");
        user.addStringProperty("zona");
        user.addStringProperty("manzana_id");
        user.addStringProperty("vivienda");
        user.addStringProperty("latitud");
        user.addStringProperty("longitud");
        user.addStringProperty("tipvia");
        user.addStringProperty("tipvia_o");
        user.addStringProperty("nomvia");
        user.addStringProperty("nropta");
        user.addStringProperty("block");
        user.addStringProperty("interior");
        user.addStringProperty("piso");
        user.addStringProperty("mza");
        user.addStringProperty("lote");
        user.addStringProperty("km");
        user.addStringProperty("telefono");
        user.addStringProperty("t_hogar");
        user.addStringProperty("usuario");
        user.addStringProperty("cobertura");

        return user;
    }

    private static Entity addHogarEntities(final Schema schema) {
        Entity user = schema.addEntity("Hogar");
        user.addIdProperty().primaryKey().autoincrement();
        user.addStringProperty("id_vivienda");
        user.addStringProperty("numero_hogar");
        user.addStringProperty("nom_ape");
        user.addStringProperty("estado");
        user.addStringProperty("nropersonas");
        user.addStringProperty("vive");
        user.addStringProperty("nroviven");
        user.addStringProperty("principal");
        user.addStringProperty("cobertura");

        return user;
    }

    private static Entity addResidenteEntities(final Schema schema) {
        Entity user = schema.addEntity("Residente");
        user.addIdProperty().primaryKey().autoincrement();
        user.addStringProperty("id_vivienda");
        user.addStringProperty("numero_hogar");
        user.addStringProperty("numero_residente");
        user.addStringProperty("numero_informante");
        user.addStringProperty("c2_p202");
        user.addStringProperty("c2_p203");
        user.addStringProperty("c2_p204");
        user.addStringProperty("c2_p205_a");
        user.addStringProperty("c2_p205_m");
        user.addStringProperty("c2_p206");
        user.addStringProperty("c2_p207");
        user.addStringProperty("cobertura");

        return user;
    }

    private static Entity addVisitaEntities(final Schema schema) {
        Entity user = schema.addEntity("Visita_encuestador");
        user.addIdProperty().primaryKey().autoincrement();
        user.addStringProperty("id_vivienda");
        user.addStringProperty("numero_hogar");
        user.addStringProperty("numero_visita");
        user.addStringProperty("vis_fecha_dd");
        user.addStringProperty("vis_fecha_mm");
        user.addStringProperty("vis_fecha_aa");
        user.addStringProperty("vis_hor_ini");
        user.addStringProperty("vis_min_ini");
        user.addStringProperty("vis_hor_fin");
        user.addStringProperty("vis_min_fin");
        user.addStringProperty("prox_vis_fecha_dd");
        user.addStringProperty("prox_vis_fecha_mm");
        user.addStringProperty("prox_vis_fecha_aa");
        user.addStringProperty("prox_vis_hor");
        user.addStringProperty("prox_vis_min");
        user.addStringProperty("vis_resu");
        user.addStringProperty("vis_resu_esp");

        return user;
    }
}
