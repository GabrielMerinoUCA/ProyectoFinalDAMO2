<?php

require_once 'conexion.php';

$idVendedor = $_POST['idVendedor'];

$q = 'DELETE FROM vendedor WHERE idVendedor = "' . $idVendedor . '"';
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>