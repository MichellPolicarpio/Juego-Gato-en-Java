# Juego del Gato (Tres en Raya)

Una implementación clásica del Juego del Gato desarrollada en Java con interfaz gráfica utilizando Swing. Este proyecto demuestra principios de programación orientada a objetos y conceptos de programación basada en eventos.

## Características Principales

- 🎮 Interfaz gráfica con menú principal y tablero de juego
- 👥 Nombres de jugadores personalizables
- 📊 Registro histórico de partidas con fecha y hora
- 🎯 Resaltado de línea ganadora
- 🤝 Detección de empates
- 💾 Historial de juego guardado en archivo

## Detalles Técnicos

- **Lenguaje:** Java
- **Framework de GUI:** Swing
- **Gestor de Diseño:** GridLayout
- **E/S de Archivos:** BufferedReader/PrintWriter para gestión del historial
- **Fecha/Hora:** LocalDateTime para registro de timestamps

## Componentes del Juego

### Menú Principal
- Iniciar Juego
- Modificar Nombres de Jugadores
- Ver Historial de Partidas
- Créditos
- Ayuda
- Salir

### Tablero de Juego
- Cuadrícula 3x3 de botones
- Retroalimentación visual para combinaciones ganadoras
- Juego por turnos (X comienza primero)
- Detección en tiempo real de victoria/empate

### Sistema de Historial
- Registro automático de resultados
- Almacenamiento de información del ganador
- Incluye marca de tiempo para cada partida
- Guarda en archivo "historial.txt"

### Funcionalidades Adicionales
- Interfaz intuitiva y fácil de usar
- Validación de movimientos
- Mensajes informativos para el usuario
- Persistencia de datos entre sesiones

## Contexto Educativo
Este proyecto fue desarrollado como parte del curso de Paradigmas de Programación II en la FIEE - UV (Facultad de Ingeniería Eléctrica y Electrónica - Universidad Veracruzana).

## Desarrollador
- **Autor:** Michell Alexis Policarpio Moran
- **Matrícula:** zS21002379
- **Semestre:** 7°
- **Carrera:** Ingeniería en Informática

## Requisitos del Sistema
- Java Runtime Environment (JRE)
- Sistema operativo compatible con Java
- Espacio mínimo en disco para almacenar historial

## Instrucciones de Uso
1. Ejecutar el programa
2. Ingresar nombres de los jugadores (opcional)
3. Seleccionar "Iniciar Juego" en el menú principal
4. Jugar alternando turnos entre X y O
5. Consultar el historial de partidas cuando se desee

Este proyecto representa una implementación práctica de conceptos fundamentales de programación orientada a objetos, manejo de eventos y desarrollo de interfaces gráficas en Java.
