<h3>Menu Service</h3>
<h4>Предоставляет REST API для CRUD операций с меню:</h4>
<ul class="mt-2">
    <li class="mt-2"><i>POST /v1/menu-items</i> - создать блюдо, информация о блюде передается в теле запроса. Доступно для сотрудников, информация о сотруднике
        передается в токене
        доступа.
    </li>
    <li class="mt-2"><i>DELETE /v1/menu-items/{id}</i> - удалить блюдо. Доступно для сотрудников, информация о сотруднике передается в токене доступа</li>
    <li class="mt-2"><i>PATCH /v1/menu-items/{id}</i> - обновить блюдо, параметры обновления передаются в теле запроса. Доступно для сотрудников, информация о
        сотруднике передается
        в токене доступа
    </li>
    <li class="mt-2"><i>GET /v1/menu-items/{id}</i> - получить блюдо. Доступно всем пользователям</li>
    <li class="mt-2"><i>GET /v1/menu-items?category={category}&sort={sort}</i> - получить список блюд из выбранной категории, отсортированный или по
        алфавиту(AZ, ZA), или по цене
        (PRICE_ASC, PRICE_DESC), или по дате создания (DATE_ASC, DATE_DESC). Доступно всем пользователям
    </li>
</ul>
Данные хранятся в реляционной базе PostgreSQL 16.
