# Tests para endpoints del TicketController

### Crear nuevo ticket
```bash
curl --location 'http://localhost:8080/api/tickets/crear' \
--header 'Content-Type: application/x-www-form-urlencoded' \


```

## Obtener el estado de un ticket por ID (ID: 1)
```bash	
curl --location 'http://localhost:8080/api/tickets/1/estado' \
--header 'Accept: text/plain'
```

## Obtiene todos los tickets creados por un usuario dado su ID (ID: 1)
```bash	
curl --location 'http://localhost:8080/api/tickets/creador/1' \
--header 'Accept: text/plain'
```

