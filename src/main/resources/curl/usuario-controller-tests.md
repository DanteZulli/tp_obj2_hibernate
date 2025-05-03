# Tests para endpoints de UsuarioController

### Crear nuevo cliente
```bash
curl --location 'http://localhost:8080/api/usuarios/clientes' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'nombre=Juan Perez' \
--data-urlencode 'email=juan@example.com' \
--data-urlencode 'plan=Premium' \
--data-urlencode 'particular=true'
```

### Actualizar cliente existente (ID: 1)
```bash
curl --location --request PUT 'http://localhost:8080/api/usuarios/clientes/1' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'nombre=Juan Perez Actualizado' \
--data-urlencode 'email=juan.nuevo@example.com' \
--data-urlencode 'plan=Basic' \
--data-urlencode 'particular=false'
```

### Asociar dirección a cliente (ID: 1)
```bash
curl --location 'http://localhost:8080/api/usuarios/clientes/1/direccion' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'direccionId=1' \
```

### Crear nuevo técnico
```bash
curl --location 'http://localhost:8080/api/usuarios/tecnicos' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'nombre=Pedro Técnico' \
--data-urlencode 'email=pedro@empresa.com' \
--data-urlencode 'nroContacto=123456789' \
--data-urlencode 'empresa=TechSolutions' \
--data-urlencode 'rolId=1'
```

### Actualizar técnico existente (ID: 4)
```bash
curl --location --request PUT 'http://localhost:8080/api/usuarios/tecnicos/4' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'nombre=Pedro Técnico Actualizado' \
--data-urlencode 'email=pedro.nuevo@empresa.com' \
--data-urlencode 'nroContacto=987654321' \
--data-urlencode 'empresa=TechSolutions Pro' \
--data-urlencode 'rolId=2'
```


