# witch-market
## Запуск
В основной директории создать .env и заполнить по шаблону **(пока что порты для серверов сделать 8081 - OMS и 8082 - AS)**.  
Далее из этой же директории выполнить `docker compose up -d`.  
Для теста можно выполнить post запрос на orders/ с содержимым:
```
{
    "pipelineId": 1,
    "clientId": "2db94bca-5ef3-467b-811d-a2496b0169ac"
}
```
И просмотреть логи серверов и состояние очередей в kafka-ui
## Что реализовано в настоящий момент
Реализована передача сообщений между order management service и artifact service через kafka, существует следующий трек: приходит POST запрос на создание заказа, он отправлется в очередь orders.new и в очередь tasks.artifact.pending, artifact service иммитирует работу и возвращает в tasks.artifact.completed, а OMS отправляет заказ в одну из очередей cancelled или completed
