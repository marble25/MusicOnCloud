<?
$password = "고추장반딱이";
$pw_encode = md5($password);

if($_POST["pw"] == $pw_encode) { 
	include 'common.php';
} else {
	echo "Fail No 2";
}

$query = "SELECT * FROM `PLAYLIST_SONGS` WHERE `playId`='".htmlspecialchars($_POST['playId'])."';";
$result = mysqli_query($conn, $query);

if(isset($result)) {
	$songs = array();
	echo "Success/";
	while($row = mysqli_fetch_row($result)) {
		$songs[] = $row[2];
	}
	foreach($songs as $value) {
		$query = "SELECT * FROM `MUSICS` WHERE `musicId`='".$value."' LIMIT 1;";
		$result = mysqli_query($conn, $query);
		$row = mysqli_fetch_row($result);
		printf("%s/%s/%s/%s/", $row[2], $row[3], $row[4], $row[5]);
	}
	
} else {
	echo "Fail No 3";
}

mysqli_close($conn);

?>