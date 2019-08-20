/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.jdeconnector.internal;

import com.atina.jdeconnectorservice.JDEConnectorService;
import com.jdedwards.base.datatypes.SqlDate;
import com.jdedwards.system.connector.dynamic.callmethod.ExecutableMethod;
import com.jdedwards.system.connector.dynamic.spec.source.BSFNParameter;
import com.atina.jdeconnectorservice.exception.JDESingleBSFNException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class JDEConverter {
     
    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);
    
    public static void convertServiceInputDataToJDEFormatToJDEDate(String entityType, Map<String, Object> entityData, ExecutableMethod callobject, BSFNParameter parameter) {

        Object value = entityData.get(parameter.getName());

        boolean valueConverted = false;

        if (value instanceof java.util.Calendar) {

            java.util.Calendar calendarDate = (java.util.Calendar)value;

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            String jdeDateFormat = format.format(calendarDate.getTime());

            callobject.setValue(parameter.getName(), jdeDateFormat);

            valueConverted = true;

        }

        if (value instanceof LocalDate) {

            LocalDate date = (LocalDate)value;

            String jdeDateFormat = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            callobject.setValue(parameter.getName(), jdeDateFormat);

            valueConverted = true;

        }

        if (value instanceof LocalDateTime) {

            LocalDateTime date = (LocalDateTime)value;

            String jdeDateFormat = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            callobject.setValue(parameter.getName(), jdeDateFormat);

            valueConverted = true;

        }

        if (value instanceof String) {

            callobject.setValue(parameter.getName(), value);

            valueConverted = true;

        }

        if (!valueConverted)
        {
            String msg = "Error converting " + value.getClass().getName() + " to JDE Date";

            logger.error(msg);

            throw new JDESingleBSFNException(formatBSFNErrorMessage(entityType, entityData, msg));

        }

    }
    
    public static void convertServiceInputDataToJDEUtime(String entityType, Map<String, Object> entityData, ExecutableMethod callobject, BSFNParameter parameter) {

        Object value = entityData.get(parameter.getName());

        boolean valueConverted = false;

        if (value instanceof java.util.Calendar) {

            java.util.Calendar calendarDate = (java.util.Calendar)value;

            TimeZone tz = TimeZone.getTimeZone("GMT");

            calendarDate.setTimeZone(tz);

            callobject.setValue(parameter.getName(), calendarDate);

            valueConverted = true;

        }

        if (value instanceof java.time.LocalDate) {

            java.time.LocalDate localDate = (java.time.LocalDate)value;

            LocalDateTime localDateTime1 = localDate.atStartOfDay();

            GregorianCalendar gc = GregorianCalendar.from(ZonedDateTime.of(localDateTime1, ZoneId.systemDefault()));

            @SuppressWarnings("static-access")
            Calendar calendar = gc.getInstance();

            TimeZone tz = TimeZone.getTimeZone("GMT");

            calendar.setTimeZone(tz);

            calendar.setTime(gc.getTime());

            callobject.setValue(parameter.getName(), calendar);

            valueConverted = true;

        }

        if (value instanceof java.time.LocalDateTime) {

            java.time.LocalDateTime localDateTimeTmp = (java.time.LocalDateTime)value;

            GregorianCalendar gc = GregorianCalendar.from(ZonedDateTime.of(localDateTimeTmp, ZoneId.systemDefault()));

            @SuppressWarnings("static-access")
            Calendar calendar = gc.getInstance();

            TimeZone tz = TimeZone.getTimeZone("GMT");

            calendar.setTimeZone(tz);

            calendar.setTime(gc.getTime());

            callobject.setValue(parameter.getName(), calendar);

            valueConverted = true;

        }

        if (value instanceof String) {

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);

            TimeZone tz = TimeZone.getTimeZone("GMT");

            Calendar calendarDate = Calendar.getInstance();

            try {

                calendarDate.setTimeZone(tz);

                calendarDate.setTime(sdf.parse((String)value));

            } catch (ParseException e) {

                throw new JDESingleBSFNException(formatBSFNErrorMessage(entityType, entityData, e.getMessage()), e);

            }

            callobject.setValue(parameter.getName(), calendarDate);

            valueConverted = true;

        }

        if (!valueConverted)
        {
            String msg = "Error converting " + value.getClass().getName() + " to JDE UTIME";

            logger.error(msg);

            throw new JDESingleBSFNException(formatBSFNErrorMessage(entityType, entityData, msg));

        }

    }

    public static void convertServiceInputDataToJDEFormatToMathNumeric(String entityType, Map<String, Object> entityData, ExecutableMethod callobject, BSFNParameter parameter) {

        boolean valueConverted = false;

        Object value = entityData.get(parameter.getName());

        if (value instanceof Double) {

            Double valueL = (Double)value;

            callobject.setValue(parameter.getName(), valueL.toString());

            valueConverted = true;

        }

        if (value instanceof Long) {

            Long valueL = (Long)value;

            callobject.setValue(parameter.getName(), valueL.toString());

            valueConverted = true;

        }

        if (value instanceof BigInteger) {

            BigInteger valueB = (BigInteger)value;

            Long valueL = valueB.longValue();

            callobject.setValue(parameter.getName(), valueL.toString());

            valueConverted = true;

        }

        if (value instanceof BigDecimal) {

            BigDecimal valueB = (BigDecimal)value;

            callobject.setValue(parameter.getName(), valueB.toString());

            valueConverted = true;

        }

        if (value instanceof String) {

            String valueS = (String)value;

            callobject.setValue(parameter.getName(), valueS);

            valueConverted = true;

        }

        if (value instanceof Integer) {

            Integer valueI = (Integer)value;

            callobject.setValue(parameter.getName(), valueI.toString());

            valueConverted = true;

        }

        if (!valueConverted)
        {
            String msg = "Error converting " + value.getClass().getName() + " to MathNumeric";

            logger.error(msg);

            throw new JDESingleBSFNException(formatBSFNErrorMessage(entityType, entityData, msg));

        }

    }

    public static void convertServiceInputDataToJDEFormatToJDEInt(String entityType, Map<String, Object> entityData, ExecutableMethod callobject, BSFNParameter parameter) {

        boolean valueConverted = false;

        Object valueToConvert = entityData.get(parameter.getName());

        if (valueToConvert instanceof Long) {

            Long valueL = (Long)valueToConvert;

            Integer iValue = valueL.intValue();

            callobject.setValue(parameter.getName(), iValue.toString());

            valueConverted = true;

        }

        if (valueToConvert instanceof BigInteger) {

            BigInteger valueB = (BigInteger)valueToConvert;

            Integer iValue = valueB.intValue();

            callobject.setValue(parameter.getName(), iValue.toString());

            valueConverted = true;

        }

        if (valueToConvert instanceof String) {

            String valueS = (String)valueToConvert;

            callobject.setValue(parameter.getName(), valueS);

            valueConverted = true;

        }

        if (valueToConvert instanceof Integer) {

            Integer valueI = (Integer)valueToConvert;

            callobject.setValue(parameter.getName(), valueI.toString());

            valueConverted = true;

        }

        if (!valueConverted)
        {
            String msg = "Error converting " + valueToConvert.getClass().getName() + " to Int";

            logger.error(msg);

            throw new JDESingleBSFNException(formatBSFNErrorMessage(entityType, entityData, msg));

        }

    }
    
    private static String formatBSFNErrorMessage(String entityType, Map<String, Object> entityData, String errorMessage) {

        StringBuilder message = new StringBuilder();

        message.append("BSFN: ");
        message.append(entityType);
        message.append(" has the following error: [");
        message.append(errorMessage);
        message.append("] ");

        if (entityData != null && !entityData.keySet()
            .isEmpty()) {

            message.append("Parameters: ");

            for (String parameter : entityData.keySet()) {

                message.append("[");
                message.append(parameter);
                message.append(":");

                if (parameter != null && entityData.get(parameter) != null) {
                    message.append(entityData.get(parameter));
                } else {
                    message.append("null");
                }

                message.append("]");

            }

        } else {
            message.append(" without Parameters.");
        }

        return message.toString();

    }
    
     public static String convertJDEDataToMUleFormatFromChar(Object value) {

        Character characterJDE = (Character)value;

        if (Character.isLetterOrDigit(characterJDE)) {
            return characterJDE.toString();

        } else {
            return " ";
        }

    }
     
     
     public static  BigDecimal convertJDEDataToMUleFormatFromMathNumeric(Object value) {

        if (value instanceof com.jdedwards.system.lib.MathNumericImpl) {

            com.jdedwards.system.lib.MathNumericImpl mnMath = (com.jdedwards.system.lib.MathNumericImpl)value;

            return mnMath.asBigDecimal();

        }

        if (value instanceof BigDecimal) {

            return (BigDecimal)value;

        }

        return BigDecimal.valueOf(0L);

    }

    public static  LocalDate convertJDEDataToMUleFormatFromJDEDate(Object value) {

        if (value instanceof SqlDate) {

            SqlDate jdeDate = (SqlDate)value;

            return jdeDate.toLocalDate();

        }

        return null;

    }

    public static LocalDateTime convertJDEDataToMUleFormatFromJDEUTime(Object value) {

        if (value instanceof java.util.Calendar) {

            java.util.Calendar calendar = (java.util.Calendar)value;

            return toLocalDateTime(calendar);

        } else if (value instanceof com.jdedwards.base.datatypes.JDECalendar) {

            com.jdedwards.base.datatypes.JDECalendar calendarDate = (com.jdedwards.base.datatypes.JDECalendar)value;

            Calendar calendar = calendarDate.convertToSqlDate().getCalendar();

            return toLocalDateTime(calendar);

        }

        return null;

    }
    
    private static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }
     
     
    
}
