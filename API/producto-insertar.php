<?php
    // Conexión a la base de datos
    require_once 'conexion.php';

    // Recibir los datos del formulario
    $idTienda = $_POST['idTienda'];
    $nombre = $_POST['nombre'];
    $descripcion = $_POST['descripcion'];
    $precio = $_POST['precio'];
    $imagen = $_POST['imagen'];
    $tiempoEstimado = $_POST['tiempoEstimado'];

    // Convertir la imagen en una cadena de caracteres binarios
    $imagen_blob = base64_decode($imagen);

    // Insertar los datos en la base de datos
    $q = 'INSERT INTO producto (nombre, precio, descripcion, imagen, idTienda, disponibilidad, tiempoEstimado) VALUES (?, ?, ?, ?, ?, 1, ?)';
    $stmt = mysqli_prepare($con, $q);
    mysqli_stmt_bind_param($stmt, 'ssssss', $nombre, $precio, $descripcion, $imagen_blob, $idTienda, $tiempoEstimado);

    // Ejecutar la consulta
    if (mysqli_stmt_execute($stmt)) {
        $json[] = array('response' => 'true');
    } else {
        $json[] = array('response' => 'false', 'error' => mysqli_error($con));
    }

    // Enviar la respuesta
    echo json_encode($json);
?>