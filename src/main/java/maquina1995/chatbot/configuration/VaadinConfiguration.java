package maquina1995.chatbot.configuration;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;

/**
 * Clase usada para la configuracion de vaadin
 * 
 * Haciendo que extienda de AppShellConfigurator hacemos la configuracion de esta clase efectiva paa toda la aplicacion
 * 
 * el <code>@Push</code> sirve para activar el Server Push" (Comunicación Servidor a Cliente) basicamente es la manera de que cuando haya un cambio en la interfaz de vaadin en el servidor se envie
 * ese cambio de estado a los clientes, esto es usado para el chatbot que tenemos con programacion reactiva que el TextArea se va populando letra a letra segun el contenedor de docker va enviando informacion
 * 
 * De esta manera no se tiene que esperar a que se genere toda la repsuesta por parte del chatbot sino que cuando el genera partes de la respuesta esta es enviada directamente a los clientes
 * 
 */
@Push 
public class VaadinConfiguration implements AppShellConfigurator {
}