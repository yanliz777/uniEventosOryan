db = connect( 'mongodb://localhost:27017/unieventos' );
db.cuentas.insertMany([
    {
        _id: ObjectId('66a2a9aaa8620e3c1c5437be'),
        rol: 'CLIENTE',
        email: 'pepeperez@email.com',
        codigoValidacionRegistro: {
            codigo: "9T5LEC",
            fechaCreacion: ISODate('2024-10-07T21:41:57.849Z'),
        },
        usuario: {
            telefono: '3012223333',
            direccion: 'Calle 12 # 12-12',
            cedula: '1213444',
            nombre: 'Pepito perez',
        },
        fechaRegistro: ISODate('2024-10-07T21:41:57.849Z'),
        password: '$2a$10$I2DJHVvWz15i9wim5lvrbOdu2CYTRMduW8clipylgL6yfwJuiZzx6',
        estado: 'INACTIVO',
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta'
    },
    {
        _id: ObjectId('66a2b9bbb8620e3c1c5437bf'),
        rol: 'CLIENTE',
        email: 'juanperez@email.com',
        usuario: {
            telefono: '3124445555',
            direccion: 'Carrera 45 # 67-89',
            cedula: '9876543',
            nombre: 'Juan Perez',
        },
        fechaRegistro: ISODate('2024-09-25T14:30:22.123Z'),
        password: '$2a$10$I2DJHVvWz15i9wim5lvrbOdu2CYTRMduW8clipylgL6yfwJuiZzx6',
        estado: 'ACTIVO',
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta'
    },
    {
        _id: ObjectId('66a2c9ccc8620e3c1c5437c0'),
        rol: 'ADMINISTRADOR',
        email: 'admin@email.com',
        codigoValidacionRegistro: {
            codigo: "D9F8G7",
            fechaCreacion: ISODate('2024-08-30T11:20:45.500Z'),
        },
        usuario: {
            telefono: '3001234567',
            direccion: 'Avenida Siempre Viva # 123',
            cedula: '3456789',
            nombre: 'Ana Rodriguez',
        },
        fechaRegistro: ISODate('2024-08-30T11:20:45.500Z'),
        password: '$2a$10$I2DJHVvWz15i9wim5lvrbOdu2CYTRMduW8clipylgL6yfwJuiZzx6',
        estado: 'INACTIVO',
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta'
    },
    {
        _id: ObjectId('66a2d9ddd8620e3c1c5437c1'),
        rol: 'CLIENTE',
        email: 'agente1@email.com',
        usuario: {
            telefono: '3018889999',
            direccion: 'Calle 23 # 56-78',
            cedula: '2233445',
            nombre: 'Carlos García',
        },
        fechaRegistro: ISODate('2024-07-18T09:15:33.254Z'),
        password: '$2a$10$I2DJHVvWz15i9wim5lvrbOdu2CYTRMduW8clipylgL6yfwJuiZzx6',
        estado: 'ACTIVO',
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta'
    },
    {
        _id: ObjectId('66a2e9eee8620e3c1c5437c2'),
        rol: 'CLIENTE',
        email: 'mariagonzalez@email.com',
        codigoValidacionRegistro: {
            codigo: "X5R7P1",
            fechaCreacion: ISODate('2024-06-15T13:45:00.789Z'),
        },
        usuario: {
            telefono: '3105678901',
            direccion: 'Carrera 10 # 34-56',
            cedula: '4455667',
            nombre: 'Maria Gonzalez',
        },
        fechaRegistro: ISODate('2024-06-15T13:45:00.789Z'),
        password: '$2a$10$I2DJHVvWz15i9wim5lvrbOdu2CYTRMduW8clipylgL6yfwJuiZzx6',
        estado: 'INACTIVO',
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Cuenta'
    }
]);
db.eventos.insertMany([
    {
        _id: ObjectId('66e6d2a1bc42fd7a8e7e82a5'),
        nombre: 'Concierto de despedida del 2024',
        descripcion: 'Concierto con los mejores artistas del 2024 para despedir el año',
        direccion: 'Coliseo de eventos, calle 10 # 10-10',
        imagenPortada: 'Url de la imagen del poster del concierto',
        imagenLocalidades: 'Url de la imagen de la distribución de las localidades',
        ciudad: 'Armenia',
        localidades: [
            {
                nombre: 'VIP',
                precio: 80000,
                capacidadMaxima: 50
            },
            {
                nombre: 'PLATEA',
                precio: 50000,
                capacidadMaxima: 100
            },
            {
                nombre: 'GENERAL',
                precio: 20000,
                capacidadMaxima: 200
            }
        ],
        tipo: 'CONCIERTO',
        fechaEvento: ISODate('2024-11-11T01:00:00.000Z'),
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Evento'
    },
    {
        _id: ObjectId('66a3d123991cff088eb80b12'),
        nombre: 'Feria de Belleza 2024',
        descripcion: 'El evento más esperado del año en el mundo de la belleza con los mejores expertos y marcas.',
        direccion: 'Centro de Convenciones, Carrera 20 # 15-25',
        imagenPortada: 'Url de la imagen del poster de la feria',
        imagenLocalidades: 'Url de la imagen de la distribución de las localidades',
        ciudad: 'Bogotá',
        localidades: [
            {
                nombre: 'VIP',
                precio: 100000,
                capacidadMaxima: 30
            },
            {
                nombre: 'GENERAL',
                precio: 50000,
                capacidadMaxima: 300
            }
        ],
        tipo: 'BELLEZA',
        fechaEvento: ISODate('2024-12-01T10:00:00.000Z'),
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Evento'
    },
    {
        _id: ObjectId('66a3e124991cff088eb80b13'),
        nombre: 'Festival de Teatro Cultural',
        descripcion: 'Un espacio para las mejores representaciones culturales del teatro contemporáneo.',
        direccion: 'Teatro Principal, Avenida 5 # 22-18',
        imagenPortada: 'Url de la imagen del festival',
        imagenLocalidades: 'Url de la imagen de la distribución de las localidades',
        ciudad: 'Medellín',
        localidades: [
            {
                nombre: 'PLATEA',
                precio: 60000,
                capacidadMaxima: 150
            },
            {
                nombre: 'BALCÓN',
                precio: 30000,
                capacidadMaxima: 100
            }
        ],
        tipo: 'CULTURAL',
        fechaEvento: ISODate('2024-10-25T19:00:00.000Z'),
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Evento'
    },
    {
        _id: ObjectId('66a3f235991cff088eb80b14'),
        nombre: 'Maratón de la Ciudad 2024',
        descripcion: 'Participa en la maratón más grande del año con recorridos de 5K, 10K y 21K.',
        direccion: 'Parque Central, Calle 40 # 30-10',
        imagenPortada: 'Url de la imagen del poster de la maratón',
        imagenLocalidades: 'Url de la imagen de la ruta de la maratón',
        ciudad: 'Cali',
        localidades: [
            {
                nombre: 'INSCRIPCIÓN GENERAL',
                precio: 100000,
                capacidadMaxima: 500
            }
        ],
        tipo: 'DEPORTE',
        fechaEvento: ISODate('2024-09-15T06:00:00.000Z'),
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Evento'
    },
    {
        _id: ObjectId('66a3g456991cff088eb80b15'),
        nombre: 'Pasarela de Moda Internacional',
        descripcion: 'Las últimas tendencias del mundo de la moda presentadas por diseñadores de talla internacional.',
        direccion: 'Centro de Convenciones, Calle 50 # 20-40',
        imagenPortada: 'Url de la imagen del evento de moda',
        imagenLocalidades: 'Url de la imagen de la distribución de las localidades',
        ciudad: 'Cartagena',
        localidades: [
            {
                nombre: 'FRONTAL',
                precio: 120000,
                capacidadMaxima: 100
            },
            {
                nombre: 'LATERAL',
                precio: 60000,
                capacidadMaxima: 200
            }
        ],
        tipo: 'MODA',
        fechaEvento: ISODate('2024-11-30T18:00:00.000Z'),
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Evento'
    }
]);
db.cupones.insertMany([
    {
        _id: ObjectId('66e6d1fabc42fd7a8e7e82a3'),
        nombre: 'BLACK FRIDAY',
        descuento: 30,
        codigo: 'FRDYKQXCVMEQ34L',
        fechaVencimiento: ISODate('2023-12-01T00:00:00.000Z'),
        tipo: 'MULTIPLE',
        estado: 'DISPONIBLE',
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Cupon'
    },
    {
        _id: ObjectId('66e6d2a1bc42fd7a8e7e82a4'),
        nombre: 'VERANO SALE',
        descuento: 15,
        codigo: 'VER4NOKD34JHX1',
        fechaVencimiento: ISODate('2023-11-15T00:00:00.000Z'),
        tipo: 'UNICO',
        estado: 'DISPONIBLE',
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Cupon'
    },
    {
        _id: ObjectId('66e6d3b2bc42fd7a8e7e82a5'),
        nombre: 'PRIMAVERA X3',
        descuento: 25,
        codigo: 'PRIMVG5FG9JQ1X',
        fechaVencimiento: ISODate('2023-10-30T00:00:00.000Z'),
        tipo: 'MULTIPLE',
        estado: 'ELIMINADO',
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Cupon'
    },
    {
        _id: ObjectId('66e6d4c3bc42fd7a8e7e82a6'),
        nombre: 'CIBER MONDAY',
        descuento: 40,
        codigo: 'CYMNLT7Q23KFG9',
        fechaVencimiento: ISODate('2023-12-05T00:00:00.000Z'),
        tipo: 'MULTIPLE',
        estado: 'DISPONIBLE',
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Cupon'
    },
    {
        _id: ObjectId('66e6d5d4bc42fd7a8e7e82a7'),
        nombre: 'HALLOWEEN DEALS',
        descuento: 50,
        codigo: 'HAL45XQJ9BWE2K',
        fechaVencimiento: ISODate('2023-10-31T00:00:00.000Z'),
        tipo: 'UNICO',
        estado: 'DISPONIBLE',
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Cupon'
    }
]);
db.carritos.insertMany([
    {
        _id: ObjectId('66eef16e37310828425bbe10'),
        idUsuario: ObjectId('66a2a9aaa8620e3c1c5437be'),
        fecha: ISODate('2024-10-01T15:00:00.000Z'),
        items: [
            {
                idEvento: ObjectId('66e6d4c3bc42fd7a8e7e82a6'),
                cantidad: 2,
                nombreLocalidad: "PLATEA"
            }
        ],
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Carrito'
    },
    {
        _id: ObjectId('66eef2d637310828425bbe11'),
        idUsuario: ObjectId('66a2b9bbb8620e3c1c5437bf'),
        fecha: ISODate('2024-10-02T18:30:00.000Z'),
        items: [
            {
                idEvento: ObjectId('66e6d4c3bc42fd7a8e7e82a6'),
                cantidad: 1,
                nombreLocalidad: "GENERAL"
            },
            {
                idEvento: ObjectId('66a3e124991cff088eb80b13'),
                cantidad: 3,
                nombreLocalidad: "VIP"
            }
        ],
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Carrito'
    },
    {
        _id: ObjectId('66eef3e137310828425bbe12'),
        idUsuario: ObjectId('66a2e9eee8620e3c1c5437c2'),
        fecha: ISODate('2024-10-03T10:45:00.000Z'),
        items: [
            {
                idEvento: ObjectId('66e6d4c3bc42fd7a8e7e82a6'),
                cantidad: 5,
                nombreLocalidad: "BALCÓN"
            }
        ],
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Carrito'
    },
    {
        _id: ObjectId('66eef4f737310828425bbe13'),
        idUsuario: ObjectId('66a2d9ddd8620e3c1c5437c1'),
        fecha: ISODate('2024-10-04T16:00:00.000Z'),
        items: [
            {
                idEvento: ObjectId('66e6d4c3bc42fd7a8e7e82a6'),
                cantidad: 4,
                nombreLocalidad: "GENERAL"
            }
        ],
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Carrito'
    }
]);
db.ordenes.insertMany([
    {
        _id: ObjectId('66a2c6a55773597d73593fff'),
        detalle: [
            {
                codigoEvento: ObjectId('66a3d123991cff088eb80b12'),
                nombreLocalidad: 'PLATEA',
                precio: 50000,
                cantidad: 2
            }
        ],
        codigoCliente: ObjectId('66a2a9aaa8620e3c1c5437be'),
        total: 100000,
        fecha: ISODate('2024-07-25T21:41:57.849Z'),
        codigoPasarela: 'CODIGO_PASARELA',
        pago: {
            codigo: '48dc3dd9-bde1-45ae-b23f-27ee7a261f00',
            fecha: ISODate('2024-07-25T21:41:57.849Z'),
            totalPagado: 100000,
            estado: 'APROBADA',
            metodoPago: 'TARJETA DE CRÉDITO'
        },
        estado: "ACTIVA",
        _class: 'co.edu.uniquindio.uniEventos.modelo.documentos.Orden'
    }
]);