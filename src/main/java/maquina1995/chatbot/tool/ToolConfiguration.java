package maquina1995.chatbot.tool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ToolConfiguration {

	@Tool(description = "get the current date with hours and minutes")
	String obtenerFechaActual() {
		LocalDateTime now = LocalDateTime.now();

		System.out.println("fecha actual: " + now);

		return now.atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
	}

	@Tool(description = "get the current temperature for the specified city")
	String temperaturaActual(@ToolParam(description = "city") String city) {

		String temperature;

		switch (city) {
		case "Madrid" -> temperature = "-10º";
		default -> temperature = "20º";
		}

		return temperature;
	}

    @Tool(description = "Set a user alarm for the given time")
    void crearAlarma(@ToolParam(description = "Time in ISO-8601 format") String time) {
        LocalDateTime alarmTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("Alarm set for " + alarmTime);
    }
    
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