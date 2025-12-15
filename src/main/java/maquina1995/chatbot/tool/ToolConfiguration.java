package maquina1995.chatbot.tool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Clase usada para englobar ciertas utilidades o herramientas para que nuestro modelo de LLM pueda usar para dar respuestas a usuarios
 * 
 * Esto es util cuando por ejemplo no queremos que el LLM sea quien genere las respuestas a ciertas preguntas del usuario, especialmente util cuando por ejemplo tenemos apis externas como la del tiempo meteorologico
 * en el que un LLM basico sin acceso a internet o sin datos meteorologicos puede dar informacion actual precisa
 * 
 * Debido al LLM usado (mistral:latest) si las descripciones no están en inglés puede no dar el resultado esperado o funcionar bien
 * 
 * Funciona de la siguiente manera:
 * 
 * usuario hace pregunta al chatbot
 * SpringIA evalua si esa pregunta puede resolverse con las herramientas configuradas
 * Si es el caso envía el resultado de la ejecucion de estas herramientas al chatbot para darle contexto
 * El chatbot usa ese contexto para poder dar una respuesta al usuario con datos actualizados
 * 
 */
@Component
public class ToolConfiguration {

	/**
	 * En este caso le damos la habilidad al LLM de poder responder o saber cual es la hora actual
	 * 
	 * @return hora actual en formato string
	 */
	@Tool(description = "get the current date with hours and minutes")
	String obtenerFechaActual() {
		LocalDateTime now = LocalDateTime.now();

		System.out.println("fecha actual: " + now);

		return now.atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
	}

	/**
	 * En este caso le damos la habilidad al LLM de saber la temperatura de cierta ciudad que el usuario haya especificado
	 * 
	 * @param city ciudad elegida por el usuario
	 * @return temperatura en formato String
	 */
	@Tool(description = "get the current temperature for the specified city")
	String temperaturaActual(@ToolParam(description = "city") String city) {

		String temperature;

		switch (city) {
		case "Madrid" -> temperature = "-10º";
		default -> temperature = "20º";
		}

		return temperature;
	}

	/**
	 * Aqui tenemos un ejemplo en el que cuando el usuario dice por ejemplo que quiere hacer una alarma para dentro de 10 minutos usa 2 herramientas:
	 * - {@link ToolConfiguration#obtenerFechaActual()} para la hora actual
	 * - {@link ToolConfiguration#crearAlarma(String)} para crear la alarma
	 * 
	 * primero obtiene la hora actual y le suma 10 minutos y le pasa a su vez eso a crear la alarma
	 * 
	 * @param time hora en formato String de la alarma
	 */
    @Tool(description = "Set a user alarm for the given time")
    void crearAlarma(@ToolParam(description = "Time in ISO-8601 format") String time) {
        LocalDateTime alarmTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("Alarm set for " + alarmTime);
    }
    
    /**
     * Aqui tenemos el mismo ejemplo que en temperatura pero en este caso con el <code>returnDirect = true</code> le decimos a que directamente el resultado de la herramienta se envie al usuario sin
     * pasar por el LLM esto agiliza el procesamiento en los casos en los que necesitemos dar informacion sin procesar por el LLM
     * 
     * @param city ciudad elegida por el usuario
     * @return humedad en formato String
     */
	@Tool(description = "get the current humidity for the specified city", returnDirect = true)
	String humedadActual(@ToolParam(description = "city") String city) {

		String humidity;

		switch (city) {
		case "Madrid" -> humidity = "90%";
		default -> humidity = "10%";
		}

		return humidity;
	}

}