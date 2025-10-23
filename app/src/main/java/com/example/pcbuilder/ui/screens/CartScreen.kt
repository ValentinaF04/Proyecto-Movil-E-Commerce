@Composable
fun CartScreen(navController: NavController) {
    val context = LocalContext.current
    val db = (context.applicationContext as PCBuilderApplication).database
    val factory = AppViewModelFactory(db.userDao(), db.productDao(), db.cartDao())
    val viewModel: CartViewModel = viewModel(factory = factory)
    val sessionManager = remember { SessionManager(context) }
    val userId by sessionManager.userIdFlow.collectAsState(initial = null)

    Column(Modifier.padding(16.dp)) {
        Text("Tu Carrito", style = MaterialTheme.typography.headlineMedium)

        userId?.let { id ->
            val items by viewModel.getCartItems(id).collectAsState(initial = emptyList())

            if (items.isEmpty()) {
                Text("Tu carrito está vacío.")
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(items) { cartItem ->
                        CartItemRow(item = cartItem, viewModel = viewModel)
                    }
                }

                //botón de pago
                Button(
                    onClick = { navController.navigate("checkout") }, 
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Proceder al Pago")
                }
            }
        } ?: Text("Inicia sesión para ver tu carrito.")
    }
}

@Composable
fun CartItemRow(item: CartItem, viewModel: CartViewModel) {
    val product by viewModel.getProductById(item.productId).collectAsState(initial = null)

    product?.let { p ->
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text(p.name, modifier = Modifier.weight(1f))
            Text("Cant: ${item.quantity}")
            Text("$$${p.price * item.quantity}", modifier = Modifier.padding(start = 16.dp))
        }
    } ?: Text("Cargando producto...")
}