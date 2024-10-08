db = connect('mongodb://localhost:27017/proyecto');

// Account
db.Account.insertMany([
    {
        _id: ObjectId("66f8db70c1ce3939dbcbe1e0"),
        email: "juanperez@example.com",
        registrationValidationCode: {
            creationDate: new Date(),
            code: "b5d9ce1f"
        },
        rol: "CUSTOMER",
        registrationDate: new Date(),
        password: "$2a$10$n7yiHoxleQtFldijM7z4ueqN0DNJzLR9v1nxbf/HH0zrPEuas1DCy", // Contraseña sin encriptar: passwordJuanPerez
        status: "INACTIVE",
        user: {
            _id: "1005774025",
            name: "Juan Perez",
            phoneNumber: "3245478525",
            address: "Crr 22 # 7-12"
        },
        _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
    },
    {
        _id: ObjectId("66f8dbbb4b350424b236bddb"),
        email: "maria.rodriguez@example.com",
        registrationValidationCode: {
            creationDate: new Date(),
            code: "6bb0ad32"
        },
        rol: "CUSTOMER",
        registrationDate: new Date(),
        password: "$2a$10$cfwPyfVpzf/wcHH11yU/SOCG5hL874EmKJKyNlynxslMBtZNuJ6Wu", // Contraseña sin encriptar: passwordMariaRodriguez
        status: "INACTIVE",
        user: {
            _id: "1005774026",
            name: "Maria Rodriguez",
            phoneNumber: "3215478925",
            address: "Cll 30 # 8-32"
        },
        _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
    },
    {
        _id: ObjectId("66f8dbfb12c88b4846e60860"),
        email: "carlos.gomez@example.com",
        registrationValidationCode: {
            creationDate: new Date(),
            code: "78911fa0"
        },
        rol: "CUSTOMER",
        registrationDate: new Date(),
        password: "$2a$10$on3iaIhr9W5shk1fbtA9X.lP5qDg/ELveC7zBeWCPsYJvFKZ/FiDG", // Contraseña sin encriptar: passwordCarlosGomez
        status: "INACTIVE",
        user: {
            _id: "1005774027",
            name: "Carlos Gomez",
            phoneNumber: "3105478520",
            address: "Cll 40 # 10-23"
        },
        _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
    },
    {
        _id: ObjectId("66f8dc576f736842f97ca0ce"),
        email: "luisa.fernandez@example.com",
        registrationValidationCode: {
            creationDate: new Date(),
            code: "688105d8"
        },
        rol: "CUSTOMER",
        registrationDate: new Date(),
        password: "$2a$10$loCyyJkz7w/khWvxcgkKDeaMbZ91itumuL.spnAZIfl3Brj98BzuC", // Contraseña sin encriptar: passwordLuisaFernandez
        status: "INACTIVE",
        user: {
            _id: "1005774028",
            name: "Luisa Fernandez",
            phoneNumber: "3109876543",
            address: "Cll 15 # 2-50"
        },
        _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
    },
    {
        _id: ObjectId("66f8dc7f8d4a5579f3e388d3"),
        email: "ana.martinez@example.com",
        registrationValidationCode: {
            creationDate: new Date(),
            code: "39110253"
        },
        rol: "ADMINISTRATOR",
        registrationDate: new Date(),
        password: "$2a$10$lDWLG6Zc6jkxo/ZPYyTxW.bC0yePHF8gRPyBbynYknktkIfxwM6uy", // Contraseña sin encriptar: passwordAnaMartinez
        status: "INACTIVE",
        user: {
            _id: "1005774029",
            name: "Ana Martinez",
            phoneNumber: "3001234567",
            address: "Av 9 # 5-40"
        },
        _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
    },{
        _id: ObjectId("67057761165dfd74d97b6c40"),
        email: "JuanCarlAg@hotmail.com",
        registrationValidationCode: {
            creationDate: new Date("2024-10-08T18:18:09.192Z"),
            code: "1e647ade"
        },
        rol: "CUSTOMER",
        registrationDate: new Date("2024-10-08T18:18:09.192Z"),
        password: "$2a$10$Ne/VoZ/b8kPOSMRYoCHJ2ONeJY5ZDEn9c1OXYMvCxRbye.JA/S1mm", // Contraseña sin encriptar: passwordJuanCarlosAguilar
        status: "INACTIVE",
        user: {
            _id: "1004667809",
            name: "Juan Carlos Aguilar",
            phoneNumber: "3245478325",
            address: "Crr 12 # 12-2"
        },
        _class: "co.edu.uniquindio.proyecto.model.Accounts.Account"
    }
]);

