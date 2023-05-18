$db_host = "us-cdbr-east-06.cleardb.net";
$db_user = "b15343bed58c83";
$db_pass = "0629ffa2";
$db_name = "heroku_856dfefc0b0fdac";

$con = mysqli_connect($db_host, $db_user, $db_pass, $db_name);

if(!$con){
    echo 'Error al conectar';
}