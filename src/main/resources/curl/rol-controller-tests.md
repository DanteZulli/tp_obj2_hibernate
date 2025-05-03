# Tests para endpoints de RolController

### Crear nuevo rol
```bash
curl --location 'http://localhost:8080/api/roles' \
--header 'Content-Type: application/json' \
--data '{
    "nombre": "Nuevo Rol"
}'
```

### Modificar rol existente (ID: 1)
```bash
curl --location --request PUT 'http://localhost:8080/api/roles/1' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'nombre=Rol Modificado'
```

### Obtener rol por ID (ID: 1)
```bash
curl --location 'http://localhost:8080/api/roles/1' \
--header 'Accept: application/json'
```