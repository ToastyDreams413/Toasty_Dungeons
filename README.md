# Info
This is a mod being developed in conjunction with a Forge server.

The intention of this mod is to develop a fun and immersive RPG dungeons experience for minecraft players.

Minecraft Forge version: 1.19

IP Address: currently not open to public

# How to Setup
1. Install Java 17 Development Kit (JDK)
2. Clone the repository
3. Choose an IDE
    * Intellij IDEA
    * Eclipse
    * VSCode (you will need to install these two extensions: [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) and [Gradle for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-gradle))
4. Generating IDE Launch/Run Configurations by running the command in the root directory of the repository:
    * Intellij IDEA: `gradlew genIntellijRuns`
    * Eclipse: `gradlew genEclipseRuns`
    * VSCode: `gradlew genVSCodeRuns`
5. You can now navigate to the gradlew run configurations or use the following commands:
    * `gradlew build`
        + This compiles the mod to a .jar file located in build/libs
    * `gradlew runClient`
        + This runs and opens the mod through an instance of Minecraft

[More Info](https://docs.minecraftforge.net/en/latest/gettingstarted/)