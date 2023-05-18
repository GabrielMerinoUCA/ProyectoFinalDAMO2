<?php

require_once 'conexion.php';

$idCliente = $_POST['idCliente'];

$q = 'DELETE FROM cliente WHERE idCliente = "' . $idCliente . '"';
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>