FROM postgres:latest

# Use non-root user for install and run your application
RUN groupadd --gid 1024 newuserkk \
    && useradd \
        --uid 1024 \
        --gid 1024 \
        --create-home \
        --shell /bin/bash \
        newuserkk
