@Composable
fun CheckoutScreen(navController: NavController) {
    val context = LocalContext.current
    val db = (context.applicationContext as PCBuilderApplication).database
    val factory = AppViewModelFactory(db.userDao(), db.productDao(), db.cartDao())
    val viewModel: CartViewModel = viewModel(factory = factory)
    val sessionManager = remember { SessionManager(context) }
    val userId by sessionManager.userIdFlow.collectAsState(initial = null)
    val scope = rememberCoroutineScope()

    Column(Modifier.padding(16.dp)) {
        Text("Simulación de Pago", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Número de Tarjeta") })
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Nombre en Tarjeta") })

        Button(
            onClick = {
                userId?.let { id ->
                    scope.launch {
                        viewModel.performCheckout(id) {
                            navController.navigate("compra_exitosa") {
                                popUpTo("catalogo") 
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Pagar Ahora")
        }
    }
}