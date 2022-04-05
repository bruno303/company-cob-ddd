FROM localstack/localstack:0.14.2

COPY init_sqs.sh /docker-entrypoint-initaws.d/init_sqs.sh

RUN chmod +x /docker-entrypoint-initaws.d/init_sqs.sh

ENTRYPOINT ["docker-entrypoint.sh"]