# Estágio 1: Build da aplicação (não exige Java ou Maven instalados no servidor)
FROM eclipse-temurin:25 as jre-build AS build
WORKDIR /app

# Copia os arquivos do Maven Wrapper e o pom.xml primeiro (aproveita cache do Docker)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copia o código-fonte e gera o arquivo .jar
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Estágio 2: Imagem final otimizada para produção
FROM eclipse-temurin:25 as jre-build
WORKDIR /app

# Cria um usuário sem privilégios de root (boa prática de segurança em servidores)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia apenas o .jar compilado do estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão (plataformas cloud geralmente injetam a variável PORT)
EXPOSE 8080

# Otimizações de memória da JVM para containers + suporte à variável PORT de servidores cloud
ENTRYPOINT ["sh", "-c", "java -XX:MaxRAMPercentage=75.0 -Dserver.port=${PORT:-8080} -jar app.jar"]