package pe.gob.inei.generadorinei.model.dao;

public class SQLConstantes {
    public static final String DB_PATH = "/data/data/pe.gob.inei.generadorinei/databases/";
    public static final String DB_NAME = "dbgenerador.sqlite";

    /** Tablas primarias que conforman la aplicacion:
     *  -Estas tablas se "cargan" al iniciar la aplicacion por primera vez
     *  -Contienen datos que permiten generar la encuesta
     *  -Tablas preguntas, subpreguntas, eventos, marco inicial, configuraciones, etc.
     * */

    /** Tabla Encuesta
     *  -contiene los datos generales de la encuesta
     *  @_id: id comun
     *  @titulo: nombre de la encuesta
     *  @tipo: tipo de encuesta (empresas 1, hogar 2)
     * */
    public static final String tablaencuestas = "encuestas";

    public static final String encuestas_id = "_id";
    public static final String encuestas_titulo = "titulo";
    public static final String encuestas_tipo = "tipo";


    /** Tabla Usuarios
     *  -usuarios que tienen acceso a la encuesta
     *  @_id: id comun
     *  @usuario: nickname del usuario de la encuesta
     *  @clave: contraseña del usuario
     *  @cargo_id: cargo del usuario (encuestador 1, supervisor 2, coordinador 3)
     *  @dni: dni del usuario
     *  @nombre: nombre del usuario
     *  @supervisor_id: supervisor del usuario
     *  @coordinador_id: coordinador del usuario
     * */

    public static final String tablausuarios = "usuarios";

    public static final String usuario_id = "_id";
    public static final String usuario_clave = "clave";
    public static final String usuario_cargo_id = "cargo_id";
    public static final String usuario_dni = "dni";
    public static final String usuario_nombre = "nombre";
    public static final String usuario_supervisor_id = "supervisor_id";
    public static final String usuario_coordinador_id = "coordinador_id";

    /** Tabla Marco
     *  -marco general de la encuesta
     *  @_id: id comun
     *  @anio: año

     * */

    public static final String tablamarco = "marco";
    public static final String tablamarco_hogar = "marco_hogar";
    public static final String tablacaratula = "Caratula";

    public static final String marco_id = "_id";
    public static final String marco_anio = "anio";
    public static final String marco_mes = "mes";
    public static final String marco_periodo = "periodo";
    public static final String marco_zona = "zona";
    public static final String marco_conglomerado = "conglomerado";
    public static final String marco_norden = "norden";
    public static final String marco_ccdd = "ccdd";
    public static final String marco_departamento = "departamento";
    public static final String marco_ccpp = "ccpp";
    public static final String marco_provincia = "provincia";
    public static final String marco_ccdi = "ccdi";
    public static final String marco_distrito = "distrito";
    public static final String marco_codccpp = "codccpp";
    public static final String marco_nomccpp = "nomccpp";
    public static final String marco_ruc = "ruc";
    public static final String marco_razon_social = "razon_social";
    public static final String marco_nombre_comercial = "nombre_comercial";
    public static final String marco_tipo_empresa = "tipo_empresa";
    public static final String marco_encuestador = "encuestador";
    public static final String marco_supervisor = "supervisor";
    public static final String marco_coordinador = "coordinador";
    public static final String marco_frente = "frente";
    public static final String marco_numero_orden = "numero_orden";
    public static final String marco_manzana_id = "manzana_id";
    public static final String marco_tipvia = "tipvia";
    public static final String marco_nomvia = "nomvia";
    public static final String marco_nropta = "nropta";
    public static final String marco_tipo_via = "tipo_via";
    public static final String marco_nombre_via = "nombre_via";
    public static final String marco_puerta = "puerta";
    public static final String marco_lote = "lote";
    public static final String marco_piso = "piso";
    public static final String marco_mza = "mza";
    public static final String marco_manzana = "manzana";
    public static final String marco_block = "block";
    public static final String marco_interior = "interior";
    public static final String marco_usuario_id = "usuario_id";
    public static final String marco_usuario_sup_id = "usuario_sup_id";
    public static final String marco_estado = "estado";
    public static final String marco_nombre = "nombre";
    public static final String marco_usuario = "usuario";
    public static final String marco_dni = "dni";


    public static final String tablacamposmarco = "campos_marco";

