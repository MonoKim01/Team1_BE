FROM gradle:7.3.1-jdk17

COPY . .

RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties

RUN ./gradlew clean build

ENV DATABASE_URL=jdbc:mariadb://mariadb/krampoline

COPY --from=builder /home/gradle/project/build/libs/albbaim-1.0.jar .

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/home/gradle/project/build/libs/albbaim-1.0.jar"]
