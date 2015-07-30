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
  	this.user = {
    	firstName:"",
    	lastName:"",
   		login:""
    }
});

userApp.controller('panelController', function($scope, userService) {
	$scope.firstName = userService.user.firstName;
	$scope.lastName = userService.user.lastName;
	$scope.login = userService.user.login;
});

userApp.controller('loginController', function($scope) {

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

			userService.user.firstName = data.firstName;
			userService.user.lastName = data.lastName;
			userService.user.login = data.login;

			$window.open("#user","_self");

    	}).error(function(data, status) {
    	    $scope.submitting = false;
    	    if (status === 400)
    		$scope.badRequest = data;
    	});
    };
});