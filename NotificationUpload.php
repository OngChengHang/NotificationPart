<?php

$servername = "localhost";

// for testing the user name is root.
$username = "root";

// the password for testing is "blank"
$password = "";

// below is the name for our
// database which we have added.
$dbname = "notificationresult";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// an array to display response
$response = array();
// on below line we are checking if the body provided by user contains
// this keys as course name,course description and course duration
if(isset($_POST['id']) &&isset($_POST['UserID']) && isset($_POST['competitionResult']) && isset($_POST['competitionDescription'])){
	// if above three parameters are present then we are extravting values
	// from it and storing it in new variables.
	$id = $_POST['id'];
	$UserID = $_POST['UserID'];
	$competitionResult = $_POST['competitionResult'];
	$competitionDescription = $_POST['competitionDescription'];
	// after that we are writing an sql query to
	// add this data to our database.
	// on below line make sure to add your table name
	// in previous article we have created our table name
	// as courseDb and add all column headers to it except our id.
	$stmt = $conn->prepare("INSERT INTO `result`(`id`,`UserID`, `competitionResult`, `competitionDescription`) VALUES (?,?,?,?)");
	$stmt->bind_param('ssss', $id, $UserID, $competitionResult, $competitionDescription);
// on below line we are checking if our sql query is executed successfully.
if($stmt->execute() == TRUE){
		// if the script is executed successfully we are
		// passing data to our response object
		// with a success message.
		$response['error'] = false;
		$response['message'] = "Created Successfully!";
	} else{
		// if we get any error we are passing error to our object.
		$response['error'] = true;
		$response['message'] = "failed\n ".$conn->error;
	}
} else{
	// this msethod is called when user
	// donot enter sufficient parameters.
	$response['error'] = true;
	$response['message'] = "Insufficient parameters";
}
// at last we are prinintg our response which we get.
echo json_encode($response);
?>
