var userApp = angular.module('userApp', ['ngRoute']);

userApp.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl : 'resources/pages/login.html',
			controller  : 'loginController'
		});
});

userApp.controller('loginController', function($scope) {
//	$scope.message = 'Everyone come and see how good I look!';
});

userApp.controller('signinController', function($scope, $http,$window) {

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

    	    var id = data.id;

    	    $window.open("/user/"+id,"_self");
//			$window.open("user.html","_self");

    	}).error(function(data, status) {
    	    $scope.submitting = false;
    	    if (status === 400)
    		$scope.badRequest = data;
    	});
    };
});