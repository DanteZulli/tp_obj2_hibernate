# Pruebas del EtiquetaController

## Crear Etiqueta

```bash
curl -X POST http://localhost:8080/api/etiquetas/crear \
-H "Content-Type: application/json" \
-d '{
    "nombre": "Oferta",
    "descripcion": "Productos en oferta especial"
}'
```


## Actualizar Etiqueta

```bash
curl -X PUT http://localhost:8080/api/etiquetas/actualizar \
-H "Content-Type: application/json" \
-d '{
    "id": 1,
    "nombre": "Oferta Flash",
    "descripcion": "Ofertas por tiempo limitado"
}'
```
