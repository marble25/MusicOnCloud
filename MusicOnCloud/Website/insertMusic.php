<?
$password = "고추장반딱이";
$pw_encode = md5($password);

if($_POST["pw"] == $pw_encode) { 
	include 'common.php';
} else {
	echo "Fail No 2";
}

$query = "SELECT * FROM `MUSICS` WHERE `musicid`=".htmlspecialchars($_POST['musicId']).";";
$result = mysqli_query($conn, $query);

if(mysqli_num_rows($result) == 0) {
	$query = "INSERT INTO `MUSICS` (`musicId`, `trackId`, `title`, `artists`, `album`) values ('".htmlspecialchars($_POST["musicId"])."', '".htmlspecialchars($_POST["trackId"])."', '".htmlspecialchars($_POST["title"])."', '".htmlspecialchars($_POST["artists"])."', '".htmlspecialchars($_POST["album"])."');";
	
	if(mysqli_query($conn, $query)) {
		echo "Success";
	} else {
		echo "Fail No 3";
	}
} else {
	echo "Same Music Already Exists";
}

mysqli_close($conn);

?>