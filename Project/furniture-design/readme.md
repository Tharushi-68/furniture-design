# 🪑 Furniture Design App (JavaFX)

This is a JavaFX-based Furniture Design tool that allows users to:

- Design a room using a **2D canvas**
- Automatically sync the design with a **3D canvas**
- Add, move, and remove furniture items
- Customize room shape and colors
- Save and load canvas designs using JSON files
--

---

## 📦 Features

- 📐 2D Canvas with interactive furniture placement
- 🧱 Real-time 3D room synchronization using JavaFX 3D
- 🎨 Customizable room shape (`rectangle` or `oval`), wall and floor colors
- 🧩 Add furniture items by name
- 🖱️ Drag & drop furniture with mouse
- 🗑️ Right-click to remove furniture
- 💾 Save/load designs as `.json` files using Gson
- 📁 Toolbar with **Back**, **Save**, and **Delete** buttons

---

## 🚀 Getting Started

### 🔧 Prerequisites

- Java 11 or above
- JavaFX SDK installed
- Gson library

### 💻 Setup

1. Clone this repository or download the source code.

2. Make sure your IDE is configured to use JavaFX.

3. Add **Gson** to your project. If you're using Maven:

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>


- Start the project
mvn javafx:run

