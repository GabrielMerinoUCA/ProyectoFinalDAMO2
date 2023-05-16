<?php

require_once 'conexion.php';

$idOrden = $_POST['idOrden'];

$q = "UPDATE orden SET estado = '1' WHERE idOrden = '$idOrden'";
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>