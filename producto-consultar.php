<?php
require_once 'conexion.php';

//Consultar productos de una tienda
$idTienda = $_POST['idTienda'];

$q = 'SELECT * FROM producto WHERE idTienda = ' . $idTienda;
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $json[] = array('id'=>$row['idProducto'], 'nombre'=>$row['nombre'], 'precio'=>$row['precio'], 'descripcion'=>$row['descripcion'], 'imagen'=>$row['imagen'], 'idTienda'=>$row['idTienda'], 'disponibilidad'=>$row['disponibilidad'], 'tiempoEstimado'=>$row['tiempoEstimado'] );
}

echo json_encode($json);
?>