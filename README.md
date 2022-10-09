# Currency Exchange

Как пользоваться: 
1. Клонировать репозиторий \
```git clone https://github.com/pavel-k-4/currency-exchange-test.git``` \
или скачать файл [отсюда](https://github.com/pavel-k-4/currency-exchange-test/releases/tag/v1.0.0).
2. Установать в файле ```src/main/resources/application-prod.properties``` корректную строку подключения и учетные данные для подключения к Postgres-у. \
```spring.datasource.url=``` \
```spring.datasource.username=```\
```spring.datasource.password=```
3. Запустить командой ```.\mvnw spring-boot:run -P prod```, предварительно убедившись, что 8080-й порт свободен. 
4. Зарегистрироваться и войти.