// Event
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
        amount: 200,
        localities: [
            {
                price: 50,
                name: "GENERAL",
                ticketsSold: 1,
                maximumCapacity: 200
            },
            {
                price: 150,
                name: "VIP",
                ticketsSold: 2,
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
                name: "GENERAL",
                ticketsSold: 8,
                maximumCapacity: 300
            },
            {
                price: 100,
                name: "VIP",
                ticketsSold: 2,
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
                name: "GENERAL",
                ticketsSold: 3,
                maximumCapacity: 100
            },
            {
                price: 60,
                name: "VIP",
                ticketsSold: 1,
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
                price: 2,
                name: "GENERAL",
                ticketsSold: 150,
                maximumCapacity: 200
            },
            {
                price: 75,
                name: "VIP",
                ticketsSold: 5,
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
                name: "GENERAL",
                ticketsSold: 5,
                maximumCapacity: 100
            },
            {
                price: 200,
                name: "VIP",
                ticketsSold: 2,
                maximumCapacity: 50
            }
        ],
        _class: "co.edu.uniquindio.proyecto.model.Events.Event"
    }
]);

// Order
db.Order.insertMany([
    {
        "_id": ObjectId("67020240653239785780116c"),
        "idAccount": ObjectId("66f8db70c1ce3939dbcbe1e0"),
        "date": ISODate("2024-10-08T10:30:00.000Z"),
        "gatewayCode": "2021909487-2a3b4c5d-6e7f-8g9h-0i1j2k3l4m5n",
        "items": [
            {
                "_id": 1,
                "idEvent": ObjectId("66f5c5a0de22e82833106d93"),
                "price": 40,
                "localityName": "GENERAL",
                "amount": 3
            }
        ],
        "payment": {
            "currency": "COP",
            "typePayment": "CREDIT_CARD",
            "authorizationCode": "",
            "date": ISODate("2024-10-08T10:30:00.000Z"),
            "transactionValue": 120,
            "state": "approved"
        },
        "total": 120,
        "_class": "co.edu.uniquindio.proyecto.model.PurchaseOrder.Order"
    },
    {
        "_id": ObjectId("67020240653239785780116d"),
        "idAccount": ObjectId("66f8dbbb4b350424b236bddb"),
        "date": ISODate("2024-10-09T14:20:00.000Z"),
        "gatewayCode": "2021909487-a1b2c3d4-e5f6-7g8h-9i0j1k2l3m4n",
        "items": [
            {
                "_id": 2,
                "idEvent": ObjectId("66f5c5a0de22e82833106d94"),
                "price": 30,
                "localityName": "GENERAL",
                "amount": 4
            }
        ],
        "payment": {
            "currency": "COP",
            "typePayment": "PSE",
            "authorizationCode": "",
            "date": ISODate("2024-10-09T14:20:00.000Z"),
            "transactionValue": 120,
            "state": "in_process"
        },
        "total": 120,
        "_class": "co.edu.uniquindio.proyecto.model.PurchaseOrder.Order"
    },
    {
        "_id": ObjectId("67020240653239785780116e"),
        "idAccount": ObjectId("66f8dbfb12c88b4846e60860"),
        "date": ISODate("2024-10-10T16:45:00.000Z"),
        "gatewayCode": "2021909487-0a1b2c3d-4e5f-6g7h-8i9j0k1l2m3n",
        "items": [
            {
                "_id": 3,
                "idEvent": ObjectId("66f5c5a0de22e82833106d95"),
                "price": 75,
                "localityName": "VIP",
                "amount": 1
            }
        ],
        "payment": {
            "currency": "COP",
            "typePayment": "CREDIT_CARD",
            "authorizationCode": "",
            "date": ISODate("2024-10-10T16:45:00.000Z"),
            "transactionValue": 75,
            "state": "approved"
        },
        "total": 75,
        "_class": "co.edu.uniquindio.proyecto.model.PurchaseOrder.Order"
    },
    {
        "_id": ObjectId("67020240653239785780116f"),
        "idAccount": ObjectId("66f8db70c1ce3939dbcbe1e0"),
        "date": ISODate("2024-10-11T09:15:00.000Z"),
        "gatewayCode": "2021909487-z9y8x7w6-v5u4-t3s2-r1p0o9n8m7l",
        "items": [
            {
                "_id": 4,
                "idEvent": ObjectId("66f5c5a0de22e82833106d96"),
                "price": 80,
                "localityName": "GENERAL",
                "amount": 2
            }
        ],
        "payment": {
            "currency": "COP",
            "typePayment": "PSE",
            "authorizationCode": "",
            "date": ISODate("2024-10-11T09:15:00.000Z"),
            "transactionValue": 160,
            "state": "in_process"
        },
        "total": 160,
        "_class": "co.edu.uniquindio.proyecto.model.PurchaseOrder.Order"
    },
    {
        "_id": ObjectId("670202406532397857801170"),
        "idAccount": ObjectId("66f8dbbb4b350424b236bddb"),
        "date": ISODate("2024-10-12T18:30:00.000Z"),
        "gatewayCode": "2021909487-q1w2e3r4-t5y6-u7i8-o9p0a1s2d3f4",
        "items": [
            {
                "_id": 5,
                "idEvent": ObjectId("66f5c5a0de22e82833106d94"),
                "price": 60,
                "localityName": "VIP",
                "amount": 1
            }
        ],
        "payment": {
            "currency": "COP",
            "typePayment": "CREDIT_CARD",
            "authorizationCode": "",
            "date": ISODate("2024-10-12T18:30:00.000Z"),
            "transactionValue": 60,
            "state": "approved"
        },
        "total": 60,
        "_class": "co.edu.uniquindio.proyecto.model.PurchaseOrder.Order"
    }
]);

