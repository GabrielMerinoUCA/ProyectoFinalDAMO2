<?php

require_once 'conexion.php';

$idTienda = $_POST['idTienda'];

$q = 'DELETE FROM tienda WHERE idTienda = "' . $idTienda . '"';
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>