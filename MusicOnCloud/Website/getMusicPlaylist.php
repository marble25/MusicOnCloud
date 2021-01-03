<?
$password = "고추장반딱이";
$pw_encode = md5($password);

if($_POST["pw"] == $pw_encode) { 
	include 'common.php';
} else {
	echo "Fail No 2";
}

$query = "SELECT * FROM `PLAYLISTS` WHERE `userId`='".htmlspecialchars($_POST['userId'])."';";
$result = mysqli_query($conn, $query);

if(isset($result)) {
	echo "Success/";
	while($row = mysqli_fetch_row($result)) {
		printf("%s/%s/", $row[0], $row[2]);
	}
} else {
	echo "Fail No 3";
}

mysqli_close($conn);

?>