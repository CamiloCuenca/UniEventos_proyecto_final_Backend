db = connect('mongodb://localhost:27017/proyecto');

// Account
db.Account.insertMany([
    {
        _id: ObjectId("66f70c5a0000000000ae1ccf"),
        email: "juan.perez@example.com",
        registrationValidationCode: {
            creationDate: new Date(),
            code: Math.random().toString(36).substring(2, 10)
        },
        rol: "CUSTOMER",
        registrationDate: new Date(),
        password: "$2a$10$Op14s.JAlHnPTV8ohJywxeGhCkap.nN6l9EI5plpE/G3tZLbZ80fi",
        status: "INACTIVE",
        user: {
            _id: "1001234567",
            name: "Juan Perez",
            phoneNumber: "3123456789",
            address: "Calle 123 #45-67",
            _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
        }
    },
    {
        _id: ObjectId("66f70c5a0000000000ae1cd0"),
        email: "ana.lopez@example.com",
        registrationValidationCode: {
            creationDate: new Date(),
            code: Math.random().toString(36).substring(2, 10)
        },
        rol: "ADMINISTRATOR",
        registrationDate: new Date(),
        password: "$2a$10$0qpAlP/FS/6nOlhXcdjXyZ5v.QiTxLd7Hq/uEyUNqq1Fkhmrkqye.",
        status: "ACTIVE",
        user: {
            _id: "1002345678",
            name: "Ana Lopez",
            phoneNumber: "3209876543",
            address: "Carrera 50 #30-20",
            _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
        }
    },
    {
        _id: ObjectId("66f70c5a0000000000ae1cd1"),
        email: "carlos.martinez@example.com",
        registrationValidationCode: {
            creationDate: new Date(),
            code: Math.random().toString(36).substring(2, 10)
        },
        rol: "CUSTOMER",
        registrationDate: new Date(),
        password: "$2a$10$dFkh9kL7M.XGgfZcFlr7zu3b.Buw5b8X9T0Ob8bX9FYlRmqzMhfKS",
        status: "INACTIVE",
        user: {
            _id: "1003456789",
            name: "Carlos Martinez",
            phoneNumber: "3012345678",
            address: "Diagonal 15 #25-10",
            _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
        }
    },
    {
        _id: ObjectId("66f70c5a0000000000ae1cd2"),
        email: "laura.gomez@example.com",
        registrationValidationCode: {
            creationDate: new Date(),
            code: Math.random().toString(36).substring(2, 10)
        },
        rol: "CUSTOMER",
        registrationDate: new Date(),
        password: "$2a$10$cA/ZPH/UxClpxxPqGci5puR.MFumXa.Ds6OnVxaEQNgzGkyz9hTgS",
        status: "ACTIVE",
        user: {
            _id: "1004567890",
            name: "Laura Gomez",
            phoneNumber: "3109876543",
            address: "Transversal 20 #30-50",
            _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
        }
    },
    {
        _id: ObjectId("66f70c5a0000000000ae1cd3"),
        email: "brandon.montealegre@example.com",
        registrationValidationCode: {
            creationDate: new Date(),
            code: Math.random().toString(36).substring(2, 10)
        },
        rol: "CUSTOMER",
        registrationDate: new Date(),
        password: "$2a$10$4gJmZiKnkqsM9uzL3l8jOebGgNPUdK.6u/ZVTVnrrVWZSqoL8crg2",
        status: "INACTIVE",
        user: {
            _id: "1005774025",
            name: "Brandon Montealegre",
            phoneNumber: "3245478525",
            address: "Crr 22 # 7-12",
            _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
        }
    }
]);
// EVent
db.Event.insertMany([
    {
        _id: ObjectId("66f5c5a0de22e82833106d92"),
        coverImage: "rutaImg2.png",
        name: "Evento prueba 2",
        status: "ACTIVE",
        description: "Prueba Creacion de Evento 2",
        imageLocalities: "updated_image_localities.jpg",
        type: "CONCERT",
        date: ISODate("2024-10-20T23:00:00.000Z"),
        city: "Armenia",
        address: "crr 20 # 1-23",
        amount: 600,
        localities: [
            {
                price: 50,
                name: "General",
                ticketsSold: 100,
                maximumCapacity: 200
            },
            {
                price: 150,
                name: "VIP",
                ticketsSold: 50,
                maximumCapacity: 100
            }
        ],
        _class: "co.edu.uniquindio.proyecto.model.Events.Event"
    },
    {
        _id: ObjectId("66f5c5a0de22e82833106d93"),
        coverImage: "rutaImg1.png",
        name: "Concierto Rock 2024",
        status: "INACTIVE",
        description: "Evento de rock internacional",
        imageLocalities: "rock_localities.jpg",
        type: "CONCERT",
        date: ISODate("2024-11-15T20:00:00.000Z"),
        city: "Bogotá",
        address: "Av. Caracas #12-34",
        amount: 500,
        localities: [
            {
                price: 40,
                name: "General",
                ticketsSold: 80,
                maximumCapacity: 300
            },
            {
                price: 100,
                name: "VIP",
                ticketsSold: 20,
                maximumCapacity: 50
            }
        ],
        _class: "co.edu.uniquindio.proyecto.model.Events.Event"
    },
    {
        _id: ObjectId("66f5c5a0de22e82833106d94"),
        coverImage: "rutaImg3.png",
        name: "Feria de Emprendimiento",
        status: "ACTIVE",
        description: "Espacio para emprendedores locales",
        imageLocalities: "fair_localities.jpg",
        type: "FAIR",
        date: ISODate("2024-12-01T10:00:00.000Z"),
        city: "Medellín",
        address: "Plaza Mayor",
        amount: 300,
        localities: [
            {
                price: 30,
                name: "Stand Básico",
                ticketsSold: 30,
                maximumCapacity: 100
            },
            {
                price: 60,
                name: "Stand Premium",
                ticketsSold: 10,
                maximumCapacity: 50
            }
        ],
        _class: "co.edu.uniquindio.proyecto.model.Events.Event"
    },
    {
        _id: ObjectId("66f5c5a0de22e82833106d95"),
        coverImage: "rutaImg4.png",
        name: "Festival de Cine 2024",
        status: "ACTIVE",
        description: "Proyecciones de cine independiente",
        imageLocalities: "cinema_localities.jpg",
        type: "FESTIVAL",
        date: ISODate("2024-09-30T18:00:00.000Z"),
        city: "Cali",
        address: "Teatro Municipal",
        amount: 700,
        localities: [
            {
                price: 25,
                name: "General",
                ticketsSold: 150,
                maximumCapacity: 200
            },
            {
                price: 75,
                name: "VIP",
                ticketsSold: 50,
                maximumCapacity: 100
            }
        ],
        _class: "co.edu.uniquindio.proyecto.model.Events.Event"
    },
    {
        _id: ObjectId("66f5c5a0de22e82833106d96"),
        coverImage: "rutaImg5.png",
        name: "Congreso de Tecnología 2024",
        status: "INACTIVE",
        description: "Evento de innovación y tecnología",
        imageLocalities: "tech_localities.jpg",
        type: "CONFERENCE",
        date: ISODate("2024-11-05T09:00:00.000Z"),
        city: "Barranquilla",
        address: "Centro de Convenciones",
        amount: 400,
        localities: [
            {
                price: 80,
                name: "General",
                ticketsSold: 50,
                maximumCapacity: 100
            },
            {
                price: 200,
                name: "VIP",
                ticketsSold: 20,
                maximumCapacity: 50
            }
        ],
        _class: "co.edu.uniquindio.proyecto.model.Events.Event"
    }
]);
// Cart
db.Cart.insertMany([
    {
        _id: ObjectId("66f7112588751406573dcef9"),
        date: new Date(),
        items: [
            {
                amount: 2,
                capacity: 250,
                localityName: "General",
                idEvent: ObjectId("66f5c5a0de22e82833106d92") // Evento de prueba
            }
        ],
        accountId: ObjectId("66f70c5a0000000000ae1ccf"), // Juan Perez
        _class: "co.edu.uniquindio.proyecto.model.Carts.Cart"
    },
    {
        _id: ObjectId("66f7112588751406573dcefa"),
        date: new Date(),
        items: [
            {
                amount: 1,
                capacity: 150,
                localityName: "VIP",
                idEvent: ObjectId("66f5c5a0de22e82833106d93") // Evento de "Music Fest 2024"
            }
        ],
        accountId: ObjectId("66f70c5a0000000000ae1cd0"), // Ana Lopez
        _class: "co.edu.uniquindio.proyecto.model.Carts.Cart"
    },
    {
        _id: ObjectId("66f7112588751406573dcefb"),
        date: new Date(),
        items: [
            {
                amount: 3,
                capacity: 100,
                localityName: "Palco",
                idEvent: ObjectId("66f5c5a0de22e82833106d94") // Evento de "Art Expo 2024"
            }
        ],
        accountId: ObjectId("66f70c5a0000000000ae1cd1"), // Carlos Martinez
        _class: "co.edu.uniquindio.proyecto.model.Carts.Cart"
    },
    {
        _id: ObjectId("66f7112588751406573dcefc"),
        date: new Date(),
        items: [
            {
                amount: 4,
                capacity: 300,
                localityName: "Preferencial",
                idEvent: ObjectId("66f5c5a0de22e82833106d95") // Evento de "Startup Summit 2024"
            }
        ],
        accountId: ObjectId("66f70c5a0000000000ae1cd2"), // Laura Gomez
        _class: "co.edu.uniquindio.proyecto.model.Carts.Cart"
    },
    {
        _id: ObjectId("66f7112588751406573dcefd"),
        date: new Date(),
        items: [
            {
                amount: 5,
                capacity: 500,
                localityName: "General",
                idEvent: ObjectId("66f5c5a0de22e82833106d96") // Evento de "Science Fair 2024"
            }
        ],
        accountId: ObjectId("66f70c5a0000000000ae1cd3"), // Brandon Montealegre
        _class: "co.edu.uniquindio.proyecto.model.Carts.Cart"
    }
]);

// Coupon

// Order