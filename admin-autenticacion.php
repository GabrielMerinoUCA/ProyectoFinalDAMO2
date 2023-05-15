<?php

require_once 'conexion.php';

$nombreUsuario = $_POST['nombreUsuario'];
$pwd = $_POST['pwd'];

$q = "SELECT * FROM administrador WHERE nombreUsuario = '$nombreUsuario'";
$query = mysqli_query($con, $q);

$usuarioObtenido = $query ->fetch_array();

if($usuarioObtenido == null){
    $json[] = array('response' => 'false');
}else{
    if($nombreUsuario == $usuarioObtenido['nombreUsuario'] && $pwd == $usuarioObtenido['pwd']){
        $json[] = array('response' => 'true');
    }else{
        $json[] = array('response' => 'false');
    }
}

echo json_encode($json);

?>