    public static final String campos_marco_id = "_id";
    public static final String campos_marco_var1 = "var1";
    public static final String campos_marco_var2 = "var2";
    public static final String campos_marco_var3 = "var3";
    public static final String campos_marco_var4 = "var4";
    public static final String campos_marco_var5 = "var5";
    public static final String campos_marco_var6 = "var6";
    public static final String campos_marco_var7 = "var7";
    public static final String campos_marco_peso1 = "peso1";
    public static final String campos_marco_peso2 = "peso2";
    public static final String campos_marco_peso3 = "peso3";
    public static final String campos_marco_peso4 = "peso4";
    public static final String campos_marco_peso5 = "peso5";
    public static final String campos_marco_peso6 = "peso6";
    public static final String campos_marco_peso7 = "peso7";
    public static final String campos_marco_nombre1 = "nombre1";
    public static final String campos_marco_nombre2 = "nombre2";
    public static final String campos_marco_nombre3 = "nombre3";
    public static final String campos_marco_nombre4 = "nombre4";
    public static final String campos_marco_nombre5 = "nombre5";
    public static final String campos_marco_nombre6 = "nombre6";
    public static final String campos_marco_nombre7 = "nombre7";


    public static final String tablafiltrosmarco = "filtros_marco";

    public static final String filtros_marco_id = "_id";
    public static final String filtros_marco_filtro1 = "filtro1";
    public static final String filtros_marco_filtro2 = "filtro2";
    public static final String filtros_marco_filtro3 = "filtro3";
    public static final String filtros_marco_filtro4 = "filtro4";
    public static final String filtros_marco_nombre1 = "nombre1";
    public static final String filtros_marco_nombre2 = "nombre2";
    public static final String filtros_marco_nombre3 = "nombre3";
    public static final String filtros_marco_nombre4 = "nombre4";




    /** Tabla Modulos
     *  -modulos que componen una actividad en la encuesta
     *  @id: id comun
     *  @numero: numero
     *  @titulo: titulo del modulo que ira en la parte superior
     *  @cabecera: texto que aparece en el menu navigation view como cabecera de la lista desplegable
     *  @tipo_actividad: 11,12,21,22,23 segun la actividad encuesta (caratula,empresa o vivienda,hogar,encuesta)
     * */

    public static final String tablamodulos = "modulos";

    public static final String modulos_id = "_id";
    public static final String modulos_numero = "numero";
    public static final String modulos_titulo = "titulo";
    public static final String modulos_cabecera = "cabecera";
    public static final String modulos_tipo_actividad = "tipo_actividad";



    /** Tabla Páginas
     *  -paginas que componen un modulo en la encuesta
     *  @id: id comun
     *  @numero: numero de pagina
     *  @modulo: id_modulo al que pertenece
     *  @nombre: id_modulo al que pertenece
     *  @tipo_actividad: 11,12,21,22,23 segun la actividad encuesta (caratula,empresa o vivienda,hogar,encuesta)
     *  @tipo_pagina: 1 o 2 segun sea con componente normal o scrolleable (visitas,cruds,etc)
     * */
    public static final String tablapaginas = "paginas";

    public static final String paginas_id = "_id";
    public static final String paginas_numero = "numero";
    public static final String paginas_modulo = "modulo";
    public static final String paginas_nombre = "nombre";
    public static final String paginas_tipo_actividad = "tipo_actividad";
    public static final String paginas_tipo_pagina = "tipo_pagina";
    public static final String paginas_tipo_guardado = "tipo_guardado";
    public static final String paginas_tabla_guardado = "tabla_guardado";


    /** Tabla Preguntas
     *  -paginas que componen un modulo en la encuesta
     *  @id: id de la pregunta
     *  @modulo: id_modulo al que pertenece
     *  @pagina: id_pagina al que pertenece
     *  @numero: número de pregunta
     *  @tipo_pregunta: 1 o 2 segun sea con componente normal o scrolleable (visitas,cruds,etc)
     *  @tipo_actividad: 11,12,21,22,23 segun la actividad encuesta (caratula,empresa o vivienda,hogar,encuesta)
     * */

    public static final String tablapreguntas = "preguntas";

    public static final String preguntas_id = "_id";
    public static final String preguntas_modulo = "modulo";
    public static final String preguntas_pagina = "pagina";
    public static final String preguntas_numero = "numero";
    public static final String preguntas_tipo_pregunta = "tipo_pregunta";
    public static final String preguntas_tipo_actividad = "tipo_actividad";
    public static final String preguntas_orden_flujo = "orden_flujo";

