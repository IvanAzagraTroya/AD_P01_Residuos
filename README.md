# Aplicación para consulta del tratado de residuos en la Comunidad de Madrid

![imagen](image\image.png)

## Cómo ejecutar el programa

Existen 3 opciones para la ejecución del programa

- ` parser [origen_datos] [destino_datos]`
- ` resumen [origen_datos] [destino_datos]`
- ` resumen [distrito] [origen_datos] [destino_datos]`

En caso de no introducir los datos correctamente se finalizará la ejecución.

En la carpeta de origen de datos deberán encontrarse los csv que se utilizan para sacar los datos, estos son _contenedores_varios.csv_ y _modelo_residuos_2021.csv_.

## **¡Se necesitan ambos archivos para el correcto funcionamiento del programa!**

El programa guardará un archivo de la ejecución del programa en caso exitoso o fallido, en él se podrá consultar el tiempo de ejecución, fecha de uso y tipo de opción ejecutada.
