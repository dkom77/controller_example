# Пример сервиса

При запуске инициализуется файловая база h2 (h2db).

Консоль БД доступна по ссылке http://localhost:5555/h2-console/ при запущеном сервисе

datasource.username = sa

datasource.password = pswd

### Создание клиента
curl --location --request POST 'http://localhost:5555/register' \
--header 'Content-Type: application/xml' \
--data-raw '<?xml version="1.0" encoding="utf-8"?>
<request>
<request-type>CREATE-AGT</request-type>
<extra name="login">123456</extra>
<extra name="password">pwd</extra>    
</request>'

### Получение баланса
curl --location --request POST 'http://localhost:5555/balance' \
--header 'Content-Type: application/xml' \
--data-raw '<?xml version="1.0" encoding="utf-8"?>
<request>
<request-type>GET-BALANCE</request-type>
<extra name="login">123456</extra>
<extra name="password">pwd</extra>    
</request>'