    public static final String tablavariable_flujo = "variable_flujo";
    public static final String variable_flujo_numero_flujo = "numero_flujo";
    public static final String variable_flujo_nombre_var = "nombre_var";
    public static final String variable_flujo_nombre_tabla = "nombre_tabla";
    public static final String variable_flujo_operador = "operador";

    public static final String tablaeventos = "eventos";

    public static final String eventos_id = "_id";
    public static final String eventos_idpag1 = "idpag1";
    public static final String eventos_idpreg1 = "idpreg1";
    public static final String eventos_idvar = "idvar";
    public static final String eventos_valor = "valor";
    public static final String eventos_idpag2 = "idpag2";
    public static final String eventos_idpreg2 = "idpreg2";
    public static final String eventos_accion = "accion";







    /** Tablas secundarias que conforman la aplicacion:
     *  -Estas tablas se "crean" iniciar la aplicacion por primera vez
     *  -Almacenan datos para el funcionamiento de la aplicacion en produccion.
     *  -Tablas de guardado de datos capturados
     * */


    /**Inicio Tablas Componentes **/
    public static final String tablacheckbox = "c_checkbox";
    /**InicioCampos**/
    public static final String checkbox_id = "_id";
    public static final String checkbox_modulo = "modulo";
    public static final String checkbox_numero = "numero";
    public static final String checkbox_pregunta = "pregunta";
    public static final String checkbox_id_tabla = "id_tabla";

    /**Fin Campos**/
    public static final String tablaedittext = "c_edittext";
    /**InicioCampos**/
    public static final String edittext_id = "_id";
    public static final String edittext_modulo = "modulo";
    public static final String edittext_numero = "numero";
    public static final String edittext_pregunta = "pregunta";
    public static final String edittext_id_tabla = "id_tabla";
    /**Fin Campos**/
    public static final String tablaformulario = "c_formulario";
    /**InicioCampos**/
    public static final String formulario_id = "_id";
    public static final String formulario_id_modulo = "id_modulo";
    public static final String formulario_id_numero = "id_numero";
    public static final String formulario_titulo = "titulo";
    public static final String formulario_id_tabla = "id_tabla";
    /**Fin Campos**/
    public static final String tablagps = "c_gps";
    /**InicioCampos**/
    public static final String gps_id = "_id";
    public static final String gps_modulo = "modulo";
    public static final String gps_numero = "numero";
    public static final String gps_var_lat = "var_lat";
    public static final String gps_var_long = "var_long";
    public static final String gps_var_alt = "var_alt";
    public static final String gps_id_tabla = "id_tabla";
    /**Fin Campos**/
    public static final String tablaradio = "c_radio";
    /**InicioCampos**/
    public static final String radio_id = "_id";
    public static final String radio_modulo = "modulo";
    public static final String radio_numero = "numero";
    public static final String radio_pregunta = "pregunta";
    public static final String radio_id_tabla = "id_tabla";
    /**Fin Campos**/
    public static final String tablaspcheckbox = "c_sp_checkbox";
    /**InicioCampos**/
    public static final String sp_checkbox_id = "_id";
    public static final String sp_checkbox_id_pregunta = "id_pregunta";
    public static final String sp_checkbox_subpregunta = "subpregunta";
    public static final String sp_checkbox_var_guardado = "var_guardado";
    public static final String sp_checkbox_var_especifique = "var_especifique";
    public static final String sp_checkbox_deshabilita = "deshabilita";
    /**Fin Campos**/
    public static final String tablaspedittext = "c_sp_edittext";
    /**InicioCampos**/
    public static final String sp_edittext_id = "_id";
    public static final String sp_edittext_id_pregunta = "id_pregunta";
    public static final String sp_edittext_subpregunta = "subpregunta";
    public static final String sp_edittext_var_input = "var_input";
    public static final String sp_edittext_tipo = "tipo";
    public static final String sp_edittext_longitud = "longitud";
    /**Fin Campos**/
    public static final String tablaspformulario = "c_sp_formulario";
    /**InicioCampos**/
    public static final String sp_formulario_id = "_id";
    public static final String sp_formulario_id_pregunta = "id_pregunta";
    public static final String sp_formulario_subpregunta = "subpregunta";
    public static final String sp_formulario_var_edittext = "var_edittext";
    public static final String sp_formulario_tipo_edittext = "tipo_edittext";
    public static final String sp_formulario_long_edittext = "long_edittext";
    public static final String sp_formulario_var_spinner = "var_spinner";
    public static final String sp_formulario_var_esp_spinner = "var_esp_spinner";
    public static final String sp_formulario_tipo_esp_spinner = "tipo_esp_spinner";
    public static final String sp_formulario_long_esp_spinner = "long_esp_spinner";
    public static final String sp_formulario_hab_esp_spinner = "hab_esp_spinner";
    public static final String sp_formulario_var_check_no = "var_check_no";
    public static final String sp_formulario_hab_edittext = "hab_edittext";
    public static final String sp_formulario_var_radio = "var_radio";
    public static final String sp_formulario_var_radio_o = "var_radio_o";
    public static final String sp_formulario_var_esp_radio = "var_esp_radio";

