package com.example.pcbuilder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController 
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pcbuilder.data.model.Producto
import com.example.pcbuilder.ui.theme.PCBuilderTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    navController: NavController,
    productos: List<Producto>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Productos") }
            )
        },
        modifier = modifier
    ) { paddingValues ->


        LazyVerticalGrid(

            columns = GridCells.Adaptive(minSize = 160.dp),

            modifier = Modifier.padding(paddingValues),

            contentPadding = PaddingValues(8.dp)
        ) {
            items(productos) { producto ->
                ProductoCard(
                    producto = producto,
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        navController.navigate("detalle/${producto.id}")
                    }
                )
            }
        }
    }
}


@Composable
fun ProductoCard(producto: Producto, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(producto.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagen de ${producto.nombre}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$${"%.2f".format(producto.precio)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * Función de ayuda para generar productos
 * falta agregarle las url de las fotos
 */
fun obtenerDatos(): List<Producto> {
    return listOf(
        Producto(1, "Ryzen 5 5600X", "CPU", 150000, "https://www.amd.com/content/dam/amd/en/images/products/processors/ryzen/2505503-ryzen-5-5600x.jpg"),
        Producto(2, "Nvidia RTX 3060", "GPU", 350000, "https://media.spdigital.cl/thumbnails/products/1760965689339-b3_6db8e262_c241b8a4_thumbnail_512.png"),
        Producto(3, "Corsair Vengeance 16GB", "RAM", 80000, "https://rstech.cl/wp-content/uploads/2023/02/ram000000001.png"),
        Producto(4, "Samsung 980 Pro 1TB", "SSD M.2", 120000, "https://m.media-amazon.com/images/I/61Mo8ug0aQS._AC_SL1500_.jpg"),
        Producto(5, "ASUS B550-F", "Motherboard", 180000, "https://dlcdnwebimgs.asus.com/gain/803CCAEB-848C-416A-A24C-B107B9575134"),
        Producto(6, "NZXT H510", "Gabinete", 90000, "https://i5.walmartimages.com/seo/NZXT-Case-H510-Elite-Mid-Tower-Elite-Matte-White_3870aeea-bb96-4ce8-98f1-65863ed9cab5.69c58b377d14de20883ce8302f8a18a3.jpeg")
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewProductoCard() {
    val productoEjemplo = Producto(1, "Producto de Ejemplo", "Desc.", 99000, "")
    PCBuilderTheme {
        ProductoCard(
            producto = productoEjemplo,
            modifier = Modifier.padding(8.dp),
            onClick = {} 
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewCatalogoScreen() {
    val navController = rememberNavController() 
    PCBuilderTheme {
        CatalogoScreen(
            navController = navController, 
            productos = obtenerDatos()
        )
    }
}