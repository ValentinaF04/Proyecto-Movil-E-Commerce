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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pcbuilder.ui.theme.PCBuilderTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
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
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}


@Composable
fun ProductoCard(producto: Producto, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { // falta agregar la funcion onclick para mostrar los detalles del producto (en proceso)

        }
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
        Producto(1, "Ryzen 5 5600X", "CPU", 150000, "URL FOTO PROCESADOR"),
        Producto(2, "Nvidia RTX 3060", "GPU", 350000, "URL FOTO TARJETA GRAFICA"),
        Producto(3, "Corsair Vengeance 16GB", "RAM", 80000, "URL FOTO RAM"),
        Producto(4, "Samsung 980 Pro 1TB", "SSD M.2", 120000, "URL FOTO SSD"),
        Producto(5, "ASUS B550-F", "Motherboard", 180000, "URL FOTO MOTHERBOARD"),
        Producto(6, "NZXT H510", "Gabinete", 90000, "URL FOTO GABINETE")
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewProductoCard() {
    val productoEjemplo = Producto(1, "Producto de Ejemplo", "Desc.", 99000, "")
    PCBuilderTheme { 
        ProductoCard(producto = productoEjemplo, modifier = Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewCatalogoScreen() {
    PCBuilderTheme { 
        CatalogoScreen(productos = obtenerDatos())
    }
}