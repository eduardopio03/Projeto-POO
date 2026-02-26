# Projeto POO - JumpMan Game

A 2D grid-based game developed in Java as part of the Object Oriented Programming (POO) course. The game is inspired by the classic Donkey Kong, where the player controls "JumpMan" to navigate through levels, avoid enemies, and save the Princess.

## 🎮 Game Description

The game runs on a custom GUI framework (`ImageGUI`) that renders a grid of images. The game logic is tick-based, meaning elements update their state (move, rot, explode) at specific intervals or when the player moves.

### Objective
Navigate through the room to reach the **Door** to advance to the next level, or reach the **Princess** to win the game. You must avoid enemies and traps while managing your health.

## 🕹️ Controls

| Key | Action |
| :--- | :--- |
| **Arrow Keys** | Move JumpMan (Up, Down, Left, Right) |
| **B** | Drop a Bomb (if available in inventory) |

## 🧩 Game Elements

### Characters
*   **JumpMan:** The main character controlled by the player. Starts with 100 health.
*   **Donkey Kong:** An enemy that moves randomly and throws Bananas. Contact causes damage.
*   **Bat:** An enemy that moves randomly (often downwards) and deals damage upon contact.
*   **Princess:** The end-game goal. Reaching her triggers the "You Won" state.

### Items & Consumables
*   **Good Meat:** Restores health. If not consumed within 10 ticks, it turns into Bad Meat.
*   **Bad Meat:** Result of rotten meat.
*   **Banana:** Thrown by Donkey Kongs.
*   **Sword:** Collectible item (logic implies attack capability).
*   **Bomb:** Can be picked up and dropped. Explodes after 5 ticks, destroying nearby elements.

### Environment
*   **Walls (W):** Impassable obstacles.
*   **Stairs (S):** Allow movement.
*   **Traps (t) & Hidden Traps (h):** Dangerous tiles on the floor.
*   **Door (0):** Exit to the next level.

## ⚙️ Technical Details

### Level Loading
Levels are loaded from text files (e.g., `room0.txt`, `room1.txt`). The `Room` class parses these files to populate the grid:
*   `W`: Wall
*   `S`: Stairs
*   `H`: JumpMan (Hero)
*   `G`: Donkey Kong (Gorilla)
*   `b`: Bat
*   `B`: Bomb
*   `m`: Good Meat
*   `P`: Princess
*   `0`: Door

### Engine
The `GameEngine` implements the `Observer` pattern to listen for key presses from the GUI. It manages the game loop, processing "ticks" to handle:
*   Gravity (JumpMan falling).
*   Enemy movement (Donkey Kongs and Bats).
*   Item updates (Meat rotting, Bombs ticking).
*   Win/Loss conditions.

## 🚀 How to Run

1.  Ensure you have a Java JDK installed.
2.  Compile the source code in the `src` folder.
3.  Ensure a folder named `images` exists in the project root containing the required assets (PNGs for JumpMan, DonkeyKong, Wall, etc.).
4.  Ensure level files (`room0.txt`, etc.) are in the project root.
5.  Run the main class (typically located in `pt.iscte.poo.main` or similar, initializing `GameEngine`).

## 📂 Project Structure

*   `pt.iscte.poo.gui`: GUI framework and image handling.
*   `pt.iscte.poo.game`: Core game logic (`GameEngine`, `Room`).
*   `pt.iscte.poo.Characters`: Entity classes (`JumpMan`, `Enemy`, `DonkeyKong`, `Bat`).
*   `pt.iscte.poo.Consumables`: Items (`Banana`, `Meat`, `Sword`).
*   `pt.iscte.poo.Interactables`: Objects like `Bomb` and `Trap`.
*   `pt.iscte.poo.utils`: Helper classes (`Point2D`, `Vector2D`, `Direction`).

---
*Developed for POO Course @ ISCTE-IUL*