<?php 
require_once 'conexion.php';

$nombre = $_POST['nombre'];
$apellido = $_POST['apellido'];
$nombreUsuario = $_POST['nombreUsuario'];
$pwd = $_POST['pwd'];

$q = 'INSERT INTO cliente(nombre, apellido, nombreUsuario, pwd) VALUES ("' . $nombre . '","' . $apellido . '","' . $nombreUsuario . '","' . $pwd . '")';
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>