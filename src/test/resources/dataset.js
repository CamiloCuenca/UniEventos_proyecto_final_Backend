db = connect('mongodb://localhost:27017/proyecto');


// Events
db.Eventos.insertMany([
    {
        _id: ObjectId('66dcf9d99b293d0c2aba1372'),
        coverImage: 'image4.jpg',
        name: 'Jazz Night',
        status: 'ACTIVO',
        description: 'An intimate evening of smooth jazz.',
        imageLocalities: 'jazz_night_localities.jpg',
        type: 'CONCIERTO',
        date: ISODate('2024-12-05T20:00:00.000Z'),
        city: 'Bogotá',
        localities: [
            {
                price: 100,
                name: 'General',
                ticketsSold: 200,
                maximumCapacity: 300
            },
            {
                price: 250,
                name: 'VIP',
                ticketsSold: 50,
                maximumCapacity: 100
            }
        ],
        _class: 'co.edu.uniquindio.proyecto.model.Events.Event'
    },
    {
        _id: ObjectId('66dcf9d99b293d0c2aba1373'),
        coverImage: 'image5.jpg',
        name: 'Pop Music Awards',
        status: 'ACTIVO',
        description: 'Annual pop music award show featuring top artists.',
        imageLocalities: 'pop_music_awards_localities.jpg',
        type: 'GALA',
        date: ISODate('2024-09-15T19:00:00.000Z'),
        city: 'Cali',
        localities: [
            {
                price: 150,
                name: 'General',
                ticketsSold: 300,
                maximumCapacity: 500
            },
            {
                price: 400,
                name: 'VIP',
                ticketsSold: 100,
                maximumCapacity: 150
            }
        ],
        _class: 'co.edu.uniquindio.proyecto.model.Events.Event'
    },
    {
        _id: ObjectId('66dcf9d99b293d0c2aba1374'),
        coverImage: 'image6.jpg',
        name: 'Classical Concert',
        status: 'INACTIVO',
        description: 'A night of classical music by renowned orchestras.',
        imageLocalities: 'classical_concert_localities.jpg',
        type: 'CONCIERTO',
        date: ISODate('2025-01-10T18:30:00.000Z'),
        city: 'Medellín',
        localities: [
            {
                price: 80,
                name: 'General',
                ticketsSold: 100,
                maximumCapacity: 250
            },
            {
                price: 300,
                name: 'VIP',
                ticketsSold: 75,
                maximumCapacity: 100
            }
        ],
        _class: 'co.edu.uniquindio.proyecto.model.Events.Event'
    },
    {
        _id: ObjectId('66dcf9d99b293d0c2aba1375'),
        coverImage: 'image7.jpg',
        name: 'Tech Conference 2024',
        status: 'ACTIVO',
        description: 'An annual conference focused on the latest tech trends.',
        imageLocalities: 'tech_conference_localities.jpg',
        type: 'CONFERENCIA',
        date: ISODate('2024-11-22T09:00:00.000Z'),
        city: 'Cartagena',
        localities: [
            {
                price: 200,
                name: 'General',
                ticketsSold: 250,
                maximumCapacity: 400
            },
            {
                price: 500,
                name: 'VIP',
                ticketsSold: 50,
                maximumCapacity: 100
            }
        ],
        _class: 'co.edu.uniquindio.proyecto.model.Events.Event'
    },
    {
        _id: ObjectId('66dcf9d99b293d0c2aba1376'),
        coverImage: 'image8.jpg',
        name: 'Food Festival 2024',
        status: 'ACTIVO',
        description: 'A celebration of local and international cuisine.',
        imageLocalities: 'food_festival_localities.jpg',
        type: 'FESTIVAL',
        date: ISODate('2024-10-05T12:00:00.000Z'),
        city: 'Pereira',
        localities: [
            {
                price: 30,
                name: 'General',
                ticketsSold: 350,
                maximumCapacity: 500
            },
            {
                price: 100,
                name: 'VIP',
                ticketsSold: 100,
                maximumCapacity: 200
            }
        ],
        _class: 'co.edu.uniquindio.proyecto.model.Events.Event'
    }
]);

// Account

// Cart

// Coupon

// Order