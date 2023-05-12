<?php
require_once 'conexion.php';

$nombre = $_POST['nombre'];
$descripcion = $_POST['descripcion'];
$precio = $_POST['precio'];
$imagen = $_POST['imagen'];
$idTienda = $_POST['idTienda'];
$disponibilidad = $_POST['disponibilidad'];
$tiempoEstimado = $_POST['tiempoEstimado'];

$q = 'INSERT INTO producto(nombre, descripcion, precio, imagen, idTienda, disponibilidad, tiempoEstimado) VALUES ("' . $nombre . '","' . $descripcion . '","' . $precio . '","' . $imagen . '","' . $idTienda . '","' . $disponibilidad . '","' . $tiempoEstimado . '")';
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);
?>