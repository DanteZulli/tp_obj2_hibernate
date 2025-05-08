# Tests para endpoints de Comentarios

### Obtener comentarios por ID de ticket (ID: 1)
```bash
curl --location 'http://localhost:8080/api/comentarios/traerComent/{idTicket}' \
--header 'Accept: application/json'
```

### Crear nuevo comentario
```bash
curl --location 'http://localhost:8080/api/comentarios/crear' \
--header 'Content-Type: application/x-www-form-urlencoded' \


```