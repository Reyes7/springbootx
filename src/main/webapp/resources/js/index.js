var userApp = angular.module('userApp', ['ngRoute']);

userApp.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl : 'resources/pages/login.html',
			controller  : 'loginController'
		})
		.when('/user', {
        	templateUrl : 'resources/pages/user_panel.html',
        	controller  : 'panelController'
        });
});

userApp.service('userService', function() {
 	this.userData = {yearSetCount: 0};

  	this.user = function() {
        return this.userData;
  	};

  	this.setFirstName = function(firstName) {
        this.userData.firstName = firstName;
  	};

  	this.getFirstName = function() {
        return this.userData.firstName;
  	};

  	this.setLastName = function(lastName) {
        this.userData.lastName = lastName;
  	};

  	this.getLastName = function() {
        return this.userData.lastName;
  	};

  	this.setLogin = function(login) {
        this.userData.login = login;
  	};

  	this.getLogin = function() {
        return this.userData.login;
  	};
});

userApp.controller('loginController', function($scope) {

});

userApp.controller('panelController', function($scope, userService) {
	$scope.firstName = userService.getFirstName();
	$scope.lastName = userService.getLastName();
	$scope.login = userService.getLogin();
});

userApp.controller('signinController', function($scope, $http,$window, userService) {

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

			userService.setFirstName(data.firstName);
			userService.setLastName(data.lastName);
			userService.setLogin(data.login);

			$window.open("#user","_self");

    	}).error(function(data, status) {
    	    $scope.submitting = false;
    	    if (status === 400)
    		$scope.badRequest = data;
    	});
    };
});