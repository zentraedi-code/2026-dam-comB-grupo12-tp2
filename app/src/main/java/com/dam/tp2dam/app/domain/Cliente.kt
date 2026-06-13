data class Cliente(
    val dni: String,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val email: String,
    val habilitado: Boolean,
    val esSocio: Boolean,
    val fechaAlta: String,
    val tipoCliente: String,   // "SOCIO" o "NO_SOCIO"
    val aptoFisico: Boolean?
)