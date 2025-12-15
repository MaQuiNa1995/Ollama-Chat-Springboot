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

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route("/chat")
public class MainWindow extends VerticalLayout {
	

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
		promptUsuarioText.addKeyDownListener(Key.ENTER, event -> {
			String promptUsuario = promptUsuarioText.getValue();
			conversacionChatTextArea.clear();
			if (StringUtils.isNotEmpty(promptUsuario)) {
				UI ui = UI.getCurrent();
				chatClient.prompt(promptUsuario)
					.tools(toolConfiguration)
					
					.stream()
					.content()
					.subscribe(nuevoMensaje -> {
						ui.access(() -> {
							String contenidoActual = conversacionChatTextArea.getValue();
							conversacionChatTextArea.setValue(contenidoActual + nuevoMensaje);
						});
				}, error -> ui.access(() -> conversacionChatTextArea.setValue("Error en el stream: " + error.getMessage())));
			}
		});

		add(conversacionChatTextArea, encabezadoPrompt, promptUsuarioText);
	}
}
