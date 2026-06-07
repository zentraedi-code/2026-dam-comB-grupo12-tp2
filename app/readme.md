app:
|-> dao: comunicacion con db. En SQLiteHelper estan las estructuras de la db, y en cada xxxDao los querys y updates e inserts a las tablas. Cada vez que se agregue un Dao se debe agregar metodo a SQLiteHelper getXXDao como getClienteDao()...
|-> domain: clases data/dto
|-> ui: como veniamos trabajando, estan los activities y los adapters (adapters para manejar listas)
|-> SplashActivity: hay una linea // deleteDatabase("club.db") que si se descomenta borra la db al iniciar el sistema por si se agregan nuevas columnas y tablas. Para que el cambio impacte lo mas facil es borrar la db.