// Coupon
db.Coupon.insertMany([
    {
        "_id": ObjectId("6705714b2f45aa6768d7d410"),
        "expirationDate": ISODate("2024-11-06T18:54:12.598Z"),
        "status": "AVAILABLE",
        "type": "ONLY",
        "name": "Cupón de Bienvenida",
        "discount": "15",
        "startDate": ISODate("2024-10-07T18:54:12.599Z"),
        "_class": "co.edu.uniquindio.proyecto.model.Coupons.Coupon"
    },
    {
        "_id": ObjectId("6705714b2f45aa6768d7d411"),
        "expirationDate": ISODate("2024-11-07T17:52:10.795Z"),
        "code": "178932EE",
        "status": "NOT_AVAILABLE",
        "type": "MULTIPLE",
        "name": "Emprendimiento",
        "discount": "10",
        "eventId": ObjectId("66f5c5a0de22e82833106d94"),
        "startDate": ISODate("2024-11-07T17:52:10.795Z"),
        "_class": "co.edu.uniquindio.proyecto.model.Coupons.Coupon"
    },
    {
        "_id": ObjectId("6705714b2f45aa6768d7d412"),
        "expirationDate": ISODate("2024-12-01T18:00:00.000Z"),
        "status": "AVAILABLE",
        "type": "ONLY",
        "name": "Descuento de Temporada",
        "discount": "20",
        "startDate": ISODate("2024-10-15T08:00:00.000Z"),
        "_class": "co.edu.uniquindio.proyecto.model.Coupons.Coupon"
    },
    {
        "_id": ObjectId("6705714b2f45aa6768d7d413"),
        "expirationDate": ISODate("2024-11-30T23:59:59.000Z"),
        "status": "NOT_AVAILABLE",
        "type": "MULTIPLE",
        "name": "Cupon Evento Especial",
        "discount": "25",
        "eventId": ObjectId("66f5c5a0de22e82833106d93"),
        "startDate": ISODate("2024-10-01T10:00:00.000Z"),
        "_class": "co.edu.uniquindio.proyecto.model.Coupons.Coupon"
    },
    {
        "_id": ObjectId("6705714b2f45aa6768d7d414"),
        "expirationDate": ISODate("2024-12-15T20:00:00.000Z"),
        "status": "AVAILABLE",
        "type": "ONLY",
        "name": "Descuento Navideño",
        "discount": "30",
        "startDate": ISODate("2024-11-01T08:00:00.000Z"),
        "_class": "co.edu.uniquindio.proyecto.model.Coupons.Coupon"
    },
    {
        "_id": ObjectId("6705714b2f45aa6768d7d415"),
        "expirationDate": ISODate("2024-11-25T17:52:10.795Z"),
        "status": "NOT_AVAILABLE",
        "type": "MULTIPLE",
        "name": "Cupon para Feria de Emprendimiento",
        "discount": "10",
        "eventId": ObjectId("66f5c5a0de22e82833106d94"),
        "startDate": ISODate("2024-11-01T09:00:00.000Z"),
        "_class": "co.edu.uniquindio.proyecto.model.Coupons.Coupon"
    }
]);

