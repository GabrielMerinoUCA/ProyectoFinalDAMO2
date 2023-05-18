<?php

require_once 'conexion.php';

$idProducto = $_POST['idProducto'];

$q = 'DELETE FROM producto WHERE idProducto = "' . $idProducto . '"';
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>