    /**Fin Campos**/
    public static final String tablaspradio = "c_sp_radio";
    /**InicioCampos**/
    public static final String sp_radio_id = "_id";
    public static final String sp_radio_id_pregunta = "id_pregunta";
    public static final String sp_radio_subpregunta = "subpregunta";
    public static final String sp_radio_var_input = "var_input";
    public static final String sp_radio_var_especifique = "var_especifique";
    /**Fin Campos**/
    public static final String tablaubicacion = "c_ubicacion";
    /**InicioCampos**/
    public static final String ubicacion_id = "_id";
    public static final String ubicacion_modulo = "modulo";
    public static final String ubicacion_numero = "numero";
    public static final String ubicacion_var_dep = "var_dep";
    public static final String ubicacion_var_prov = "var_prov";
    public static final String ubicacion_var_dist = "var_dist";
    public static final String ubicacion_id_tabla = "id_tabla";
    /**Fin Campos**/
    public static final String tablavisitasencuestador = "c_visitas_encuestador";
    /**InicioCampos**/
    public static final String visitas_encuestador_id = "_id";
    public static final String visitas_encuestador_modulo = "modulo";
    public static final String visitas_encuestador_numero = "numero";
    public static final String visitas_encuestador_var_numero = "var_numero";
    public static final String visitas_encuestador_var_dia = "var_dia";
    public static final String visitas_encuestador_var_mes = "var_mes";
    public static final String visitas_encuestador_var_anio = "var_anio";
    public static final String visitas_encuestador_var_hora_inicio = "var_hora_inicio";
    public static final String visitas_encuestador_var_min_inicio = "var_min_inicio";
    public static final String visitas_encuestador_var_hor_final = "var_hor_final";
    public static final String visitas_encuestador_var_min_final = "var_min_final";
    public static final String visitas_encuestador_var_prox_dia = "var_prox_dia";
    public static final String visitas_encuestador_var_prox_mes = "var_prox_mes";
    public static final String visitas_encuestador_var_prox_anio = "var_prox_anio";
    public static final String visitas_encuestador_var_prox_hora = "var_prox_hora";
    public static final String visitas_encuestador_var_prox_min = "var_prox_min";
    public static final String visitas_encuestador_var_resultado = "var_resultado";
    public static final String visitas_encuestador_var_final_resultado = "var_final_resultado";
    public static final String visitas_encuestador_var_final_dia = "var_final_dia";
    public static final String visitas_encuestador_var_final_mes = "var_final_mes";
    public static final String visitas_encuestador_var_final_anio = "var_final_anio";
    public static final String visitas_encuestador_id_tabla_visitas = "var_id_tabla_visitas";
    public static final String visitas_encuestador_id_tabla_resultado = "var_id_tabla_resultado";
    /**Fin Campos**/
    public static final String tablavisitassupervisor = "c_visitas_supervisor";
    /**InicioCampos**/
    public static final String visitas_supervisor_id = "_id";
    public static final String visitas_supervisor_modulo = "modulo";
    public static final String visitas_supervisor_numero = "numero";
    public static final String visitas_supervisor_var_numero = "var_numero";
    public static final String visitas_supervisor_var_dia = "var_dia";
    public static final String visitas_supervisor_var_mes = "var_mes";
    public static final String visitas_supervisor_var_anio = "var_anio";
    public static final String visitas_supervisor_var_hora_inicio = "var_hora_inicio";
    public static final String visitas_supervisor_var_min_inicio = "var_min_inicio";
    public static final String visitas_supervisor_var_hor_final = "var_hor_final";
    public static final String visitas_supervisor_var_min_final = "var_min_final";
    public static final String visitas_supervisor_var_resultado = "var_resultado";
    public static final String visitas_supervisor_var_final_resultado = "var_final_resultado";
    public static final String visitas_supervisor_var_final_dia = "var_final_dia";
    public static final String visitas_supervisor_var_final_mes = "var_final_mes";
    public static final String visitas_supervisor_var_final_anio = "var_final_anio";
    public static final String visitas_supervisor_id_tabla_visitas = "var_id_tabla_visitas";
    public static final String visitas_supervisor_id_tabla_resultado = "var_id_tabla_resultado";
    /**Fin Campos**/
    public static final String tablaOpcionSpinner = "opciones";
    /**InicioCampos**/
    public static final String OPCION_SPINNER_ID = "ID";
    public static final String OPCION_SPINNER_IDVARIABLE = "IDVARIABLE";
    public static final String OPCION_SPINNER_NDATO = "NDATO";
    public static final String OPCION_SPINNER_DATO = "DATO";
    /**Fin Campos**/
    /**Fin Tablas Componentes **/


