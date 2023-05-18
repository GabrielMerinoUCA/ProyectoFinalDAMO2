<?php 
require_once 'conexion.php';

$idCliente = $_POST['idCliente'];
$nombre = $_POST['nombre'];
$apellido = $_POST['apellido'];
$nombreUsuario = $_POST['nombreUsuario'];
$pwd = $_POST['pwd'];

$q = 'UPDATE cliente SET nombre = "' . $nombre . '", apellido = "' . $apellido . '", nombreUsuario = "' . $nombreUsuario . '", pwd = "' . $pwd . '" WHERE idCliente = "' . $idCliente . '"';
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>