<?php

require_once 'conexion.php';

$idAdministrador = $_POST['idAdministrador'];

$q = "DELETE FROM administrador WHERE idAdministrador = '$idAdministrador'";
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>