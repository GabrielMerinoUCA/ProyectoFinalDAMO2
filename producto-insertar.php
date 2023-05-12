<?php
require_once 'conexion.php';

$idVendedor = $_POST['idVendedor'];
$nombre = $_POST['nombre'];
$descripcion = $_POST['descripcion'];
$precio = $_POST['precio'];
$imagen = $_POST['imagen'];
$disponibilidad = $_POST['disponibilidad'];
$tiempoEstimado = $_POST['tiempoEstimado'];

//Obtener el idTienda del vendedor

$q = "SELECT * FROM vendedor WHERE idVendedor = '$idVendedor'";
$query = mysqli_query($con, $q);

while($row = $query->fetch_array()){
    $idTienda = $row['idTienda'];
}

//Insertar el producto en la base de datos

$q = "INSERT INTO producto (idTienda, nombre, descripcion, precio, imagen, disponibilidad, tiempoEstimado) VALUES ('$idTienda', '$nombre', '$descripcion', '$precio', '$imagen', '$disponibilidad', '$tiempoEstimado')";

$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);
?>