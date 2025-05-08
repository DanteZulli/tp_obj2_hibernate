# Pruebas del CategoriasController

## Crear Categoría

```bash
curl -X POST http://localhost:8080/api/categorias/crear \
-H "Content-Type: application/json" \
-d '{
    "nombre": "Electrónicos",
    "descripcion": "Productos electrónicos y gadgets"
}'
```



## Actualizar Categoría

```bash
curl -X PUT http://localhost:8080/api/categorias/actualizar \
-H "Content-Type: application/json" \
-d '{
    "id": 1,
    "nombre": "Electrónicos Actualizado",
    "descripcion": "Nueva descripción de productos electrónicos"
}'
```


