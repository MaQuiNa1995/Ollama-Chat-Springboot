# Ollama-Chat-Springboot

## Docker
Este proyecto muestra la configuracion y ejecucion de un java interactuando con un contenedor docker de ollama con un modelo de lenguaje embebido

En este caso usamos mistral:latest

Para hacer funcionar en local este proyecto necesitas docker y ejecutar el docker compose que está en la raiz del proyecto

`docker-compose up` desde la consola

Cuando está corriendo en docker usa este comando para conectarte a la consola del contenedor:

`docker exec -it <Nombre_del_Contenedor> bash` 

para saber el nombre del contenedor puedes verlo con `docker ps` en nuestro caso se llama `ollama`

puedes acceder a la consola del contendor y ejecutar:

`ollama pull nombreModelo`

Para descargar el modelo para posteriormente usarlo

Aqui tienes algunos ejemplos de modelos:          
 - mistral:latest 
 - qwen3:30b-a3b  
 - qwen3:30b      
 - qwen3:32b      
 - qwen3:14b      
 - qwen3:8b       
 - gemma3:27b     
 - qwen2.5:7b     
 - deepseek-r1:32b
 - deepseek-r1:14b
 - deepseek-r1:7b 
 - qwen2.5:14b    
 - gemma2:9b      
 - gemma:7b       
 - llama3.1:8b    
 - llama3.2:3b

El lenguaje mas liviano que encontré es mistral:latest

puedes ejecutar un promp directamente en la consola del contendor:

`ollama run nombreModelo "prompt"`

# Java

Si dejas la url por defecto no hace falta definir ninguna variable en el `application.properties`

## Un ejemplo de ejecucion:

```
Formula tu pregunta: Genera un texto con 3 frase para probar el streaming de ChatClient de springboot
 1. "Hola, bienvenido al ChatBot de SpringBoot! Puede comenzar a chatar conmigo de inmediato."

2. "Por favor, escriba su mensaje y estaré encantado de responderle lo antes posible."

3. "Gracias por utilizar el ChatBot de SpringBoot. Si tiene alguna pregunta o sugerencia, no dudo en decírsela."

--- Generación Completada ---
```
 
La respuesta del Llm sería poco a poco sin tener que esperar a que la genere completa

# Tool Calling

Tienes ejemplos de tool calling en: ToolConfiguration 
