FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:resolve

COPY src ./src

ARG access_key=AKIARFK7TWZWG7Q5KNW4
ARG secret_key=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
ARG spring_datasource_username=database_render_e_store_user
ARG spring_datasource_password=Iwz2G95IK8ELKKmMqTsx1Ct8AzMnbiSI
ARG spring_security_oauth2_client_registration_google_client-id=223580115280-49jbi43omd6lklkupnj8im3uo21ibbnq.apps.googleusercontent.com
ARG spring_mail_username=emailwrecker@gmail.com
ARG spring_mail_password=jlludajvyayrkgax

CMD ["./mvnw", "spring-boot:run"]