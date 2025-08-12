# 🎮 Juego RPG - El Trono de Hierro

## 📋 Descripción del Proyecto

Este es un juego de rol (RPG) por consola desarrollado en Java como trabajo final para la UTN. El juego simula combates épicos entre personajes de diferentes razas en la lucha por el Trono de Hierro.

## 🎯 Características Principales

- **Sistema de combate por turnos** con mecánicas balanceadas
- **3 razas jugables** con características únicas: Humanos, Elfos y Orcos
- **Generación automática y manual** de personajes
- **Sistema de logging** completo para seguimiento de partidas
- **Interfaz de usuario** intuitiva por consola
- **Arquitectura modular** siguiendo principios SOLID

## 🏗️ Arquitectura del Proyecto

```
src/main/java/com/utn/rpg/
├── Main.java                    # Punto de entrada
├── controller/
│   └── GameController.java     # Controlador principal
├── model/                      # Modelos de datos
│   ├── Character.java          # Clase abstracta base
│   ├── Human.java             # Personaje Humano
│   ├── Elf.java               # Personaje Elfo
│   ├── Orc.java               # Personaje Orco
│   ├── Player.java            # Jugador
│   ├── Race.java              # Enumeración de razas
│   ├── Constants.java         # Constantes del juego
│   └── RoundResult.java       # Resultado de ronda
├── service/                   # Lógica de negocio
│   ├── Game.java             # Motor del juego
│   ├── CharacterGenerator.java # Generador de personajes
│   ├── GameLogger.java       # Sistema de logging
│   └── GameUI.java           # Interfaz de usuario
└── exception/                # Excepciones personalizadas
    ├── GameException.java    # Excepción base
    └── CombatException.java  # Excepciones de combate
```

## 🎲 Mecánicas de Juego

### Razas y Características

| Raza | Multiplicador de Daño | Mejora al Ganar | 
|------|----------------------|------------------|
| **Humano** | 1.0x | +1 Nivel | 
| **Elfo** | 1.05x | +1 Habilidad | 
| **Orco** | 1.1x | +8 Salud | 

### Sistema de Combate

- **Cálculo de daño**: `((Habilidad × Fuerza × Nivel × Efectividad) - (Armadura × Velocidad del defensor)) / 500 × 100`
- **Efectividad**: Factor aleatorio entre 0.01 y 1.0
- **Máximo 7 ataques por ronda**
- **Sistema de turnos**: El perdedor de la ronda anterior ataca primero

### Atributos de Personajes

- **Salud**: 1-100 (máximo 100)
- **Velocidad**: 1-10
- **Habilidad**: 1-5
- **Fuerza**: 1-10
- **Nivel**: 1-10
- **Armadura**: 1-10
- **Edad**: 1-300 años

## 🚀 Cómo Ejecutar

### Prerrequisitos
- Java 17 o superior
- IDE compatible (IntelliJ IDEA, Eclipse, VS Code)

### Compilación y Ejecución

```bash
# Compilar desde la raíz del proyecto
javac -d out src/main/java/com/utn/rpg/**/*.java

# Ejecutar
java -cp out main.java.com.utn.rpg.Main
```

### Desde IDE
1. Importar el proyecto
2. Ejecutar la clase `Main.java`

## 🎮 Guía de Uso

### Menú Principal

1. **Nuevo juego con personajes aleatorios**: Genera 6 personajes automáticamente
2. **Nuevo juego creando personajes**: Permite crear personajes manualmente
3. **Leer logs de partidas**: Visualiza el historial de juegos
4. **Limpiar logs de partidas**: Elimina el archivo de logs
5. **Salir**: Termina la aplicación

### Durante el Juego

- Los combates se desarrollan automáticamente
- Se muestra el progreso de cada ataque y el estado de los personajes
- Los logs se guardan automáticamente en `logs/game_log.txt`
- El juego termina cuando un jugador se queda sin personajes vivos

## 👥 Desarrollado por
-  Joaquin Auday

Trabajo Final - Programación en Java - 2023

---
