<?php 
require_once 'conexion.php';

$nombre = $_POST['nombre'];
$apellido = $_POST['apellido'];
$nombreUsuario = $_POST['nombreUsuario'];
$nombreTienda = $_POST['nombreTienda'];
$horaApertura = $_POST['horaApertura'];
$horaCierre = $_POST['horaCierre'];
$pwd = $_POST['pwd'];

$q = 'INSERT INTO registro(nombre, apellido, nombreUsuario, nombreTienda, horaApertura, horaCierre, pwd, estado) VALUES ("' . $nombre . '","' . $apellido . '","' . $nombreUsuario . '","' . $nombreTienda . '","' . $horaApertura . '","' . $horaCierre . '","' . $pwd . '","'. 1 . '")';
$query = mysqli_query($con, $q);

if($query == true){
    $json[] = array('response' => 'true');
}else{
    $json[] = array('response' => 'false');
}

echo json_encode($json);

?>