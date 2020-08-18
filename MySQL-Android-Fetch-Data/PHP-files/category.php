<?php

	//Please carefully read README file
	//Read all the comments carefully...
	
	//This config file contain the information of DatabaseName,DatabaseHost,DatabasePassword... I think everybody knows about this!
    require "config.php";
    
	//Taken From application   See my Category activity to how to take these datas from application REQUEST Link
    $id = $_REQUEST["id"];
    $username = $_REQUEST["username"];
    
	//This is required to how to application read the JSON object data (Listen Carefully --- It is very simple)
    $result = array();
    $result["data"] = array();
    
	//This is shopping application so I am here counting items in my cart
    $sql = "SELECT COUNT(id) FROM order_table WHERE username ='".$username."' AND ordered = 0";
    $stmt = mysqli_prepare($conn,$sql);
    mysqli_stmt_execute($stmt);
    mysqli_stmt_store_result($stmt);
    mysqli_stmt_bind_result($stmt,$cart_item_count);
    mysqli_stmt_fetch($stmt);
    mysqli_stmt_close($stmt);
    
	//I am selecting the name of category of items available in my shopping application...!!
    $sql = "SELECT name FROM category WHERE id=".$id." ";
    $stmt = mysqli_prepare($conn,$sql);
    mysqli_stmt_execute($stmt);
    mysqli_stmt_store_result($stmt);
    mysqli_stmt_bind_result($stmt,$category);
    mysqli_stmt_fetch($stmt);
    mysqli_stmt_close($stmt);
	
    //Here is the values which we are going to print on our Application
    $sql = "SELECT * FROM products WHERE category='".$category."' ORDER BY p_name";
    $response = mysqli_query($conn,$sql);
    
    while($row = mysqli_fetch_array($response))
    {
        $index["id"] = $row["id"];
        $index["image"] = $row["p_image"];
        $index["name"] = $row["p_name"];
        
        $sql_qty = "SELECT quantity,price,oldprice FROM quantity WHERE p_id =".$row['id']." LIMIT 1";
        $response_qty = mysqli_query($conn,$sql_qty);
        while($row_qty = mysqli_fetch_array($response_qty))
        {
            $index["quantity"] = $row_qty["quantity"];
            $index["price"] = $row_qty["price"];
            $index["oldprice"] = $row_qty["oldprice"];
        }
        
        array_push($result["data"], $index);
    }
    
    $result["cart_item_count"] = $cart_item_count;
    $result["category_name"] = $category;
    $result["success"] = "1";
	
	//Here we are printing all of our information into JSON object which read by volley library (No need to worry about this...! Volley will handle all that!!!)
    echo json_encode($result);

?>