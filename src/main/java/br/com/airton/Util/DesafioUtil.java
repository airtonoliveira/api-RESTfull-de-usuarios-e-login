package br.com.airton.Util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import br.com.airton.model.Usuario;

public class DesafioUtil {

    public static final String FORMATO_DD_MM_YYYY = "dd/MM/yyyy HH:mm";

    public  static boolean isCamposObrigatoriosUsuarioPreenchidos(Usuario usuario){
        System.out.println(usuario.toString());
        if(isNullOrEmpty(usuario.getEmail())
                || isNullOrEmpty(usuario.getFirstName())
                ||isNullOrEmpty(usuario.getLastName())
                ||isNullOrEmpty(usuario.getPassword())
                ||usuario.getPhones()==null
                ||usuario.getPhones().isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public static boolean isCamposObrigatoriosAutenticacaoPreenchidos(UserSS usuario){
        System.out.println(usuario.toString());
        if(isNullOrEmpty(usuario.getEmail())
                || isNullOrEmpty(usuario.getPassword())){
            return false;
        }else{
            return true;
        }
    }

    public  static boolean isNullOrEmpty(String param){
        if(param ==null || param.equals("")){
            return true;
        }

        return false;
    }

    public static String formatar(LocalDateTime data) {

        String dataFormatada = null;

        try {
            if(data==null){
                return "";
            }
            SimpleDateFormat formatador = new SimpleDateFormat(FORMATO_DD_MM_YYYY);
            formatador.setLenient(true);
            dataFormatada = formatador.format(data);

        } catch (Exception e) {

        }

        return dataFormatada;
    }

    public static Date addMinutesToDate(int minutes, Date beforeTime){
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }
    
    public static LocalDateTime getActualDateTimeBrazilTimeZone(){
	    Instant nowUtc = Instant.now();
		ZoneId americaRecife = ZoneId.of("America/Recife");
		ZonedDateTime now = ZonedDateTime.ofInstant(nowUtc, americaRecife);
		return now.toLocalDateTime();
    }
}
