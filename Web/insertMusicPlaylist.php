<?
$password = "고추장반딱이";
$pw_encode = md5($password);

if($_POST["pw"] == $pw_encode) { 
	include 'common.php';
} else {
	echo "Fail No 2";
}

$query = "SELECT * FROM `PLAYLISTS` WHERE `name`='".htmlspecialchars($_POST['name'])."' AND `userId`='".htmlspecialchars($_POST['userId'])."';";
$result = mysqli_query($conn, $query);

if(mysqli_num_rows($result) == 0) {
	$query = "INSERT INTO `PLAYLISTS` (`userId`, `name`) VALUES ('".htmlspecialchars($_POST["userId"])."', '".htmlspecialchars($_POST["name"])."');";
	if(mysqli_query($conn, $query)) {
		$query = "SELECT * FROM `PLAYLISTS` WHERE `name`='".htmlspecialchars($_POST['name'])."' AND `userId`='".htmlspecialchars($_POST['userId'])."';";
		$result = mysqli_query($conn, $query);
		$row = mysqli_fetch_row($result);

		echo "Success/".$row[0];
	} else {
		echo "Fail No 3";
	}
} else {
	echo "Same Name Already Exists";
}

mysqli_close($conn);

?>