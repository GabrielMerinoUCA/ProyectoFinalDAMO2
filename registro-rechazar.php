<?php

require_once 'conexion.php';

$idRegistro = $_POST['idRegistro'];

$q = "UPDATE registro SET estado = '2' WHERE idRegistro = '$idRegistro'";
$query = mysqli_query($con, $q);

if($query){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>