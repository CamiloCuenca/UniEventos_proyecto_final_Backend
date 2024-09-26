db = connect('mongodb://localhost:27017/proyecto');

// Account
db.Account.insertMany([
    {
        _id: ObjectId("66f5bd3e47df155fcc9971fb"),
        email: "ba5808864@gmail.com",
        registrationValidationCode:"",
        rol: "CUSTOMER",
        registrationDate: ISODate("2024-09-26T19:59:58.707Z"),
        password: "$2a$10$Op14s.JAlHnPTV8ohJywxeGhCkap.nN6l9EI5plpE/G3tZLbZ80fi",
        status: "INACTIVE",
        user: {
            _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
        }
    },
    {
        _id: ObjectId("66f5bd3e47df155fcc9971fc"),
        email: "john.doe@example.com",
        registrationValidationCode:"",
        rol: "ADMIN",
        registrationDate: ISODate("2024-09-25T14:30:22.000Z"),
        password: "$2a$10$0qpAlP/FS/6nOlhXcdjXyZ5v.QiTxLd7Hq/uEyUNqq1Fkhmrkqye.",
        status: "ACTIVE",
        user: {
            _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
        }
    },
    {
        _id: ObjectId("66f5bd3e47df155fcc9971fd"),
        email: "jane.smith@example.com",
        registrationValidationCode:"",
        rol: "CUSTOMER",
        registrationDate: ISODate("2024-09-22T10:15:30.000Z"),
        password: "$2a$10$dFkh9kL7M.XGgfZcFlr7zu3b.Buw5b8X9T0Ob8bX9FYlRmqzMhfKS",
        status: "INACTIVE",
        user: {
            _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
        }
    },
    {
        _id: ObjectId("66f5bd3e47df155fcc9971fe"),
        email: "michael.jordan@example.com",
        registrationValidationCode:"",
        rol: "CUSTOMER",
        registrationDate: ISODate("2024-09-24T17:45:50.000Z"),
        password: "$2a$10$cA/ZPH/UxClpxxPqGci5puR.MFumXa.Ds6OnVxaEQNgzGkyz9hTgS",
        status: "ACTIVE",
        user: {
            _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
        }
    },
    {
        _id: ObjectId("66f5bd3e47df155fcc9971ff"),
        email: "alice.wonderland@example.com",
        registrationValidationCode:"",
        rol: "ADMIN",
        registrationDate: ISODate("2024-09-20T08:05:45.000Z"),
        password: "$2a$10$BxMf9J3O7cO9zD/Cq1O3LuQyHHb7KsF5PwWkeVQXcnomcykIDDozq",
        status: "INACTIVE",
        user: {
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

// Coupon

// Order