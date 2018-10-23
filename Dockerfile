FROM aglover/java8-pier

RUN apt-get update
RUN apt-get install -y \
    nmap \
    espeak
