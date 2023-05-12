<?php
require_once 'conexion.php';

$idProducto = $_POST['idProducto'];
$nombre = $_POST['nombre'];
$descripcion = $_POST['descripcion'];
$precio = $_POST['precio'];
$imagen = $_POST['imagen'];
$disponibilidad = $_POST['disponibilidad'];
$tiempoEstimado = $_POST['tiempoEstimado'];

$q = 'UPDATE producto SET nombre = "' . $nombre . '", descripcion = "' . $descripcion . '", precio = "' . $precio . '", imagen = "' . $imagen . '", disponibilidad = "' . $disponibilidad . '", tiempoEstimado = "' . $tiempoEstimado . '" WHERE idProducto = "' . $idProducto . '"';
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>