    /** Clausulas WHERE * */
    public static final String clausula_where_id = "_id=?";
    public static final String clausula_where_encuestador = "encuestador=?";
    public static final String clausula_where_tipo_actividad = "tipo_actividad=?";
    public static final String clausula_where_tipo_actividad_activa = "tipo_actividad=? and estado=1";
    public static final String clausula_where_numero_modulo = "numero=?";
    public static final String clausula_where_numero_pagina = "numero=?";
    public static final String clausula_where_modulo_pagina = "modulo=?";
    public static final String clausula_where_pagina_pregunta = "pagina=?";
    public static final String clausula_where_pagina_pregunta_activa = "pagina=? and estado=1";
    public static final String clausula_where_id_pregunta = "id_pregunta=?";
    public static final String clausula_where_id_pregunta_activa = "id_pregunta=? and estado=1";
    public static final String WHERE_CLAUSE_ID_VARIABLE = "IDVARIABLE=?";

    public static final String WHERE_CLAUSE_ANIO = "anio=?";
    public static final String WHERE_CLAUSE_MES = "mes=?";
    public static final String WHERE_CLAUSE_PERIODO = "periodo=?";
    public static final String WHERE_CLAUSE_ZONA = "zona=?";
    public static final String WHERE_CLAUSE_USUARIO_ID = "usuario_id=?";
    public static final String WHERE_CLAUSE_USUARIO = "usuario=?";
    public static final String WHERE_CLAUSE_USUARIO_SUP_ID = "usuario_sup_id=?";

    public static final String clausula_where_nombre_flujo = "nombre_flujo=?";

    // Tabla Infotablas
    public static final String tablainfo_tablas = "info_tablas";
    public static final String tablainfo_flujos = "info_flujos";

    public static final String info_tablas_id = "_id";
    public static final String info_tablas_nombre = "nombre";

    public static final String flujo_nombre_flujo = "nombre_flujo";
    public static final String flujo_orden_flujo_ini = "orden_flujo_ini";
    public static final String flujo_numero_var = "numero_var";
    public static final String flujo_go_to = "go_to";
    public static final String flujo_numero_go_to_max = "numero_go_to_max";


    // Tabla Variables
    public static final String tablavariables = "variables";

    public static final String variables_id = "_id";
    public static final String variables_id_tabla = "id_tabla";
    public static final String variables_id_modulo = "id_modulo";
    public static final String variables_id_pagina = "id_pagina";
    public static final String variables_id_pregunta = "id_pregunta";
    public static final String variables_pk = "pk";

    public static final String clausula_where_idtabla_pk = "id_tabla=? and pk=?";

    public static final String clausula_where_id_vivienda = "id_vivienda=?";
    public static final String clausula_where_id_hogar = "id_vivienda=? and id_hogar=?";
    public static final String clausula_where_id_residente = "id_vivienda=? and id_hogar=? and id_residente=?";


    public static final String clausula_where_nombre_var = "nombre_var=?";
}
