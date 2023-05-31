<?php
require_once 'conexion.php';

$idProducto = $_POST['idProducto'];
$nombre = $_POST['nombre'];
$descripcion = $_POST['descripcion'];
$precio = $_POST['precio'];
$imagen = $_POST['imagen'];
$tiempoEstimado = $_POST['tiempoEstimado'];

// Convertir la imagen en una cadena de caracteres binarios
$imagen_blob = base64_decode($imagen);

// Insertar los datos en la base de datos
$q = 'UPDATE producto SET nombre = ?, precio = ?, descripcion = ?, imagen = ?, tiempoEstimado = ? WHERE idProducto = ?';
$stmt = mysqli_prepare($con, $q);
mysqli_stmt_bind_param($stmt, 'ssssss', $nombre, $precio, $descripcion, $imagen_blob, $tiempoEstimado, $idProducto);

// Ejecutar la consulta
if (mysqli_stmt_execute($stmt)) {
    $json[] = array('response' => 'true');
} else {
    $json[] = array('response' => 'false', 'error' => mysqli_error($con));
}

echo json_encode($json);

?>