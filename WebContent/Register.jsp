<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>RegisterCostumer</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <h2>Register for Customer</h2>
            <p>
                Please enter all the required information for creating a new account for the customer
            </p>
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-sm-2" for="email">Email:</label>
                    <div class="col-sm-4">
                        <input type="email" class="form-control" id="email" placeholder="Enter email">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="pwd">Password:</label>
                    <div class="col-sm-4">          
                        <input type="password" class="form-control" id="pwd" placeholder="Enter password">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="firstname">First Name:</label>
                    <div class="col-sm-4">          
                        <input type="text" class="form-control" id="name" placeholder="Enter first name">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="lastname">Last Name:</label>
                    <div class="col-sm-4">          
                        <input type="text" class="form-control" id="name" placeholder="Enter last name">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="address">Address:</label>
                    <div class="col-sm-4">          
                        <input type="text" class="form-control" id="address" placeholder="Enter address">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="address">Zipcode:</label>
                    <div class="col-sm-2">          
                        <input type="number" class="form-control" id="zipcode">
                    </div>
                </div>    
                <div>    
                    <label class="control-label col-sm-offset-4">City:</label>
                    <div class="col-sm-offset-6">
                        <input type="text" class="form-control" id="city">
                    </div>
                </div>
                
                <div class="form-group">        
                    <div class="col-sm-offset-2 col-sm-8">
                      <button type="submit" class="btn btn-default">Create</button>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>