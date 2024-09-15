db = connect('mongodb://localhost:27017/proyecto');


db.Cuenta.insertMany([
    {
        _id: ObjectId('66a2a9aaa8620e3c1c5437be'),
        rol: 'CLIENTE',
        status: 'INACTIVO',
        email: 'pepeperez@email.com',
        password: 'password',
        user: {
            cedula: '1213444',
            nombre: 'Pepito Perez',
            telefono: '3012223333',
            direccion: '123 Main St # 12-12',
        },
        registrationDate: ISODate('2024-07-25T21:41:57.849Z'),
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Account'
    },
    {
        _id: ObjectId('66a2c14dd9219911cd34f2c0'),
        rol: 'CLIENTE',
        status: 'ACTIVO',
        email: 'rosalopez@email.com',
        password: 'password',
        user: {
            cedula: '1213445',
            nombre: 'Rosa Lopez',
            telefono: '3128889191',
            direccion: 'ABC St # 12-12',
        },
        registrationDate: ISODate('2024-08-02T21:41:57.849Z'),
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Account'
    },
    {
        _id: ObjectId('66a2c1517f3b340441ffdeb0'),
        rol: 'ADMINISTRADOR',
        status: 'ACTIVO',
        email: 'admin1@email.com',
        password: 'password',
        user: {
            nombre: 'Admin 1'
        },
        registrationDate: ISODate('2024-08-25T21:41:57.849Z'),
        _class: 'co.edu.uniquindio.proyecto.modelo.documentos.Account'
    },
]);