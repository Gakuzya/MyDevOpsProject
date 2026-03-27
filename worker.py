import pika, time, sys

# Ждем 10 секунд при запуске, чтобы RabbitMQ успел полностью загрузиться
time.sleep(10)

print(" [*] Подключение Сервиса Склада к RabbitMQ...")

# Подключаемся к нашему брокеру (он доступен по имени контейнера 'rabbitmq')
connection = pika.BlockingConnection(pika.ConnectionParameters(host='rabbitmq'))
channel = connection.channel()

# Убеждаемся, что очередь существует (если нет, она создастся)
channel.queue_declare(queue='clothing_sales_queue', durable=True)

# Это функция, которая сработает автоматически, когда придет сообщение
def callback(ch, method, properties, body):
    print(f"\n[V] УРА! Получено сообщение от магазина: {body.decode()}")
    print(f"[V] Товар успешно списан со склада.\n")

# Говорим: "Слушай эту очередь и при получении вызывай функцию callback"
channel.basic_consume(queue='clothing_sales_queue', on_message_callback=callback, auto_ack=True)

print(' [*] Сервис склада готов! Ожидание новых продаж...')
channel.start_consuming()