// Cart
db.Cart.insertMany([
    {
        "_id": ObjectId("66f8db70c1ce3939dbcbe1e0"),
        "date": ISODate("2024-10-08T18:03:06.555Z"),
        "items": [
            {
                "eventId": ObjectId("66f5c5a0de22e82833106d93"),
                "itemId": "302c4038-c602-4136-9a0c-561ed473ec26",
                "eventName": "Concierto Rock 2024",
                "localityName": "VIP",
                "price": 100,
                "quantity": 5,
                "subtotal": 500
            },
            {
                "eventId": ObjectId("66f5c5a0de22e82833106d94"),
                "itemId": "98a97479-827a-42a7-9fa2-0a51baedc209",
                "eventName": "Feria de Emprendimiento",
                "localityName": "VIP",
                "price": 60,
                "quantity": 5,
                "subtotal": 300
            }
        ],
        "total": 800,
        "_class": "co.edu.uniquindio.proyecto.model.Carts.Cart"
    },
    {
        "_id": ObjectId("66f8dbbb4b350424b236bddb"),
        "date": ISODate("2024-10-08T18:10:06.229Z"),
        "items": [
            {
                "eventId": ObjectId("66f5c5a0de22e82833106d93"),
                "itemId": "63f210ab-c7d7-4027-a12e-c765751a99a3",
                "eventName": "Concierto Rock 2024",
                "localityName": "VIP",
                "price": 100,
                "quantity": 5,
                "subtotal": 500
            },
            {
                "eventId": ObjectId("66f5c5a0de22e82833106d95"),
                "itemId": "a208355d-7f27-4929-b6a9-25e1b2abd9df",
                "eventName": "Festival de Cine 2024",
                "localityName": "VIP",
                "price": 75,
                "quantity": 5,
                "subtotal": 375
            }
        ],
        "total": 875,
        "_class": "co.edu.uniquindio.proyecto.model.Carts.Cart"
    },
    {
        "_id": ObjectId("66f8dbfb12c88b4846e60860"),
        "date": ISODate("2024-10-08T18:14:50.549Z"),
        "items": [
            {
                "eventId": ObjectId("66f5c5a0de22e82833106d96"),
                "itemId": "054011e7-5a12-49d0-8ab7-6eed5e2ae037",
                "eventName": "Congreso de Tecnología 2024",
                "localityName": "VIP",
                "price": 200,
                "quantity": 5,
                "subtotal": 1000
            },
            {
                "eventId": ObjectId("66f5c5a0de22e82833106d95"),
                "itemId": "6199fddd-25a4-49e8-a48e-3db76671fff9",
                "eventName": "Festival de Cine 2024",
                "localityName": "VIP",
                "price": 75,
                "quantity": 5,
                "subtotal": 375
            }
        ],
        "total": 1375,
        "_class": "co.edu.uniquindio.proyecto.model.Carts.Cart"
    },
    {
        "_id": ObjectId("66f8dc576f736842f97ca0ce"),
        "date": ISODate("2024-10-08T18:16:00.126Z"),
        "items": [
            {
                "eventId": ObjectId("66f5c5a0de22e82833106d96"),
                "itemId": "e69228ef-24db-40a1-b35a-08b2fcb76a53",
                "eventName": "Congreso de Tecnología 2024",
                "localityName": "VIP",
                "price": 200,
                "quantity": 5,
                "subtotal": 1000
            },
            {
                "eventId": ObjectId("66f5c5a0de22e82833106d94"),
                "itemId": "ca035817-29e0-453d-804e-28a26ef38e3c",
                "eventName": "Feria de Emprendimiento",
                "localityName": "VIP",
                "price": 60,
                "quantity": 5,
                "subtotal": 300
            }
        ],
        "total": 1300,
        "_class": "co.edu.uniquindio.proyecto.model.Carts.Cart"
    },
    {
        "_id": ObjectId("67057761165dfd74d97b6c40"),
        "date": ISODate("2024-10-08T18:19:38.846Z"),
        "items": [
            {
                "eventId": ObjectId("66f5c5a0de22e82833106d96"),
                "itemId": "bb0dbeb7-1ad8-42fe-bd3c-1b001afc3e18",
                "eventName": "Congreso de Tecnología 2024",
                "localityName": "VIP",
                "price": 200,
                "quantity": 5,
                "subtotal": 1000
            },
            {
                "eventId": ObjectId("66f5c5a0de22e82833106d94"),
                "itemId": "6df0c2ed-e16a-444a-a2f8-1a0e7aa85ff1",
                "eventName": "Feria de Emprendimiento",
                "localityName": "VIP",
                "price": 60,
                "quantity": 5,
                "subtotal": 300
            }
        ],
        "total": 1300,
        "_class": "co.edu.uniquindio.proyecto.model.Carts.Cart"
    }
]);


