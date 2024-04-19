# Utilizar uma imagem base que suporte a JDK 11
FROM openjdk:11-jre-slim

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo jar da sua aplicação para o diretório de trabalho
COPY target/BackendVovoLuisa-0.0.6-SNAPSHOT.jar BackendVovoLuisa-0.0.6-SNAPSHOT.jar

# Comando para executar a aplicação
CMD ["java", "-jar", "BackendVovoLuisa-0.0.6-SNAPSHOT.jar"]