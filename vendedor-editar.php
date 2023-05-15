<?php

require_once 'conexion.php';

$idVendedor = $_POST['idVendedor'];
$nombreUsuario = $_POST['nombreUsuario'];
$pwd = $_POST['pwd'];

$q = "UPDATE vendedor SET nombreUsuario = '$nombreUsuario', pwd = '$pwd' WHERE idVendedor = '$idVendedor'";
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>