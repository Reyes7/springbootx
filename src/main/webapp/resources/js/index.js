var userApp = angular.module('userApp', ['ngRoute']);

userApp.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl : 'resources/pages/login.html',
			controller  : 'loginController'
		})
		.when('/register', {
        	templateUrl : 'resources/pages/register.html',
        	controller  : 'registerController'
        })
		.when('/user', {
        	templateUrl : 'resources/pages/user_panel.html',
        	controller  : 'panelController'
        });
});

userApp.controller('panelController', function($scope, $window) {

	$scope.firstName = $window.sessionStorage.getItem( 'firstName' );
	$scope.lastName = $window.sessionStorage.getItem( 'lastName' );
	$scope.login = $window.sessionStorage.getItem( 'login' )
});

userApp.controller('registerController', function($scope, $http, $window) {
    $scope.user = {firstName:"", lastName:"", login:"", password:""};

    $scope.submit = function() {
    	$scope.submitting = true;
    	$http({
    	    method: 'POST',
    	    url: '/users',
    	    data: $scope.user
    	}).success(function(data) {
    	    $scope.submitting = false;

    	    $window.open("#/","_self");
    	}).error(function(data, status) {
    	    $scope.submitting = false;
    	    if (status === 400)
    		$scope.badRequest = data;
    	    else if (status === 409)
    		$scope.badRequest = 'The name is already used.';
    	});
    };
});

userApp.controller('loginController', function($scope, $http,$window, userService) {

    $scope.singIn = function() {
    	$scope.submitting = true;
    	$http({
    	    method: 'POST',
    	    url: '/loggin',
    	    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    	    transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
            },
    	    data: {login: $scope.login, password: $scope.password}
    	}).success(function(data) {
    	    $scope.submitting = false;

			$window.sessionStorage.setItem('firstName', data.firstName);
			$window.sessionStorage.setItem('lastName', data.lastName);
			$window.sessionStorage.setItem('login', data.login);

			$window.open("#user","_self");

    	}).error(function(data, status) {
    	    $scope.submitting = false;
    	    if (status === 400)
    		$scope.badRequest = data;
    	});
    };
});