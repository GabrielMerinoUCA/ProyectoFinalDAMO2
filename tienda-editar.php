<?php

require_once 'conexion.php';

$idTienda = $_POST['idTienda'];
$nombre = $_POST['nombre'];
$horaApertura = $_POST['horaApertura'];
$horaCierre = $_POST['horaCierre'];
$logo = $_POST['logo'];

$q = 'UPDATE tienda SET nombre = "' . $nombre . '", horaApertura = "' . $horaApertura . '", horaCierre = "' . $horaCierre . '", logo = "' . $logo . '" WHERE idTienda = "' . $idTienda . '"';
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>