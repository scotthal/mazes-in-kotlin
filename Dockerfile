FROM adoptopenjdk/openjdk11:debian as build
WORKDIR "/home/app"
COPY . .
RUN ./gradlew test
RUN ./gradlew distTar

FROM adoptopenjdk/openjdk11:debian-jre
WORKDIR "/home/app"
COPY --from=build /home/app/app/build/distributions/app.tar .
RUN tar xf app.tar
CMD app/bin/app
