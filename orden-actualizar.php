<?php

require_once 'conexion.php';

$idOrden = $_POST['idOrden'];
$horaReclamo = $_POST['horaReclamo'];
$estado = $_POST['estado'];

$q = "UPDATE orden SET horaReclamo = '$horaReclamo', estado = '$estado' WHERE idOrden = '$idOrden'";
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>