<?php

require_once 'conexion.php';

$idVendedor = $_POST['idVendedor'];

//Obtener el idTienda del vendedor

$q = "SELECT * FROM vendedor WHERE idVendedor = '$idVendedor'";
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $idTienda = $row['idTienda'];
}

//Obtener los productos de la tienda

$q = "SELECT * FROM producto WHERE idTienda = '$idTienda'";
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $json[] = array('id'=>$row['idProducto'], 'nombre'=>$row['nombre'], 'precio'=>$row['precio'], 'descripcion'=>$row['descripcion'], 'imagen'=>$row['imagen'], 'idTienda'=>$row['idTienda'], 'disponibilidad'=>$row['disponibilidad'], 'tiempoEstimado'=>$row['tiempoEstimado'] );
}

echo json_encode($json);

?>