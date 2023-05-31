<?php
$db_host = "us-cdbr-east-06.cleardb.net";
$db_user = "bd15cdaf046071";
$db_pass = "94be2fa9";
$db_name = "heroku_e0833ed3cb9b577";

$con = mysqli_connect($db_host, $db_user, $db_pass, $db_name);

if(!$con){
    echo 'Error al conectar';
}
?>