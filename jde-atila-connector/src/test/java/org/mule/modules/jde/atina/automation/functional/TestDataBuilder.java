package org.mule.modules.jde.atina.automation.functional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataBuilder {

    private static final String VALORES_GETVALORID = "ValorById_valorByIdGet";
    private static final String LIMITE_DE_CONSUMO = "Limiteconsumo_limiteconsumoGet";
    private static final String POST_ARTICULO = "Articulo_articuloPost";
    private static final String DELETE_ARTICULO = "ArticuloById_articuloByIdDelete";
    private static final String GET_ARTICULO = "ArticuloById_articuloByIdGet";
    private static final String GET_IMAGE = "ObtenerImagen_obtenerImagenGet";
    private static final String PUT_ARTICULO_BY_ID = "ArticuloById_articuloByIdPut";
    private static final String POST_EQUIVALENCIA = "Equivalencia_equivalenciaPost";
    private static final String ARTICULO_GET = "Articulo_articuloGet";
    private static final String PRECIO_Y_STOCK = "ConsultaStockYPrecios_consultaStockYPreciosGet";
    private static final String GET_EQUIVALENCIA = "Equivalencia_equivalenciaGet";
    private static final String ADD_CUSTOMER = "Clienteecommerce_clienteecommercePost";
    private static final String DELETE_CUSTOMER = "ClienteecommerceById_clienteecommerceByIdDelete";
    private static final String POST_ORDER = "Operacionecommerce_operacionecommercePost";

    public static String getPostOrderEntityType() {
        return POST_ORDER;
    }

    public static Map<String, Object> getPostOrderEntityData() {

        final Map<String, Object> returnValue = new HashMap<String, Object>();

        Map<String, Object> tmpHeader = new java.util.HashMap();

        // CODIGO DE LA PLATAFORMA EN DRAGONFISH
        tmpHeader.put("ecommerce", "SHOPIFY");

        // NRO DE LA ORDEN EN EL ECOMMERCE
        tmpHeader.put("numero", "#1008");

        // NRO DE CLIENTE DEL ECOMMERCE EN DRAGON
        tmpHeader.put("clienteEcom", "2209931312");

        // DATOS DEL ENVIO. MAXIMO 40 CARACTERES
        tmpHeader.put("datosEnvio", "Dato Envio");

        // DATOS DEL PAGO. MAXIMO 40 CARACTERES
        tmpHeader.put("datosPago", "Dato Pago");

        // DETERMINA SI LA ORDEN HA SIDO PAGADA
        tmpHeader.put("pagado", true);

        // ID DE LA OPERACION EN EL ECOMMERCE
        tmpHeader.put("codigo", "1254658474033");

        // FECHA DateTime de la operacion. El input es "2015-07-04T21:00:00"

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse("2015-07-04T21:00:00", formatter);

        tmpHeader.put("fecha", java.util.Date.from(localDate.toLocalDate()
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()));

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

        tmpHeader.put("hora", localDate.format(formatterTime));

        // OBSERVACION
        tmpHeader.put("obs", "Observacion");

        // TOTAL DE LA OPERACION
        tmpHeader.put("total", 200.0);

        // ARRAY QUE CONTENDRA CADA UNA DE LAS LINEAS

        List<Object> tmpDetail = new java.util.ArrayList<Object>();

        // LINEA 1

        Map<String, Object> detalleOrdenObj = new java.util.HashMap();

        // Codigo es el ID del producto en el Ecommerce
        detalleOrdenObj.put("codigo", "2794233528368");

        // Descripcion del producto en Ecommerce
        detalleOrdenObj.put("descripcion", "REMERA HOMBRE DRY FIT CUELLO REDONDO A23");

        // ID de la linea en el Ecommerce
        detalleOrdenObj.put("idPublicacion", "2794233528369");

        // ID del producto de Dragon
        detalleOrdenObj.put("articulo", "00100101");

        // Descripcion del Producto de Dragon segun Ecommerce
        detalleOrdenObj.put("articuloDetalle", "REMERA HOMBRE DRY FIT CUELLO REDONDO A23 - 1 Azul");

        detalleOrdenObj.put("color", "BLACK");

        detalleOrdenObj.put("colorDetalle", "Negro");

        detalleOrdenObj.put("talle", "1");

        detalleOrdenObj.put("cantidad", 1);

        detalleOrdenObj.put("precio", 66.0);

        detalleOrdenObj.put("monto", 66.0);

        detalleOrdenObj.put("nroItem", 1);

        tmpDetail.add(detalleOrdenObj);

        // LINEA 2

        Map<String, Object> detalleOrdenObj2 = new java.util.HashMap();

        // Codigo es el ID del producto en el Ecommerce
        detalleOrdenObj2.put("codigo", "2794233528368");

        // Descripcion del producto en Ecommerce
        detalleOrdenObj2.put("descripcion", "REMERA HOMBRE DRY FIT CUELLO REDONDO A23");

        // ID de la linea en el Ecommerce
        detalleOrdenObj2.put("idPublicacion", "2794233528368");

        // ID del producto de Dragon
        detalleOrdenObj.put("articulo", "00100101");

        // Descripcion del Producto de Dragon segun Ecommerce
        detalleOrdenObj2.put("articuloDetalle", "REMERA HOMBRE DRY FIT CUELLO REDONDO A23 - 1 Azul");

        detalleOrdenObj2.put("color", "BLACK");

        detalleOrdenObj2.put("colorDetalle", "Negro");

        detalleOrdenObj2.put("talle", "1");

        detalleOrdenObj2.put("cantidad", 1);

        detalleOrdenObj2.put("precio", 66.0);

        detalleOrdenObj2.put("monto", 66.0);

        detalleOrdenObj2.put("nroItem", 2);

        tmpDetail.add(detalleOrdenObj2);

        tmpHeader.put("publicacionDetalle", tmpDetail);

        returnValue.put("request", tmpHeader);

        return returnValue;

    }

    public static String getAddCustomerEntityType() {
        return ADD_CUSTOMER;
    }

    public static Map<String, Object> getAddCustomerEntityData() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("codigo", "00100101");
        entityData.put("cuentaecommerce", "SHOPIFY");
        entityData.put("nroDoc", "");
        entityData.put("mail", "mail@gmail.com");
        entityData.put("apellido", "apellido");
        entityData.put("tipoDoc", "");
        entityData.put("clienteEcommerce", "DEMO");
        entityData.put("clienteDragon", "");
        entityData.put("telefono", "");
        entityData.put("nombre", "nombre");
        entityData.put("observacion", "");

        Map<String, Object> request = new HashMap<String, Object>();
        request.put("request", entityData);

        return request;

    }

    public static String getDeleteCustomerEntityType() {
        return DELETE_CUSTOMER;
    }

    public static Map<String, Object> getDeleteCustomerEntityData() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("id", "00100101");
        return entityData;
    }

    public static String getEquivalenciaEntityType() {
        return GET_EQUIVALENCIA;
    }

    public static Map<String, Object> getEquivalenciaEntityData() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("articulo", "00100101");
        entityData.put("color", "");
        entityData.put("talle", "");
        return entityData;
    }

    public static String getPrecioYStock() {
        return PRECIO_Y_STOCK;
    }

    public static Map<String, Object> getPrecioYStockEntityData() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("query", "00100101");
        entityData.put("lista", "LISTA4");
        return entityData;
    }

    public static String getArticuloEntityType() {
        return ARTICULO_GET;
    }

    public static Map<String, Object> getArticuloEntityData() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("limit", "10");
        entityData.put("noPublicarEnEcommerce", false);
        entityData.put("page", "0");
        entityData.put("sort", "-informacionAdicional.FechaModificacionFW,-informacionAdicional.HoraModificacionFW");
        return entityData;
    }

    public static String getPostEquivalenciaEntityType() {
        return POST_EQUIVALENCIA;
    }

    public static Map<String, Object> postEquivalenciaDEntityData() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        Map<String, Object> subEntityData = new HashMap<String, Object>();
        subEntityData.put("articulo", "92455890");
        subEntityData.put("colorX", "BLACK");
        subEntityData.put("codigo", "000000000000");
        entityData.put("request", subEntityData);
        return entityData;
    }

    public static String getPutArticuloByIDEntityType() {
        return PUT_ARTICULO_BY_ID;
    }

    public static Map<String, Object> putArticuloByIDEntityData() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("id", "92455890");
        Map<String, Object> subEntityData = new HashMap<String, Object>();
        subEntityData.put("codigo", "92455890");
        subEntityData.put("observacion", "Demo Observacion");
        entityData.put("request", subEntityData);
        return entityData;
    }

    public static String getImageEntityType() {
        return GET_IMAGE;
    }

    public static Map<String, Object> getImageEntityData() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("ruta", ".\\IMAGENES\\DEMO\\REMERA-AZUL.PNG");
        return entityData;
    }

    public static String getValoresGetValorIdEntityType() {
        return VALORES_GETVALORID;
    }

    public static Map<String, Object> getValoresGetValorIdEntityData() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("id", "AM");
        return entityData;
    }

    public static String getLimiteDeConsumoEntityType() {
        return LIMITE_DE_CONSUMO;
    }

    public static Map<String, Object> getLimiteDeConsumoEntityData() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("limit", 10);
        entityData.put("page", 0);
        return entityData;
    }

    public static String getArticuloByID() {
        return GET_ARTICULO;
    }

    public static Map<String, Object> getArticuloByIDEntityData() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("id", "00100101");
        return entityData;
    }

    public static Map<String, Object> getArticuloByIDEntityDataInvalidArticulo() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("id", "00100101A");
        return entityData;
    }

    public static Map<String, Object> getArticuloByIDEntityDataInvalidParameter() {
        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("idxxx", "00100101");
        return entityData;
    }

    public static String postArticuloEntityType() {
        return POST_ARTICULO;
    }

    public static Map<String, Object> postArticulo() {

        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("descripcion", "Str1");
        entityData.put("observacion", "Str2");
        entityData.put("codigo", "618354");
        entityData.put("porcentajeImpuestoInterno", 10);
        entityData.put("noComercializable", new Date());
        entityData.put("comportamiento", 1);
        entityData.put("restringirDescuentos", true);
        entityData.put("alto", 22);

        Map<String, Object> request = new HashMap<String, Object>();
        request.put("request", entityData);

        return request;
    }

    public static String deleteArticuloById() {
        return DELETE_ARTICULO;
    }

    public static Map<String, Object> deleteArticulo() {

        Map<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("id", "618354");
        return entityData;

    }

}
