package maquina1995.chatbot.front;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;

import maquina1995.chatbot.tool.ToolConfiguration;

/**
 * Clase que crea la ventana principal de chat en la ruta htttp:localhost:8080/chat y hace que por cada cliente se cree una instancia de esta clase es decir ya no es singleton sino prototype
 * 
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route("/chat")
public class MainWindow extends VerticalLayout {

	/**
	 * Constructor de la ventana con parametros inyectados del contexto de spring
	 * 
	 * @param chatClient objeto usado para la interaccion con el contenedor docker de ollama (chatbot)
	 * @param toolConfiguration objeto usado para usar el toolCalling con las utilidades explicadas en la clase {@link ToolConfiguration}
	 */
	public MainWindow(ChatClient chatClient, ToolConfiguration toolConfiguration) {

		setSizeFull();
		setSpacing(false);
		setPadding(false);
		getElement().getStyle().set("background-color", "black");

		TextArea conversacionChatTextArea = new TextArea();
		conversacionChatTextArea.setReadOnly(true);
		conversacionChatTextArea.setWidthFull();
		conversacionChatTextArea.getStyle().set("background-color", "grey");
		
		setFlexGrow(1.0, conversacionChatTextArea);
		
		NativeLabel encabezadoPrompt = new NativeLabel("¿ Que necesitas ?");
		encabezadoPrompt.getElement().getStyle().set("background-color", "black");
		encabezadoPrompt.setWidthFull();
		
		TextField promptUsuarioText = new TextField();
		promptUsuarioText.getElement().getStyle().set("background-color", "grey");
		promptUsuarioText.setWidthFull();
		
		/**
		 * Evento que se activa cuando se presiona enter
		 */
		promptUsuarioText.addKeyDownListener(Key.ENTER, event -> {
			String promptUsuario = promptUsuarioText.getValue();
			// Limpiamos el textArea
			conversacionChatTextArea.clear();
			// Si el textField no esta vacio
			if (StringUtils.isNotEmpty(promptUsuario)) {
				UI ui = UI.getCurrent();
				chatClient.prompt(promptUsuario)
					// aqui le decimos al chatClient que use las herramientas que hemos creado en ToolConfiguration
					.tools(toolConfiguration)
					.stream()
					.content()
					// usamos el subscribe para que cuando se vaya creando letra a letra la respuesta por parte del chatbot
					.subscribe(nuevoMensaje -> {
						// Se vaya pasando al textArea 
						ui.access(() -> {
							// Aqui cogemos lo que había antes y añadimos la nueva info
							String contenidoActual = conversacionChatTextArea.getValue();
							conversacionChatTextArea.setValue(contenidoActual + nuevoMensaje);
						});
					},
					// En el caso de que se produzca un error metemos un mensaje
					error -> ui.access(() -> conversacionChatTextArea.setValue("Error en el stream: " + error.getMessage())));
			}
		});

		add(conversacionChatTextArea, encabezadoPrompt, promptUsuarioText);
	}
}
