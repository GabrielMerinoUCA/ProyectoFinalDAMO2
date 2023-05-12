<?php
require_once 'conexion.php';

$nombre = $_POST['nombre'];
$horaApertura = $_POST['horaApertura'];
$horaCierre = $_POST['horaCierre'];
$logo = $_POST['logo'];
$estado = $_POST['estado'];

$q = 'INSERT INTO tienda(nombre, horaApertura, horaCierre, logo, estado) VALUES ("' . $nombre . '","' . $horaApertura . '","' . $horaCierre . '","' . $logo . '","' . $estado . '")';
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);
?>