<?php

require_once 'conexion.php';

$nombreUsuario = $_POST['nombreUsuario'];
$pwd = $_POST['pwd'];
$idTienda = $_POST['idTienda'];

$q = "INSERT INTO vendedor(nombreUsuario, pwd, idTienda) VALUES ('$nombreUsuario','$pwd','$idTienda')";
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>