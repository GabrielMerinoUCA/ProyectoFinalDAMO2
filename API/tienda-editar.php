<?php

require_once 'conexion.php';

$idTienda = $_POST['idTienda'];
$nombre = $_POST['nombre'];
$horaApertura = $_POST['horaApertura'];
$horaCierre = $_POST['horaCierre'];
$logo = $_POST['logo'];

// Convertir la imagen en una cadena de caracteres binarios
$imagen_blob = base64_decode($logo);

// Insertar los datos en la base de datos
$q = 'UPDATE tienda SET nombre = ?, horaApertura = ?, horaCierre = ?, logo = ? WHERE idTienda = ?';
$stmt = mysqli_prepare($con, $q);
mysqli_stmt_bind_param($stmt, 'sssss', $nombre, $horaApertura, $horaCierre, $imagen_blob, $idTienda);

// Ejecutar la consulta
if (mysqli_stmt_execute($stmt)) {
    $json[] = array('response' => 'true');
} else {
    $json[] = array('response' => 'false', 'error' => mysqli_error($con));
}

echo json_encode($json);

?>